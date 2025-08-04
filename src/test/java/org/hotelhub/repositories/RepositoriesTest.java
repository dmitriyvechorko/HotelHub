package org.hotelhub.repositories;

import org.hotelhub.models.entities.Hotel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepositoriesTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    void shouldFindByBrand() {
        Hotel hotel = Hotel.builder()
                .name("Test Hotel")
                .brand("Hilton")
                .build();
        entityManager.persist(hotel);

        List<Hotel> result = hotelRepository.search(null, "Hilton", null, null, null);

        assertEquals(1, result.size());
        assertEquals("Hilton", result.get(0).getBrand());
    }

    @Test
    void shouldFindByAmenity() {
        Hotel hotel = Hotel.builder()
                .amenities(List.of("Pool", "Spa"))
                .build();
        entityManager.persist(hotel);

        List<Hotel> result = hotelRepository.search(null, null, null, null, "Spa");

        assertEquals(1, result.size());
        assertTrue(result.get(0).getAmenities().contains("Spa"));
    }
}