package org.hotelhub.controllers;

import org.hotelhub.models.dto.HotelSummaryResponse;
import org.hotelhub.services.HotelService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SearchController.class)
class SearchControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private HotelService hotelService;

    private final HotelSummaryResponse hotel1 = HotelSummaryResponse.builder()
            .id(1L)
            .name("Hilton Minsk")
            .description("Luxury hotel in the city center")
            .address("Independence Avenue 1, Minsk, Belarus")
            .phone("+375 17 309-80-00")
            .build();

    private final HotelSummaryResponse hotel2 = HotelSummaryResponse.builder()
            .id(2L)
            .name("Marriott Berlin")
            .description("Comfortable hotel near Brandenburg Gate")
            .address("Friedrichstraße 105, Berlin, Germany")
            .phone("+49 30 2033-0")
            .build();

    @Test
    void searchHotels_ByName_ReturnsFullHotelData() throws Exception {
        Mockito.when(hotelService.searchHotels("Hilton", null, null, null, null))
                .thenReturn(Collections.singletonList(hotel1));

        mockMvc.perform(get("/property-view/search")
                        .param("name", "Hilton"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Hilton Minsk"))
                .andExpect(jsonPath("$[0].description").value("Luxury hotel in the city center"))
                .andExpect(jsonPath("$[0].address").value("Independence Avenue 1, Minsk, Belarus"))
                .andExpect(jsonPath("$[0].phone").value("+375 17 309-80-00"));
    }

    @Test
    void searchHotels_ByCity_ReturnsMultipleHotels() throws Exception {
        Mockito.when(hotelService.searchHotels(null, null, "Berlin", null, null))
                .thenReturn(Collections.singletonList(hotel2));

        mockMvc.perform(get("/property-view/search")
                        .param("city", "Berlin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].name").value("Marriott Berlin"))
                .andExpect(jsonPath("$[0].address").value("Friedrichstraße 105, Berlin, Germany"));
    }

    @Test
    void searchHotels_ByAmenities_ReturnsPartialData() throws Exception {
        HotelSummaryResponse partialHotel = HotelSummaryResponse.builder()
                .id(3L)
                .name("Partial Data Hotel")
                .address("Some Street, Partial City")
                .build();

        Mockito.when(hotelService.searchHotels(null, null, null, null, "POOL"))
                .thenReturn(Collections.singletonList(partialHotel));

        mockMvc.perform(get("/property-view/search")
                        .param("amenities", "POOL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(3))
                .andExpect(jsonPath("$[0].name").value("Partial Data Hotel"))
                .andExpect(jsonPath("$[0].description").doesNotExist())
                .andExpect(jsonPath("$[0].address").value("Some Street, Partial City"))
                .andExpect(jsonPath("$[0].phone").doesNotExist());
    }

    @Test
    void searchHotels_CombinedParameters_ReturnsFilteredResults() throws Exception {
        // Given
        Mockito.when(hotelService.searchHotels("Hilton", null, "Minsk", "Belarus", "WiFi"))
                .thenReturn(Collections.singletonList(hotel1));

        // When & Then
        mockMvc.perform(get("/property-view/search")
                        .param("name", "Hilton")
                        .param("city", "Minsk")
                        .param("country", "Belarus")
                        .param("amenities", "WiFi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].phone").value("+375 17 309-80-00"));
    }

    @Test
    void searchHotels_NoParameters_ReturnsAllHotels() throws Exception {
        // Given
        Mockito.when(hotelService.searchHotels(null, null, null, null, null))
                .thenReturn(Arrays.asList(hotel1, hotel2));

        // When & Then
        mockMvc.perform(get("/property-view/search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].description").value("Luxury hotel in the city center"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].description").value("Comfortable hotel near Brandenburg Gate"));
    }

    @Test
    void searchHotels_EmptyResult_ReturnsEmptyArray() throws Exception {
        // Given
        Mockito.when(hotelService.searchHotels("Unknown", null, null, null, null))
                .thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(get("/property-view/search")
                        .param("name", "Unknown"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void searchHotels_WithBrandParameter() throws Exception {
        // Given
        Mockito.when(hotelService.searchHotels(null, "Marriott", null, null, null))
                .thenReturn(Collections.singletonList(hotel2));

        // When & Then
        mockMvc.perform(get("/property-view/search")
                        .param("brand", "Marriott"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].phone").value("+49 30 2033-0"));
    }

    @Test
    void searchHotels_WithCountryParameter() throws Exception {
        // Given
        Mockito.when(hotelService.searchHotels(null, null, null, "Germany", null))
                .thenReturn(Collections.singletonList(hotel2));

        // When & Then
        mockMvc.perform(get("/property-view/search")
                        .param("country", "Germany"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address").value("Friedrichstraße 105, Berlin, Germany"));
    }
}