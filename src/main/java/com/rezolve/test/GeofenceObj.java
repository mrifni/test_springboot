package com.rezolve.test;

import javax.validation.constraints.NotNull;

public class GeofenceObj {
    @NotNull(message = "Lat is missing")
    double lat;
    @NotNull(message = "Lng is missing")
    double lng;
    @NotNull(message = "Radius is missing")
    double radius;
}
