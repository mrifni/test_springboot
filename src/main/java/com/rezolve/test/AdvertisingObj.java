package com.rezolve.test;

import javax.validation.constraints.NotBlank;

public class AdvertisingObj {
    @NotBlank(message = "href is missing")
    String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
