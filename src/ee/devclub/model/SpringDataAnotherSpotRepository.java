package ee.devclub.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author nkuznetsov
 * @since 10.03.2015
 */
@Repository
public interface SpringDataAnotherSpotRepository extends JpaRepository<AnotherSpot, Long> {
}
