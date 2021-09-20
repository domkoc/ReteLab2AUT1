package hu.bme.aut.retelab2.repository;

import hu.bme.aut.retelab2.domain.Ad;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AdRepository {
    @PersistenceContext
    private EntityManager em;
    @Transactional
    public Ad save(Ad ad) {
        return em.merge(ad);
    }
    public List<Ad> getBetween(Integer minimumPrice, Integer maximumPrice) {
        return em.createQuery("SELECT a FROM Ad a WHERE a.price > ?1 AND a.price < ?2", Ad.class)
                .setParameter(1, minimumPrice)
                .setParameter(2, maximumPrice)
                .getResultList();
    }
    @Transactional
    public Ad updateAd(Ad updatedAd) throws Exception {
        Ad adInDB = em.find(Ad.class, updatedAd.getId());
        if (!adInDB.getKey().equals(updatedAd.getKey())) {
            throw new Exception();
        }
        adInDB.setTitle(updatedAd.getTitle());
        adInDB.setDescription(updatedAd.getDescription());
        adInDB.setPrice(updatedAd.getPrice());
        adInDB.setTags(updatedAd.getTags());
        adInDB.setExpiration(updatedAd.getExpiration());
        return em.merge(adInDB);
    }
    public List<Ad> getByTag(String tag) {
        List<Ad> ads = em.createQuery("SELECT a FROM Ad a", Ad.class).getResultList();
        ads.removeIf( ad -> !ad.getTags().contains(tag));
        return ads;
    }
    @Scheduled(fixedDelay= 6000)
    @Transactional
    public void deleteExpired(){
        List<Ad> expiredAds = em.createQuery("SELECT a FROM Ad a WHERE a.expiration IS NOT NULL", Ad.class).getResultList();
        expiredAds.removeIf( ad -> ad.getExpiration().isAfter(LocalDateTime.now()));
        for(Ad ad : expiredAds) {
            em.remove(ad);
        }
    }
}
