package org.max.spring.data.config;

import org.max.spring.data.config.query.builder.Query;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.InterceptingHttpAccessor;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.web.client.*;
import org.springframework.web.util.DefaultUriTemplateHandler;
import org.springframework.web.util.UriTemplateHandler;

import javax.xml.transform.Source;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.*;

/**
 * Created by Maksym_Bondarenko on 4/20/2016.
 */
public class DruidTemplate extends InterceptingHttpAccessor {

    private static final boolean jackson2Present =
            ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", RestTemplate.class.getClassLoader()) &&
                    ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator", RestTemplate.class.getClassLoader());

    private static final boolean gsonPresent =
            ClassUtils.isPresent("com.google.gson.Gson", RestTemplate.class.getClassLoader());


    private final List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
    private final ResponseExtractor<HttpHeaders> headersExtractor = new DruidTemplate.HeadersExtractor();
    private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();
    private UriTemplateHandler uriTemplateHandler = new DefaultUriTemplateHandler();
    private URI druidUri;


    /**
     * Create a new instance of the {@link RestTemplate} using default settings.
     * Default {@link HttpMessageConverter}s are initialized.
     */
    public DruidTemplate() {
        this.messageConverters.add(new ByteArrayHttpMessageConverter());
        this.messageConverters.add(new StringHttpMessageConverter());
        this.messageConverters.add(new ResourceHttpMessageConverter());
        this.messageConverters.add(new SourceHttpMessageConverter<Source>());
        this.messageConverters.add(new AllEncompassingFormHttpMessageConverter());

        if (jackson2Present) {
            this.messageConverters.add(new MappingJackson2HttpMessageConverter());
        } else if (gsonPresent) {
            this.messageConverters.add(new GsonHttpMessageConverter());
        }
    }

    public DruidTemplate(String url) {
        this();
        druidUri = getUriTemplateHandler().expand(url);
    }


    /**
     * Create a new instance of the {@link RestTemplate} based on the given {@link ClientHttpRequestFactory}.
     *
     * @param requestFactory HTTP request factory to use
     * @see org.springframework.http.client.SimpleClientHttpRequestFactory
     * @see org.springframework.http.client.HttpComponentsClientHttpRequestFactory
     */
    public DruidTemplate(ClientHttpRequestFactory requestFactory) {
        this();
        setRequestFactory(requestFactory);
    }

    /**
     * Create a new instance of the {@link RestTemplate} using the given list of
     * {@link HttpMessageConverter} to use
     *
     * @param messageConverters the list of {@link HttpMessageConverter} to use
     * @since 3.2.7
     */
    public DruidTemplate(List<HttpMessageConverter<?>> messageConverters) {
        Assert.notEmpty(messageConverters, "At least one HttpMessageConverter required");
        this.messageConverters.addAll(messageConverters);
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<? extends T[]> getArrayClass(Class<T> clazz) {
        return (Class<? extends T[]>) Array.newInstance(clazz, 0).getClass();
    }

    /**
     * Return the message body converters.
     */
    public List<HttpMessageConverter<?>> getMessageConverters() {
        return this.messageConverters;
    }

    /**
     * Set the message body converters to use.
     * <p>These converters are used to convert from and to HTTP requests and responses.
     */
    public void setMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
        Assert.notEmpty(messageConverters, "At least one HttpMessageConverter required");
        // Take getMessageConverters() List as-is when passed in here
        if (this.messageConverters != messageConverters) {
            this.messageConverters.clear();
            this.messageConverters.addAll(messageConverters);
        }
    }

    /**
     * Return the error handler.
     */
    public ResponseErrorHandler getErrorHandler() {
        return this.errorHandler;
    }

    /**
     * Set the error handler.
     * <p>By default, RestTemplate uses a {@link DefaultResponseErrorHandler}.
     */
    public void setErrorHandler(ResponseErrorHandler errorHandler) {
        Assert.notNull(errorHandler, "ResponseErrorHandler must not be null");
        this.errorHandler = errorHandler;
    }

    /**
     * Return the configured URI template handler.
     */
    public UriTemplateHandler getUriTemplateHandler() {
        return this.uriTemplateHandler;
    }

    // POST

    /**
     * Set a custom {@link UriTemplateHandler} for expanding URI templates.
     * <p>By default, RestTemplate uses {@link DefaultUriTemplateHandler}.
     *
     * @param handler the URI template handler to use
     */
    public void setUriTemplateHandler(UriTemplateHandler handler) {
        Assert.notNull(handler, "UriTemplateHandler must not be null");
        this.uriTemplateHandler = handler;
    }

