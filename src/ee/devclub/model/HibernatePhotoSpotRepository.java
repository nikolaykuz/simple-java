package ee.devclub.model;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateOperations;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class HibernatePhotoSpotRepository implements PhotoSpotRepository {
    @Autowired HibernateOperations hibernate;

    @Override
    public List<PhotoSpot> getAllSpots() {
        return hibernate.loadAll(PhotoSpot.class);
    }

    @Override
    public PhotoSpot persist(PhotoSpot spot) {
        hibernate.saveOrUpdate(spot);
        return spot;
    }

    @Override
    public PhotoSpot getSpotById(Long id) {
        PhotoSpot photoSpot = hibernate.load(PhotoSpot.class, id);
        //photoSpot is a lazy-initialized proxy
        hibernate.initialize(photoSpot);
        return photoSpot;
    }
}
