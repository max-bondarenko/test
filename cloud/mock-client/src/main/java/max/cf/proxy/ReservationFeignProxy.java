package max.cf.proxy;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Maksym_Bondarenko on 1/27/2017.
 */
@FeignClient("mock-srv")
public interface ReservationFeignProxy {

    @RequestMapping(path = "/reservations", method = RequestMethod.GET)
    Resources<Reservation> get();



}

class Reservation {

    public String name;

    public void setName(String name) {
        this.name = name;
    }
}
