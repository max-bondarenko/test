package max.cf.proxy;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Maksym_Bondarenko on 1/27/2017.
 */
@RestController
@RequestMapping(path = "/reservation")
public class ReservationApiGateway {

    private final ReservationFeignProxy proxy;

    @Autowired
    public ReservationApiGateway(ReservationFeignProxy proxy) {
        this.proxy = proxy;
    }

    public List<String > fallback(){
        return new ArrayList<>();
    }

    @RequestMapping()
    @HystrixCommand(fallbackMethod = "fallback")
    public List<String> get() {
        return proxy.get().getContent()
                .stream()
                .map(re -> re.name)
                .collect(Collectors.toList());
    }

}
