package org.hotelhub.services;

import org.hotelhub.models.dto.*;
import org.hotelhub.models.entities.Address;
import org.hotelhub.models.entities.ArrivalTime;
import org.hotelhub.models.entities.Contact;
import org.hotelhub.models.entities.Hotel;
import org.hotelhub.repositories.HotelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelFactory hotelFactory;

    @InjectMocks
    private HotelService hotelService;

    @Test
    void shouldSearchHotels() {
        String name = "Grand";
        String city = "Minsk";

        Hotel hotel1 = createSampleHotel(1L, "Grand Hotel", "Minsk");
        Hotel hotel2 = createSampleHotel(2L, "Grand Plaza", "Minsk");

        List<Hotel> foundHotels = List.of(hotel1, hotel2);
        when(hotelRepository.search(name, null, city, null, null)).thenReturn(foundHotels);

        HotelSummaryResponse response1 = createSummaryResponse(1L, "Grand Hotel");
        HotelSummaryResponse response2 = createSummaryResponse(2L, "Grand Plaza");
        when(hotelFactory.createHotelSummaryResponse(hotel1)).thenReturn(response1);
        when(hotelFactory.createHotelSummaryResponse(hotel2)).thenReturn(response2);

        List<HotelSummaryResponse> result = hotelService.searchHotels(name, null, city, null, null);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
    }

    @Test
    void shouldCreateHotelAndReturnSummaryResponse() {
        // Given: Подготовка тестовых данных
        HotelRequest request = HotelRequest.builder()
                .name("Grand Plaza")
                .description("Luxury hotel")
                .brand("Hilton")
                .address(AddressRequest.builder()
                        .houseNumber(123)
                        .street("Main Street")
                        .city("Minsk")
                        .postCode("220000")
                        .country("Belarus")
                        .build())
                .contacts(ContactRequest.builder()
                        .phone("+3751234567")
                        .email("info@hotel.com")
                        .build())
                .arrivalTime(ArrivalTimeRequest.builder()
                        .checkIn("14:00")
                        .checkOut("12:00")
                        .build())
                .build();

        Hotel newHotel = Hotel.builder()
                .name("Grand Plaza")
                .description("Luxury hotel")
                .brand("Hilton")
                .address(Address.builder()
                        .houseNumber(123)
                        .street("Main Street")
                        .city("Minsk")
                        .postCode("220000")
                        .country("Belarus")
                        .build())
                .contacts(Contact.builder()
                        .phone("+3751234567")
                        .email("info@hotel.com")
                        .build())
                .arrivalTime(ArrivalTime.builder()
                        .checkIn("14:00")
                        .checkOut("12:00")
                        .build())
                .amenities(new ArrayList<>())
                .build();

        Hotel savedHotel = Hotel.builder()
                .id(1L)
                .name("Grand Plaza")
                .description("Luxury hotel")
                .brand("Hilton")
                .address(Address.builder()
                        .houseNumber(123)
                        .street("Main Street")
                        .city("Minsk")
                        .postCode("220000")
                        .country("Belarus")
                        .build())
                .contacts(Contact.builder()
                        .phone("+3751234567")
                        .email("info@hotel.com")
                        .build())
                .arrivalTime(ArrivalTime.builder()
                        .checkIn("14:00")
                        .checkOut("12:00")
                        .build())
                .amenities(new ArrayList<>())
                .build();

        HotelSummaryResponse expectedResponse = HotelSummaryResponse.builder()
                .id(1L)
                .name("Grand Plaza")
                .build();

        // When: Настройка поведения моков
        when(hotelFactory.createFromRequest(request)).thenReturn(newHotel);
        when(hotelRepository.save(newHotel)).thenReturn(savedHotel);
        when(hotelFactory.createHotelSummaryResponse(savedHotel)).thenReturn(expectedResponse);

        // Then: Выполнение тестируемого метода и проверки
        HotelSummaryResponse actualResponse = hotelService.createHotel(request);

        // Проверка результата
        assertNotNull(actualResponse, "Response should not be null");
        assertEquals(1L, actualResponse.getId(), "ID should match");
        assertEquals("Grand Plaza", actualResponse.getName(), "Name should match");

        // Проверка взаимодействий
        verify(hotelFactory, times(1)).createFromRequest(request);
        verify(hotelRepository, times(1)).save(newHotel);
        verify(hotelFactory, times(1)).createHotelSummaryResponse(savedHotel);

        // Дополнительные проверки преобразования
        assertEquals(request.getName(), newHotel.getName(), "Name should be mapped correctly");
        assertEquals(request.getDescription(), newHotel.getDescription(), "Description should be mapped correctly");
        assertEquals(request.getAddress().getCity(), newHotel.getAddress().getCity(), "City should be mapped correctly");
    }

    @Test
    void shouldGetHotelDetailsById() {
        Hotel hotel = createDetailedHotel(1L);

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(hotelFactory.createHotelResponse(hotel)).thenReturn(
                HotelResponse.builder()
                        .name(hotel.getName())
                        .address(hotel.getAddress())
                        .contacts(hotel.getContacts())
                        .arrivalTime(hotel.getArrivalTime())
                        .amenities(hotel.getAmenities())
                        .build()
        );

        HotelResponse response = hotelService.getHotelById(1L);

        assertEquals("Grand Plaza", response.getName());
        assertEquals("Main Street", response.getAddress().getStreet());
        assertEquals("info@hotel.com", response.getContacts().getEmail());
        assertEquals("14:00", response.getArrivalTime().getCheckIn());
        assertEquals(List.of("POOL", "SPA"), response.getAmenities());
    }

    @Test
    void shouldGetAllHotelsSummary() {
        Hotel hotel1 = createSampleHotel(1L, "Hotel 1", "City1");
        Hotel hotel2 = createSampleHotel(2L, "Hotel 2", "City2");
        List<Hotel> allHotels = List.of(hotel1, hotel2);

        when(hotelRepository.findAll()).thenReturn(allHotels);

        HotelSummaryResponse response1 = createSummaryResponse(1L, "Hotel 1");
        HotelSummaryResponse response2 = createSummaryResponse(2L, "Hotel 2");
        when(hotelFactory.createHotelSummaryResponse(hotel1)).thenReturn(response1);
        when(hotelFactory.createHotelSummaryResponse(hotel2)).thenReturn(response2);

        List<HotelSummaryResponse> result = hotelService.getAllHotelsSummary();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
    }

    @Test
    void shouldGetHistogramByBrand() {
        Hotel hotel1 = createSampleHotel(1L, "Hotel 1", "Brest");
        hotel1.setBrand("Brand1");
        Hotel hotel2 = createSampleHotel(2L, "Hotel 2", "Bari");
        hotel2.setBrand("Brand1");
        Hotel hotel3 = createSampleHotel(3L, "Hotel 3", "Barcelona");
        hotel3.setBrand("Brand2");

        List<Hotel> allHotels = List.of(hotel1, hotel2, hotel3);
        when(hotelRepository.findAll()).thenReturn(allHotels);

        Map<String, Long> histogram = hotelService.getHistogram("brand");

        assertEquals(2, histogram.size());
        assertEquals(2, histogram.get("Brand1"));
        assertEquals(1, histogram.get("Brand2"));
    }

    @Test
    void shouldGetHistogramByCity() {
        Hotel hotel1 = createSampleHotel(1L, "Hotel 1", "Barcelona");
        Hotel hotel2 = createSampleHotel(2L, "Hotel 2", "Bari");
        Hotel hotel3 = createSampleHotel(3L, "Hotel 3", "Barcelona");

        List<Hotel> allHotels = List.of(hotel1, hotel2, hotel3);
        when(hotelRepository.findAll()).thenReturn(allHotels);

        Map<String, Long> histogram = hotelService.getHistogram("city");

        assertEquals(2, histogram.size());
        assertEquals(2, histogram.get("Barcelona"));
        assertEquals(1, histogram.get("Bari"));
    }

    @Test
    void shouldGetHistogramByCountry() {
        Hotel hotel1 = createSampleHotel(1L, "Hotel 1", "Rome");
        hotel1.getAddress().setCountry("Italy");
        Hotel hotel2 = createSampleHotel(2L, "Hotel 2", "Bari");
        hotel2.getAddress().setCountry("Italy");
        Hotel hotel3 = createSampleHotel(3L, "Hotel 3", "Barcelona");
        hotel3.getAddress().setCountry("Spain");

        List<Hotel> allHotels = List.of(hotel1, hotel2, hotel3);
        when(hotelRepository.findAll()).thenReturn(allHotels);

        Map<String, Long> histogram = hotelService.getHistogram("country");

        assertEquals(2, histogram.size());
        assertEquals(2, histogram.get("Italy"));
        assertEquals(1, histogram.get("Spain"));
    }

    @Test
    void shouldAddAmenitiesToExistingHotel() {
        // Создаем отель и устанавливаем удобства
        Hotel hotel = createSampleHotel(1L, "Test Hotel", "Minsk");
        hotel.setAmenities(new ArrayList<>(List.of("WI-FI")));

        // Настраиваем поведение репозитория
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        // Вызываем тестируемый метод
        hotelService.addAmenities(1L, new AmenitiesRequest(List.of("POOL", "GYM")));

        // Проверяем результат
        assertEquals(3, hotel.getAmenities().size());
        assertTrue(hotel.getAmenities().contains("WI-FI"));
        assertTrue(hotel.getAmenities().contains("POOL"));
        assertTrue(hotel.getAmenities().contains("GYM"));

        // Проверяем вызов сохранения
        verify(hotelRepository, times(1)).save(hotel);
    }

    @Test
    void shouldGetHistogramByAmenities() {
        Hotel hotel1 = createSampleHotel(1L, "Hotel 1", "Brest");
        hotel1.setAmenities(List.of("POOL", "GYM"));
        Hotel hotel2 = createSampleHotel(2L, "Hotel 2", "Bari");
        hotel2.setAmenities(List.of("GYM", "SAUNA"));
        Hotel hotel3 = createSampleHotel(3L, "Hotel 3", "Barcelona");
        hotel3.setAmenities(List.of("POOL", "SAUNA"));

        List<Hotel> allHotels = List.of(hotel1, hotel2, hotel3);
        when(hotelRepository.findAll()).thenReturn(allHotels);

        Map<String, Long> histogram = hotelService.getHistogram("amenities");

        assertEquals(3, histogram.size());
        assertEquals(2, histogram.get("POOL"));
        assertEquals(2, histogram.get("GYM"));
        assertEquals(2, histogram.get("SAUNA"));
    }

    @Test
    void shouldThrowOnInvalidHistogramParam() {
        assertThrows(IllegalArgumentException.class, () ->
                hotelService.getHistogram("invalid_param"));
    }

    private Hotel createSampleHotel(Long id, String name, String city) {
        Address address = Address.builder()
                .city(city)
                .country("Country")
                .build();
        return Hotel.builder()
                .id(id)
                .name(name)
                .address(address)
                .amenities(new ArrayList<>())
                .build();
    }

    private HotelSummaryResponse createSummaryResponse(Long id, String name) {
        return HotelSummaryResponse.builder()
                .id(id)
                .name(name)
                .build();
    }

    private Hotel createDetailedHotel(Long id) {
        Address address = Address.builder()
                .houseNumber(123)
                .street("Main Street")
                .city("Minsk")
                .postCode("220000")
                .country("Belarus")
                .build();

        Contact contacts = Contact.builder()
                .phone("+3751234567")
                .email("info@hotel.com")
                .build();

        ArrivalTime arrivalTime = ArrivalTime.builder()
                .checkIn("14:00")
                .checkOut("12:00")
                .build();

        return Hotel.builder()
                .id(id)
                .name("Grand Plaza")
                .description("Luxury hotel")
                .brand("Hilton")
                .address(address)
                .contacts(contacts)
                .arrivalTime(arrivalTime)
                .amenities(List.of("POOL", "SPA"))
                .build();
    }
}