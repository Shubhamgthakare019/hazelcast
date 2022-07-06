package com.java.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.model.Country;
import com.java.db.CountryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CountryController.class)
public class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryRepository countryRepository;

    @Test
    public void testCreateCountry() throws Exception {
        String uri = "/country/addCountry";

        Country country = new Country();
        country.setCountryId("1");
        country.setCountryName("UK");

        Country savedCountry = new Country();
        savedCountry.setCountryId("1");
        savedCountry.setCountryName("UK");

        Mockito.when(countryRepository.save(country)).thenReturn(savedCountry);

        mockMvc.perform(
                post(uri)
                        .contentType("application/json")
                        .content(mapToJson(country)))
                .andExpect(status().isOk())
                .andExpect(content().string("Persisted countryId : " + 1));

    }

    @Test
    public void testUpdateCountry() throws Exception {
        String uri = "/country/updateCountry";

        Country country = new Country();
        country.setCountryId("1");
        country.setCountryName("US");

        Country savedCountry = new Country();
        savedCountry.setCountryId("1");
        savedCountry.setCountryName("UK");



        mockMvc.perform(
                post(uri)
                        .contentType("application/json")
                        .content(mapToJson(country)))
                .andExpect(status().isOk())
                .andExpect(content().string("Updated countryId : " + 1));

    }

    @Test
    public void getCountry() throws Exception {
        String url = "/country/get/1";

        Country country = new Country();
        country.setCountryId("1");
        country.setCountryName("US");

        Mockito.when(countryRepository.findByCountryId(country.getCountryId())).thenReturn(country);

        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedResponse = mapToJson(country);

        assertEquals(actualResponse, expectedResponse);


    }


    @Test
    public void testDeleteCountry() throws Exception {

        String countryId = "1";

        String url = "/country/delete/" + countryId;

       /* mockMvc.perform(get(url)).andExpect(status().isOk());
        Country result = countryRepository.deleteByCountryId(countryId);

        assertNull(result);*/

        Country country = new Country();
        country.setCountryId("1");
        country.setCountryName("UK");

        Mockito.when(countryRepository.deleteByCountryId(countryId)).thenReturn(country);

        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedResponse =  countryId;

        assertEquals(expectedResponse, actualResponse);
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}