    public <T> T postForObject(String url, Object request, Class<T> responseType, Object... uriVariables)
            throws RestClientException {

        RequestCallback requestCallback = httpEntityCallback(request, responseType);
        HttpMessageConverterExtractor<T> responseExtractor =
                new HttpMessageConverterExtractor<T>(responseType, getMessageConverters());
        return execute(url, HttpMethod.POST, requestCallback, responseExtractor, uriVariables);
    }

    /**
     * Use this.
     *
     * @param request
     * @param responseType
     * @param <T>
     * @return
     */
    public <T> List<T> query(Query request, Class<T> responseType) {
        Assert.notNull(druidUri, "Druid url must be set.");

        Class<? extends T[]> arrayClass = getArrayClass(responseType);

        RequestCallback requestCallback = httpEntityCallback(request, arrayClass);
        HttpMessageConverterExtractor<T[]> responseExtractor =
                new HttpMessageConverterExtractor<T[]>(arrayClass, getMessageConverters());

        T[] execute = execute(druidUri, HttpMethod.POST, requestCallback, responseExtractor);

        if (execute != null && execute.length > 0) {
            return new ArrayList<T>(Arrays.asList(execute));
        } else {
            return new ArrayList<>();
        }
    }

    public <T> List<T> postForList(String url, Query request, Class<T> responseType, Object... uriVariables)
            throws RestClientException {

        Class<? extends T[]> arrayClass = getArrayClass(responseType);

        RequestCallback requestCallback = httpEntityCallback(request, arrayClass);
        HttpMessageConverterExtractor<T[]> responseExtractor =
                new HttpMessageConverterExtractor<T[]>(arrayClass, getMessageConverters());

        T[] execute = execute(url, HttpMethod.POST, requestCallback, responseExtractor, uriVariables);

        if (execute != null && execute.length > 0) {
            return new ArrayList<T>(Arrays.asList(execute));
        } else {
            return new ArrayList<>();
        }
    }

