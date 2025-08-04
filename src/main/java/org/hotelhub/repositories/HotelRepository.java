package org.hotelhub.repositories;

import org.hotelhub.models.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("SELECT h FROM Hotel h WHERE " +
            "(:name IS NULL OR LOWER(h.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:brand IS NULL OR LOWER(h.brand) = LOWER(:brand)) AND " +
            "(:city IS NULL OR LOWER(h.address.city) = LOWER(:city)) AND " +
            "(:country IS NULL OR LOWER(h.address.country) = LOWER(:country)) AND " +
            "(:amenity IS NULL OR :amenity MEMBER OF h.amenities)")
    List<Hotel> search(
            @Param("name") String name,
            @Param("brand") String brand,
            @Param("city") String city,
            @Param("country") String country,
            @Param("amenity") String amenity);
}