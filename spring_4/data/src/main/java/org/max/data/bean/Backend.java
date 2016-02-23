package org.max.data.bean;

import com.google.common.collect.Sets;
import com.google.common.io.CharStreams;
import org.max.spring.data.back.QueryBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Maksym_Bondarenko on 2/23/2016.
 */
@Component
public class Backend implements QueryBackend, InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(Backend.class);

    @Value("${local.url}")
    private String localUrl;
    @Autowired(required = false)
    private RestTemplate template = new RestTemplate();
    private List<Converter> converters;
    @Autowired
    private ApplicationContext ctx;
    private PropertyPlaceholderHelper pc = new PropertyPlaceholderHelper("${", "}");
    private Map<String, String> cache = new ConcurrentReferenceHashMap<>();

    @Autowired(required = false)
    public void setConverters(List<Converter> converters) {
        this.converters = converters;
    }

    @Override
    public void afterPropertiesSet() throws IOException {
        Resource[] resources = ctx.getResources("file:**/*.tpl");
        log.debug("Registry got {} resources", resources == null ? 0 : resources.length);
        if (resources != null) {
            for (Resource resource : resources) {
                process(resource);
            }
        }
    }

    @Override
    public void validateTemplate(String name, final Set<String> placeholders) {
        Assert.notNull(name, "null template name");
        Assert.notNull(placeholders, "null template placeholders name");

        final HashSet<String> called = new HashSet<>();
        String tpl = cache.get(name);
        if (tpl == null) {
            throw new IllegalArgumentException("There is no template with name: " + name);
        }
        // add all called placeholder name
        pc.replacePlaceholders(tpl, new PropertyPlaceholderHelper.PlaceholderResolver() {
            @Override
            public String resolvePlaceholder(String placeholderName) {
                called.add(placeholderName);
                return "";
            }
        });
        //check is all need presents in given
        Sets.SetView<String> difference = Sets.difference(called, placeholders);
        if (!difference.isEmpty()) {
            throw new IllegalArgumentException("template has unresolved plaseholders:" + difference.toString());
        }
    }


    private void process(Resource resource) throws IOException {
        String filename = resource.getFilename();
        String key = filename.substring(0, filename.lastIndexOf('.'));
        if (log.isTraceEnabled()) {
            log.trace("Process {} ,put as {} cache entry.", filename, key);
        }
        cache.put(key, CharStreams.toString(new InputStreamReader(resource.getInputStream(), Charset.forName("UTF-8"))));
    }


    @Override
    public String getBaseUrl() {
        return localUrl;
    }

    @Override
    public <T> T executeByUrl(String url, HttpMethod method, Class<T> responseType, Object[] parameters) {
//        return template.exchange(getBaseUrl(),method, responseType);
        return null;
    }

    @Override
    public <T> T execute(String templateName, final Map<String, String> placeholders, Class<T> responseType) {
        String tpl = cache.get(templateName);
        if (tpl == null) {
            throw new IllegalArgumentException("There is no template with name: " + templateName);
        }
        String processedTemplate = pc.replacePlaceholders(tpl, new PropertyPlaceholderHelper.PlaceholderResolver() {
            @Override
            public String resolvePlaceholder(String placeholderName) {
                return placeholders.get(placeholderName);
            }
        });

        return template.postForObject(getBaseUrl(), processedTemplate, responseType);

    }
}
