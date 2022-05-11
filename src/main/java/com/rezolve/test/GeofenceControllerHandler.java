package com.rezolve.test;

import com.google.gson.JsonObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class GeofenceControllerHandler {
    private final GeofenceRepository geofenceRepository;

    GeofenceControllerHandler(GeofenceRepository geofenceRepository) {
        this.geofenceRepository = geofenceRepository;
    }

    public ResponseEntity<String> create(GeofenceObj body) {
        Geofence geofence = geofenceRepository.create(body);
        JsonObject root = new JsonObject();
        root.addProperty("id", geofence.getId());
        return ResponseEntity.ok().body("");
    }
}
