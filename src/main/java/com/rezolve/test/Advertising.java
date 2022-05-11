package com.rezolve.test;

import javax.persistence.*;

@Table(name = "Advertising")
@Entity
public class Advertising {

    @Id
    @Column(name = "id", updatable = false)
    private String id = null;

    @Column(name = "href")
    private String href;

    @OneToOne
    @JoinColumn
    private Geofence geofence;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Geofence getGeofence() {
        return geofence;
    }

    public void setGeofence(Geofence geofence) {
        this.geofence = geofence;
    }
}
