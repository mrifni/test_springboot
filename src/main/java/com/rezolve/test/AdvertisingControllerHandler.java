package com.rezolve.test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rezolve.test.exceptions.InvalidValueException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AdvertisingControllerHandler {
    private final AdvertisingRepository advertisingRepository;
    private final GeofenceRepository geofenceRepository;

    AdvertisingControllerHandler(AdvertisingRepository advertisingRepository, GeofenceRepository geofenceRepository) {
        this.advertisingRepository = advertisingRepository;
        this.geofenceRepository = geofenceRepository;
    }

    public ResponseEntity<String> create(String geofenceId, AdvertisingObj body) {
        Geofence geofence = geofenceRepository.find(geofenceId);
        if (geofence == null) {
            throw new InvalidValueException("geofence is not found");
        }
        Advertising advertising = advertisingRepository.create(body, geofence);
        JsonObject root = new JsonObject();
        root.addProperty("id", advertising.getId());
        return ResponseEntity.ok().body(root.toString());
    }

    public ResponseEntity<String> delete(String geofenceId, String advertisingId) {
        Geofence geofence = geofenceRepository.find(geofenceId);
        if (geofence == null) {
            throw new InvalidValueException("geofence is not found");
        }
        Advertising advertising = advertisingRepository.find(advertisingId);
        if (advertising == null) {
            throw new InvalidValueException("advertising is not found");
        }

        advertisingRepository.delete(geofence.getId(), advertising.getId());
        return ResponseEntity.accepted().build();
    }

    public ResponseEntity<String> update(String geofenceId, String advertisingId, AdvertisingObj body) {
        Advertising advertising = advertisingRepository.find(advertisingId);
        if (advertising == null) {
            throw new InvalidValueException("advertising is not found");
        }
        advertisingRepository.update(advertising, body);
        return ResponseEntity.accepted().build();
    }

    public ResponseEntity<String> getAll(String geofenceId) {
        Geofence geofence = geofenceRepository.find(geofenceId);
        if (geofence == null) {
            throw new InvalidValueException("geofence is not found");
        }

        JsonArray ads = new JsonArray();
        for (Advertising advertising : advertisingRepository.findAll(geofenceId)) {
            JsonObject obj = new JsonObject();
            obj.addProperty("href", advertising.getHref());
            obj.addProperty("radius", geofence.getRadius());

            ads.add(obj);
        }

        return ResponseEntity.ok().body(ads.toString());
    }
}
