package ee.devclub.repo;

import ee.devclub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSpotRepository extends JpaRepository<User, Long> {

}
