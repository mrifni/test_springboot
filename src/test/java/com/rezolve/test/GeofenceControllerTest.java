package com.rezolve.test;

import com.google.gson.JsonObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@ContextConfiguration
@EntityScan
public class GeofenceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void test_create() throws Exception {
        double lat = 51.0000;
        double lng = 51.0000;
        double radius = 10;
        JsonObject obj = new JsonObject();
        obj.addProperty("lat", lat);
        obj.addProperty("lng", lng);
        obj.addProperty("radius", radius);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/geofences").contentType(MediaType.APPLICATION_JSON).content(obj.toString()).accept("*/*")
        ).andExpect(MockMvcResultMatchers.status().is(200));
        Geofence geofence = testEntityManager.getEntityManager().createQuery("SELECT ltr FROM Geofence ltr", Geofence.class).getSingleResult();

        Assertions.assertThat(geofence.getLat()).isEqualTo(lat);
        Assertions.assertThat(geofence.getLng()).isEqualTo(lng);
        Assertions.assertThat(geofence.getRadius()).isEqualTo(radius);
    }
}