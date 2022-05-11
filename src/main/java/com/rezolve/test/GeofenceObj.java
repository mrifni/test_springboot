package com.rezolve.test;

import javax.validation.constraints.NotNull;

public class GeofenceObj {
    @NotNull(message = "Lat is missing")
    Double lat;
    @NotNull(message = "Lng is missing")
    Double lng;
    @NotNull(message = "Radius is missing")
    Double radius;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }
}
