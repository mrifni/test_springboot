package com.rezolve.test;

import javax.validation.constraints.NotBlank;

public class AdvertisingObj {
    @NotBlank(message = "href is missing")
    String href;
}
