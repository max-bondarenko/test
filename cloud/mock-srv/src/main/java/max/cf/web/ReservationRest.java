package max.cf.web;

import max.cf.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Maksym_Bondarenko on 1/25/2017.
 */

public class ReservationRest {

    @Autowired
    private ReservationRepo repo;

    @RequestMapping(path = "/reservation", method = RequestMethod.GET)
    private List<Reservation> get() {
        return repo.findAll();
    }

    @RequestMapping(path = "/reservation/{id}", method = RequestMethod.GET)
    private Reservation get(@PathVariable Long id) {
        return repo.findOne(id);
    }
}
