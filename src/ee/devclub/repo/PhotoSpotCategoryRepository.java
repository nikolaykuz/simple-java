package ee.devclub.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoSpotCategoryRepository extends JpaRepository<PhotoSpotRepository, Long> {
}
