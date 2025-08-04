package org.hotelhub.services;

import org.hotelhub.models.dto.*;
import org.hotelhub.models.entities.Address;
import org.hotelhub.models.entities.ArrivalTime;
import org.hotelhub.models.entities.Contact;
import org.hotelhub.models.entities.Hotel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HotelFactoryTest {

    private final HotelFactory hotelFactory = new HotelFactory();

    @Test
    void shouldCreateHotelFromRequest() {
        HotelRequest request = HotelRequest.builder()
                .name("Test Hotel")
                .address(AddressRequest.builder()
                        .houseNumber(1)
                        .street("Main St")
                        .city("New York")
                        .build())
                .contacts(new ContactRequest())
                .arrivalTime(new ArrivalTimeRequest())
                .build();

        Hotel hotel = hotelFactory.createFromRequest(request);

        assertEquals("Test Hotel", hotel.getName());
        assertEquals(1, hotel.getAddress().getHouseNumber());
        assertEquals("Main St", hotel.getAddress().getStreet());
    }

    @Test
    void shouldCreateSummaryResponse() {
        Hotel hotel = Hotel.builder()
                .id(1L)
                .name("Test Hotel")
                .address(Address.builder()
                        .houseNumber(1)
                        .street("Main St")
                        .city("New York")
                        .postCode("10001")
                        .country("USA")
                        .build())
                .contacts(Contact.builder()
                        .phone("+1234567890")
                        .build())
                .build();

        HotelSummaryResponse response = hotelFactory.createHotelSummaryResponse(hotel);

        assertEquals("1 Main St, New York, 10001 USA", response.getAddress());
        assertEquals("+1234567890", response.getPhone());
    }

    @Test
    void shouldCreateHotelResponse() {
        Address address = Address.builder()
                .houseNumber(123)
                .street("Main St")
                .city("New York")
                .postCode("10001")
                .country("USA")
                .build();

        Contact contact = Contact.builder()
                .phone("+1234567890")
                .email("info@hotel.com")
                .build();

        ArrivalTime arrivalTime = ArrivalTime.builder()
                .checkIn("14:00")
                .checkOut("12:00")
                .build();

        Hotel hotel = Hotel.builder()
                .id(1L)
                .name("Test Hotel")
                .description("A test hotel")
                .brand("Test Brand")
                .address(address)
                .contacts(contact)
                .arrivalTime(arrivalTime)
                .amenities(List.of("POOL", "GYM"))
                .build();

        // Вызов метода
        HotelResponse response = hotelFactory.createHotelResponse(hotel);

        // Проверки
        assertEquals(1L, response.getId());
        assertEquals("Test Hotel", response.getName());
        assertEquals("A test hotel", response.getDescription());
        assertEquals("Test Brand", response.getBrand());
        assertEquals(address, response.getAddress());
        assertEquals(contact, response.getContacts());
        assertEquals(arrivalTime, response.getArrivalTime());
        assertEquals(List.of("POOL", "GYM"), response.getAmenities());
    }
}