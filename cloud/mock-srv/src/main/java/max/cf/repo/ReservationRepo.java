package max.cf.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Maksym_Bondarenko on 1/25/2017.
 */
@RepositoryRestResource
public interface ReservationRepo  extends JpaRepository<Reservation ,Long> {
}
