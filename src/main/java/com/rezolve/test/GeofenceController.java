package com.rezolve.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/geofences")
public class GeofenceController {

    private final GeofenceControllerHandler handler;

    GeofenceController(GeofenceControllerHandler handler) {
        this.handler = handler;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@Valid @RequestBody GeofenceObj body){
        return handler.create(body);
    }
}
