package org.hotelhub.services;

import lombok.RequiredArgsConstructor;
import org.hotelhub.models.dto.HotelRequest;
import org.hotelhub.models.dto.HotelResponse;
import org.hotelhub.models.dto.HotelSummaryResponse;
import org.hotelhub.models.entities.Hotel;
import org.hotelhub.models.entities.Address;
import org.hotelhub.models.entities.ArrivalTime;
import org.hotelhub.models.entities.Contact;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class HotelFactory {

    public Hotel createFromRequest(HotelRequest request) {
        Hotel.HotelBuilder builder = Hotel.builder()
                .name(request.getName())
                .description(request.getDescription())
                .brand(request.getBrand())
                .amenities(new ArrayList<>());

        if (request.getAddress() != null) {
            builder.address(Address.builder()
                    .houseNumber(request.getAddress().getHouseNumber())
                    .street(request.getAddress().getStreet())
                    .city(request.getAddress().getCity())
                    .country(request.getAddress().getCountry())
                    .postCode(request.getAddress().getPostCode())
                    .build());
        }

        if (request.getContacts() != null) {
            builder.contacts(Contact.builder()
                    .phone(request.getContacts().getPhone())
                    .email(request.getContacts().getEmail())
                    .build());
        }

        if (request.getArrivalTime() != null) {
            builder.arrivalTime(ArrivalTime.builder()
                    .checkIn(request.getArrivalTime().getCheckIn())
                    .checkOut(request.getArrivalTime().getCheckOut())
                    .build());
        }

        return builder.build();
    }

    public HotelResponse createHotelResponse(Hotel hotel) {
        return HotelResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .brand(hotel.getBrand())
                .address(hotel.getAddress())
                .contacts(hotel.getContacts())
                .arrivalTime(hotel.getArrivalTime())
                .amenities(hotel.getAmenities())
                .build();
    }

    public HotelSummaryResponse createHotelSummaryResponse(Hotel hotel) {
        String address = String.format("%d %s, %s, %s %s",
                hotel.getAddress().getHouseNumber(),
                hotel.getAddress().getStreet(),
                hotel.getAddress().getCity(),
                hotel.getAddress().getPostCode(),
                hotel.getAddress().getCountry());

        return HotelSummaryResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .address(address)
                .phone(hotel.getContacts().getPhone())
                .build();
    }
}