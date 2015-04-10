package ee.devclub.repo;

import ee.devclub.model.PhotoSpotCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoSpotCategoryRepository extends JpaRepository<PhotoSpotCategory, Long> {
}