    public <T> T postForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables)
            throws RestClientException {

        RequestCallback requestCallback = httpEntityCallback(request, responseType);
        HttpMessageConverterExtractor<T> responseExtractor =
                new HttpMessageConverterExtractor<T>(responseType, getMessageConverters());
        return execute(url, HttpMethod.POST, requestCallback, responseExtractor, uriVariables);
    }

    public <T> T postForObject(URI url, Object request, Class<T> responseType) throws RestClientException {
        RequestCallback requestCallback = httpEntityCallback(request, responseType);
        HttpMessageConverterExtractor<T> responseExtractor =
                new HttpMessageConverterExtractor<T>(responseType, getMessageConverters());
        return execute(url, HttpMethod.POST, requestCallback, responseExtractor);
    }

    // OPTIONS
    public Set<HttpMethod> optionsForAllow(String url, Object... urlVariables) throws RestClientException {
        ResponseExtractor<HttpHeaders> headersExtractor = headersExtractor();
        HttpHeaders headers = execute(url, HttpMethod.OPTIONS, null, headersExtractor, urlVariables);
        return headers.getAllow();
    }

    public Set<HttpMethod> optionsForAllow(String url, Map<String, ?> urlVariables) throws RestClientException {
        ResponseExtractor<HttpHeaders> headersExtractor = headersExtractor();
        HttpHeaders headers = execute(url, HttpMethod.OPTIONS, null, headersExtractor, urlVariables);
        return headers.getAllow();
    }

    // general execution

    public Set<HttpMethod> optionsForAllow(URI url) throws RestClientException {
        ResponseExtractor<HttpHeaders> headersExtractor = headersExtractor();
        HttpHeaders headers = execute(url, HttpMethod.OPTIONS, null, headersExtractor);
        return headers.getAllow();
    }

    public <T> T execute(String url, HttpMethod method, RequestCallback requestCallback,
                         ResponseExtractor<T> responseExtractor, Object... urlVariables) throws RestClientException {

        URI expanded = getUriTemplateHandler().expand(url, urlVariables);
        return doExecute(expanded, method, requestCallback, responseExtractor);
    }

    public <T> T execute(String url, HttpMethod method, RequestCallback requestCallback,
                         ResponseExtractor<T> responseExtractor, Map<String, ?> urlVariables) throws RestClientException {

        URI expanded = getUriTemplateHandler().expand(url, urlVariables);
        return doExecute(expanded, method, requestCallback, responseExtractor);
    }

    public <T> T execute(URI url, HttpMethod method, RequestCallback requestCallback,
                         ResponseExtractor<T> responseExtractor) throws RestClientException {

        return doExecute(url, method, requestCallback, responseExtractor);
    }

    /**
     * Execute the given method on the provided URI.
     * <p>The {@link ClientHttpRequest} is processed using the {@link RequestCallback};
     * the response with the {@link ResponseExtractor}.
     *
     * @param url               the fully-expanded URL to connect to
     * @param method            the HTTP method to execute (GET, POST, etc.)
     * @param requestCallback   object that prepares the request (can be {@code null})
     * @param responseExtractor object that extracts the return value from the response (can be {@code null})
     * @return an arbitrary object, as returned by the {@link ResponseExtractor}
     */
    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback,
                              ResponseExtractor<T> responseExtractor) throws RestClientException {

        Assert.notNull(url, "'url' must not be null");
        Assert.notNull(method, "'method' must not be null");
        ClientHttpResponse response = null;
        try {
            ClientHttpRequest request = createRequest(url, method);
            if (requestCallback != null) {
                requestCallback.doWithRequest(request);
            }
            response = request.execute();
            handleResponse(url, method, response);
            if (responseExtractor != null) {
                return responseExtractor.extractData(response);
            } else {
                return null;
            }
        } catch (IOException ex) {
            throw new ResourceAccessException("I/O error on " + method.name() +
                    " request for \"" + url + "\": " + ex.getMessage(), ex);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    /**
     * Handle the given response, performing appropriate logging and
     * invoking the {@link ResponseErrorHandler} if necessary.
     * <p>Can be overridden in subclasses.
     *
     * @param url      the fully-expanded URL to connect to
     * @param method   the HTTP method to execute (GET, POST, etc.)
     * @param response the resulting {@link ClientHttpResponse}
     * @throws IOException if propagated from {@link ResponseErrorHandler}
     * @see #setErrorHandler
     * @since 4.1.6
     */
    protected void handleResponse(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        ResponseErrorHandler errorHandler = getErrorHandler();
        boolean hasError = errorHandler.hasError(response);
        if (logger.isDebugEnabled()) {
            try {
                logger.debug(method.name() + " request for \"" + url + "\" resulted in " +
                        response.getRawStatusCode() + " (" + response.getStatusText() + ")" +
                        (hasError ? "; invoking error handler" : ""));
            } catch (IOException ex) {
                // ignore
            }
        }
        if (hasError) {
            errorHandler.handleError(response);
        }
    }

    /**
     * Returns a request callback implementation that prepares the request {@code Accept}
     * headers based on the given response type and configured
     * {@linkplain #getMessageConverters() message converters}.
     */
    protected <T> RequestCallback acceptHeaderRequestCallback(Class<T> responseType) {
        return new DruidTemplate.AcceptHeaderRequestCallback(responseType);
    }

    /**
     * Returns a request callback implementation that writes the given object to the
     * request stream.
     */
    protected <T> RequestCallback httpEntityCallback(Object requestBody) {
        return new DruidTemplate.HttpEntityRequestCallback(requestBody);
    }

    /**
     * Returns a request callback implementation that writes the given object to the
     * request stream.
     */
    protected <T> RequestCallback httpEntityCallback(Object requestBody, Type responseType) {
        return new DruidTemplate.HttpEntityRequestCallback(requestBody, responseType);
    }

    /**
     * Returns a response extractor for {@link ResponseEntity}.
     */
    protected <T> ResponseExtractor<ResponseEntity<T>> responseEntityExtractor(Type responseType) {
        return new DruidTemplate.ResponseEntityResponseExtractor<T>(responseType);
    }

    /**
     * Returns a response extractor for {@link HttpHeaders}.
     */
    protected ResponseExtractor<HttpHeaders> headersExtractor() {
        return this.headersExtractor;
    }

    /**
     * Response extractor that extracts the response {@link HttpHeaders}.
     */
    private static class HeadersExtractor implements ResponseExtractor<HttpHeaders> {


        public HttpHeaders extractData(ClientHttpResponse response) throws IOException {
            return response.getHeaders();
        }
    }

    /**
     * Request callback implementation that prepares the request's accept headers.
     */
    private class AcceptHeaderRequestCallback implements RequestCallback {

        private final Type responseType;

        private AcceptHeaderRequestCallback(Type responseType) {
            this.responseType = responseType;
        }


        public void doWithRequest(ClientHttpRequest request) throws IOException {
            if (this.responseType != null) {
                Class<?> responseClass = null;
                if (this.responseType instanceof Class) {
                    responseClass = (Class<?>) this.responseType;
                }
                List<MediaType> allSupportedMediaTypes = new ArrayList<MediaType>();
                for (HttpMessageConverter<?> converter : getMessageConverters()) {
                    if (responseClass != null) {
                        if (converter.canRead(responseClass, null)) {
                            allSupportedMediaTypes.addAll(getSupportedMediaTypes(converter));
                        }
                    } else if (converter instanceof GenericHttpMessageConverter) {
                        GenericHttpMessageConverter<?> genericConverter = (GenericHttpMessageConverter<?>) converter;
                        if (genericConverter.canRead(this.responseType, null, null)) {
                            allSupportedMediaTypes.addAll(getSupportedMediaTypes(converter));
                        }
                    }
                }
                if (!allSupportedMediaTypes.isEmpty()) {
                    MediaType.sortBySpecificity(allSupportedMediaTypes);
                    if (logger.isDebugEnabled()) {
                        logger.debug("Setting request Accept header to " + allSupportedMediaTypes);
                    }
                    request.getHeaders().setAccept(allSupportedMediaTypes);
                }
            }
        }

        private List<MediaType> getSupportedMediaTypes(HttpMessageConverter<?> messageConverter) {
            List<MediaType> supportedMediaTypes = messageConverter.getSupportedMediaTypes();
            List<MediaType> result = new ArrayList<MediaType>(supportedMediaTypes.size());
            for (MediaType supportedMediaType : supportedMediaTypes) {
                if (supportedMediaType.getCharSet() != null) {
                    supportedMediaType =
                            new MediaType(supportedMediaType.getType(), supportedMediaType.getSubtype());
                }
                result.add(supportedMediaType);
            }
            return result;
        }
    }

    /**
     * Request callback implementation that writes the given object to the request stream.
     */
    private class HttpEntityRequestCallback extends DruidTemplate.AcceptHeaderRequestCallback {

        private final HttpEntity<?> requestEntity;

        private HttpEntityRequestCallback(Object requestBody) {
            this(requestBody, null);
        }

        private HttpEntityRequestCallback(Object requestBody, Type responseType) {
            super(responseType);
            if (requestBody instanceof HttpEntity) {
                this.requestEntity = (HttpEntity<?>) requestBody;
            } else if (requestBody != null) {
                this.requestEntity = new HttpEntity<Object>(requestBody);
            } else {
                this.requestEntity = HttpEntity.EMPTY;
            }
        }


        @SuppressWarnings("unchecked")
        public void doWithRequest(ClientHttpRequest httpRequest) throws IOException {
            super.doWithRequest(httpRequest);
            if (!this.requestEntity.hasBody()) {
                HttpHeaders httpHeaders = httpRequest.getHeaders();
                HttpHeaders requestHeaders = this.requestEntity.getHeaders();
                if (!requestHeaders.isEmpty()) {
                    httpHeaders.putAll(requestHeaders);
                }
                if (httpHeaders.getContentLength() == -1) {
                    httpHeaders.setContentLength(0L);
                }
            } else {
                Object requestBody = this.requestEntity.getBody();
                Class<?> requestType = requestBody.getClass();
                HttpHeaders requestHeaders = this.requestEntity.getHeaders();
                MediaType requestContentType = requestHeaders.getContentType();
                for (HttpMessageConverter<?> messageConverter : getMessageConverters()) {
                    if (messageConverter.canWrite(requestType, requestContentType)) {
                        if (!requestHeaders.isEmpty()) {
                            httpRequest.getHeaders().putAll(requestHeaders);
                        }
                        if (logger.isDebugEnabled()) {
                            if (requestContentType != null) {
                                logger.debug("Writing [" + requestBody + "] as \"" + requestContentType +
                                        "\" using [" + messageConverter + "]");
                            } else {
                                logger.debug("Writing [" + requestBody + "] using [" + messageConverter + "]");
                            }

                        }
                        ((HttpMessageConverter<Object>) messageConverter).write(
                                requestBody, requestContentType, httpRequest);
                        return;
                    }
                }
                String message = "Could not write request: no suitable HttpMessageConverter found for request type [" +
                        requestType.getName() + "]";
                if (requestContentType != null) {
                    message += " and content type [" + requestContentType + "]";
                }
                throw new RestClientException(message);
            }
        }
    }

    /**
     * Response extractor for {@link HttpEntity}.
     */
    private class ResponseEntityResponseExtractor<T> implements ResponseExtractor<ResponseEntity<T>> {

        private final HttpMessageConverterExtractor<T> delegate;

        public ResponseEntityResponseExtractor(Type responseType) {
            if (responseType != null && Void.class != responseType) {
                this.delegate = new HttpMessageConverterExtractor<T>(responseType, getMessageConverters());
            } else {
                this.delegate = null;
            }
        }


        public ResponseEntity<T> extractData(ClientHttpResponse response) throws IOException {
            if (this.delegate != null) {
                T body = this.delegate.extractData(response);
                return new ResponseEntity<T>(body, response.getHeaders(), response.getStatusCode());
            } else {
                return new ResponseEntity<T>(response.getHeaders(), response.getStatusCode());
            }
        }
    }
}
