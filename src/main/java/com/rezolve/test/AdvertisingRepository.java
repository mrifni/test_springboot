package com.rezolve.test;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public class AdvertisingRepository {

    private final EntityManager em;

    AdvertisingRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public Advertising create(AdvertisingObj body, Geofence geofence) {
        // check href is reachable
        HttpUrlTest.test(body.href);

        Advertising advertising = new Advertising();
        advertising.setId(UUID.randomUUID().toString());
        advertising.setHref(body.href);
        advertising.setGeofence(geofence);

        em.persist(advertising);
        return advertising;
    }

    @Transactional
    public void delete(String geofenceId, String advertisingId) {
        em.createQuery("DELETE FROM Advertising a WHERE a.id = :id AND a.geofence.id = :geofenceId")
            .setParameter("id", advertisingId)
            .setParameter("geofenceId", geofenceId).executeUpdate();
    }

    public Advertising find(String id) {
        return em.find(Advertising.class, id);
    }

    public List<Advertising> findAll(String geofenceId) {
        return em.createQuery("SELECT a FROM Advertising a WHERE a.geofence.id = :geofenceId ORDER BY a.id")
            .setParameter("geofenceId", geofenceId)
            .getResultList();
    }

    @Transactional
    public void update(Advertising advertising, AdvertisingObj body) {
        // check href is reachable
        HttpUrlTest.test(body.href);

        advertising.setHref(body.href);
        em.merge(advertising);
    }
}
