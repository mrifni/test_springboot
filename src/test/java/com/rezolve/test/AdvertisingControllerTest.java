package com.rezolve.test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@ContextConfiguration
@EntityScan
public class AdvertisingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestEntityManager testEntityManager;
    Geofence geofence;

    @BeforeEach
    public void create_geofence() {
        geofence = new Geofence();
        geofence.setId(UUID.randomUUID().toString());
        geofence.setLat(51.0000);
        geofence.setLng(51.232444);
        geofence.setRadius(10);

        testEntityManager.persist(geofence);
    }

    private String create_ad() throws Exception {
        JsonObject obj = new JsonObject();
        obj.addProperty("href", "https://google.com");
        Thread.sleep(1000);
        return mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/geofences/" + geofence.getId() + "/advertisings").contentType(MediaType.APPLICATION_JSON).content(obj.toString()).accept("*/*")
        ).andExpect(MockMvcResultMatchers.status().is(200)).andReturn().getResponse().getContentAsString();
    }

    @Test
    public void test_create_ads() throws Exception {
        create_ad();
        Advertising advertising = testEntityManager.getEntityManager().createQuery("SELECT ltr FROM Advertising ltr", Advertising.class).getSingleResult();

        Assertions.assertThat(advertising.getHref()).isEqualTo("https://google.com");
        Assertions.assertThat(advertising.getGeofence().getId()).isEqualTo(geofence.getId());
    }

    @Test
    public void test_update_ads() throws Exception {
        String adId = JsonParser.parseString(create_ad()).getAsJsonObject().get("id").getAsString();
        JsonObject obj = new JsonObject();
        obj.addProperty("href", "unreachable url");

        // unreachable URL
        mockMvc.perform(
                MockMvcRequestBuilders.put("/v1/geofences/" + geofence.getId() + "/advertisings/" + adId).contentType(MediaType.APPLICATION_JSON).content(obj.toString()).accept("*/*")
            )
            .andExpect(MockMvcResultMatchers.status().is(400))
            .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("Invalid URL unreachable url")));

        // reachable url
        obj.addProperty("href", "https://yahoo.com");
        mockMvc.perform(
                MockMvcRequestBuilders.put("/v1/geofences/" + geofence.getId() + "/advertisings/" + adId).contentType(MediaType.APPLICATION_JSON).content(obj.toString()).accept("*/*")
            )
            .andExpect(MockMvcResultMatchers.status().is(202));

        Advertising advertising = testEntityManager.getEntityManager().createQuery("SELECT ltr FROM Advertising ltr", Advertising.class).getSingleResult();
        Assertions.assertThat(advertising.getHref()).isEqualTo("https://yahoo.com");
    }

    @Test
    public void test_delete_ads() throws Exception {
        String adId = JsonParser.parseString(create_ad()).getAsJsonObject().get("id").getAsString();

        // unreachable URL
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/v1/geofences/" + geofence.getId() + "/advertisings/" + adId).contentType(MediaType.APPLICATION_JSON).accept("*/*")
            )
            .andExpect(MockMvcResultMatchers.status().is(202));

        List<Advertising> results = testEntityManager.getEntityManager().createQuery("SELECT ltr FROM Advertising ltr", Advertising.class).getResultList();
        Assertions.assertThat(results.size()).isEqualTo(0);
    }

    @Test
    public void test_getall_ads() throws Exception {
        Advertising ad1 = new Advertising();
        ad1.setId(UUID.randomUUID().toString());
        ad1.setHref("https://google.com");
        ad1.setGeofence(geofence);

        Advertising ad2 = new Advertising();
        ad2.setId(UUID.randomUUID().toString());
        ad2.setHref("https://yahoo.com");
        ad2.setGeofence(geofence);

        testEntityManager.persist(ad1);
        testEntityManager.persist(ad2);

        // unreachable URL
        String response = mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/geofences/" + geofence.getId() + "/advertisings/").contentType(MediaType.APPLICATION_JSON).accept("*/*")
            )
            .andExpect(MockMvcResultMatchers.status().is(200))
            .andReturn().getResponse().getContentAsString();

        JsonArray responseArray = JsonParser.parseString(response).getAsJsonArray();

        Assertions.assertThat(responseArray.size()).isEqualTo(2);
        Assertions.assertThat(responseArray.get(0).getAsJsonObject().get("radius").getAsDouble()).isEqualTo(geofence.getRadius());
    }
}