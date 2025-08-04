package org.hotelhub.services;

import lombok.RequiredArgsConstructor;
import org.hotelhub.exceptions.HotelNotFoundException;
import org.hotelhub.models.dto.HotelRequest;
import org.hotelhub.models.dto.AmenitiesRequest;
import org.hotelhub.models.dto.HotelResponse;
import org.hotelhub.models.dto.HotelSummaryResponse;
import org.hotelhub.models.entities.Hotel;
import org.hotelhub.repositories.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelFactory hotelFactory;

    public List<HotelSummaryResponse> getAllHotelsSummary() {
        return hotelRepository.findAll().stream()
                .map(hotelFactory::createHotelSummaryResponse)
                .toList();
    }

    public HotelResponse getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));
        return hotelFactory.createHotelResponse(hotel);
    }

    public List<HotelSummaryResponse> searchHotels(
            String name, String brand, String city, String country, String amenity) {

        List<Hotel> hotels = hotelRepository.search(
                name, brand, city, country, amenity);

        return hotels.stream()
                .map(hotelFactory::createHotelSummaryResponse)
                .toList();
    }

    public HotelSummaryResponse createHotel(HotelRequest request) {
        Hotel hotel = hotelFactory.createFromRequest(request);
        Hotel savedHotel = hotelRepository.save(hotel);
        return hotelFactory.createHotelSummaryResponse(savedHotel);
    }

    public void addAmenities(Long hotelId, AmenitiesRequest request) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException(hotelId));

        if (hotel.getAmenities() == null) {
            hotel.setAmenities(new ArrayList<>());
        }

        hotel.getAmenities().addAll(request.getAmenities());
        hotelRepository.save(hotel);
    }

    public Map<String, Long> getHistogram(String param) {
        return switch (param.toLowerCase()) {
            case "brand" -> hotelRepository.findAll().stream()
                    .collect(Collectors.groupingBy(
                            Hotel::getBrand, Collectors.counting()));
            case "city" -> hotelRepository.findAll().stream()
                    .collect(Collectors.groupingBy(
                            h -> h.getAddress().getCity(), Collectors.counting()));
            case "country" -> hotelRepository.findAll().stream()
                    .collect(Collectors.groupingBy(
                            h -> h.getAddress().getCountry(), Collectors.counting()));
            case "amenities" -> hotelRepository.findAll().stream()
                    .flatMap(h -> h.getAmenities().stream())
                    .collect(Collectors.groupingBy(
                            Function.identity(), Collectors.counting()));
            default -> throw new IllegalArgumentException("Invalid histogram parameter: " + param);
        };
    }
}