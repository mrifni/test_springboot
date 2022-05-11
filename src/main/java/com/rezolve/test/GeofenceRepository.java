package com.rezolve.test;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.UUID;

@Repository
public class GeofenceRepository {

    private final EntityManager em;

    GeofenceRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public Geofence create(GeofenceObj body) {
        Geofence geofence = new Geofence();
        geofence.setId(UUID.randomUUID().toString());
        geofence.setLat(body.lat);
        geofence.setLng(body.lng);
        geofence.setRadius(body.radius);

        em.persist(geofence);
        return geofence;
    }

    public Geofence find(String geofenceId) {
        return em.find(Geofence.class, geofenceId);
    }
}
