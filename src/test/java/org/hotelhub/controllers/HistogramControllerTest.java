package org.hotelhub.controllers;

import org.hotelhub.services.HotelService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class HistogramControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private HotelService hotelService;

    @Test
    void getHistogram_ByCountry() throws Exception {
        Mockito.when(hotelService.getHistogram("country"))
                .thenReturn(Map.of(
                        "Germany", 15L,
                        "France", 8L
                ));

        mockMvc.perform(get("/property-view/histogram/country"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Germany").value(15))
                .andExpect(jsonPath("$.France").value(8));
    }

    @Test
    void getHistogram_ByBrand() throws Exception {
        Mockito.when(hotelService.getHistogram("brand"))
                .thenReturn(Map.of(
                        "Hilton", 12L,
                        "Marriott", 9L
                ));

        mockMvc.perform(get("/property-view/histogram/brand"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Hilton").value(12))
                .andExpect(jsonPath("$.Marriott").value(9));
    }

    @Test
    void getHistogram_EmptyResult() throws Exception {
        Mockito.when(hotelService.getHistogram("unknownParam"))
                .thenReturn(Map.of());

        mockMvc.perform(get("/property-view/histogram/unknownParam"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}