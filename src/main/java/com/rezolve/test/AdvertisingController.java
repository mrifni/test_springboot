package com.rezolve.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/geofences/{geofenceId}/advertisings")
public class AdvertisingController {

    private final AdvertisingControllerHandler handler;

    AdvertisingController(AdvertisingControllerHandler handler) {
        this.handler = handler;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@PathVariable String geofenceId, @Valid @RequestBody AdvertisingObj body) {
        return handler.create(geofenceId, body);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{advertisingId}")
    public ResponseEntity<String> delete(@PathVariable String geofenceId, @PathVariable String advertisingId) {
        return handler.delete(geofenceId, advertisingId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{advertisingId}")
    public ResponseEntity<String> update(@PathVariable String geofenceId, @PathVariable String advertisingId, @Valid @RequestBody AdvertisingObj body) {
        return handler.update(geofenceId, advertisingId, body);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getAll(@PathVariable String geofenceId) {
        return handler.getAll(geofenceId);
    }
}
