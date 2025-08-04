package org.hotelhub.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hotelhub.exceptions.HotelNotFoundException;
import org.hotelhub.models.dto.*;
import org.hotelhub.models.entities.Address;
import org.hotelhub.models.entities.ArrivalTime;
import org.hotelhub.models.entities.Contact;
import org.hotelhub.services.HotelService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HotelController.class)
class HotelControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private HotelService hotelService;

    private final HotelSummaryResponse hotelSummary = HotelSummaryResponse.builder()
            .id(1L)
            .name("Test Hotel")
            .description("Test Description")
            .address("Test Address")
            .phone("+375 123-4567")
            .build();

    private final HotelResponse hotelDetails = HotelResponse.builder()
            .id(1L)
            .name("Test Hotel")
            .description("Detailed Description")
            .brand("Test Brand")
            .address(Address.builder()
                    .street("Main St")
                    .houseNumber(123)
                    .city("Test City")
                    .country("Test Country")
                    .postCode("123456")
                    .build())
            .contacts(Contact.builder()
                    .phone("+375 123-4567")
                    .email("test@example.com")
                    .build())
            .arrivalTime(ArrivalTime.builder()
                    .checkIn("14:00")
                    .checkOut("12:00")
                    .build())
            .build();

    @Test
    void shouldCreateHotel() throws Exception {
        HotelRequest request = HotelRequest.builder()
                .name("Test Hotel")
                .build();

        when(hotelService.createHotel(any(HotelRequest.class))).thenReturn(hotelSummary);

        mockMvc.perform(post("/property-view/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Hotel"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.address").value("Test Address"))
                .andExpect(jsonPath("$.phone").value("+375 123-4567"));
    }

    @Test
    void shouldReturn404WhenHotelNotFound() throws Exception {
        Long hotelId = 1L;
        when(hotelService.getHotelById(hotelId))
                .thenThrow(new HotelNotFoundException(hotelId));

        mockMvc.perform(get("/property-view/hotels/{id}", hotelId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllHotels() throws Exception {
        List<HotelSummaryResponse> hotels = Arrays.asList(
                HotelSummaryResponse.builder().id(1L).name("Hotel 1").build(),
                HotelSummaryResponse.builder().id(2L).name("Hotel 2").build()
        );

        when(hotelService.getAllHotelsSummary()).thenReturn(hotels);

        mockMvc.perform(get("/property-view/hotels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Hotel 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Hotel 2"));
    }

    @Test
    void shouldReturnEmptyListWhenNoHotels() throws Exception {
        when(hotelService.getAllHotelsSummary()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/property-view/hotels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldAddAmenitiesToHotel() throws Exception {
        AmenitiesRequest request = new AmenitiesRequest();
        request.setAmenities(Arrays.asList("POOL", "GYM"));

        mockMvc.perform(post("/property-view/hotels/1/amenities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        Mockito.verify(hotelService).addAmenities(eq(1L), any(AmenitiesRequest.class));
    }

    @Test
    void shouldReturn404WhenAddingAmenitiesToNonExistentHotel() throws Exception {

        AmenitiesRequest request = new AmenitiesRequest();
        request.setAmenities(List.of("POOL"));

        doThrow(new HotelNotFoundException(999L))
                .when(hotelService).addAmenities(eq(999L), any(AmenitiesRequest.class));

        mockMvc.perform(post("/property-view/hotels/999/amenities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetHotelDetailsById() throws Exception {
        when(hotelService.getHotelById(1L)).thenReturn(hotelDetails);

        mockMvc.perform(get("/property-view/hotels/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Hotel"))
                .andExpect(jsonPath("$.description").value("Detailed Description"))
                .andExpect(jsonPath("$.brand").value("Test Brand"))
                .andExpect(jsonPath("$.address.street").value("Main St"))
                .andExpect(jsonPath("$.contacts.phone").value("+375 123-4567"))
                .andExpect(jsonPath("$.arrivalTime.checkIn").value("14:00"));
    }
}