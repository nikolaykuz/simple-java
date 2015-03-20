package ee.devclub.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataAnotherSpotRepository extends JpaRepository<AnotherSpot, Long> {
}
