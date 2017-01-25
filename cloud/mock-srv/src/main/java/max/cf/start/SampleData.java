package max.cf.start;

import max.cf.repo.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Maksym_Bondarenko on 1/25/2017.
 */
@Component
public class SampleData implements CommandLineRunner{

    private static final Logger log = getLogger(SampleData.class);
    @Autowired
    private ReservationRepo repo;

    @Override
    public void run(String... args) throws Exception {
        Stream.of("Some One","Some Two").forEach( name -> repo.save(new Reservation(name)));
        repo.findAll().forEach(entity ->  log.debug("{} - {}", entity.getId(), entity.getName()));
    }
}
