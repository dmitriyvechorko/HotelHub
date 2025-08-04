package org.hotelhub.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private String description;
        private String brand;

        @Embedded
        private Address address;

        @Embedded
        private Contact contacts;

        @Embedded
        private ArrivalTime arrivalTime;

        @ElementCollection
        @CollectionTable(name = "amenities", joinColumns = @JoinColumn(name = "hotel_id"))
        @Column(name = "amenity")
        private List<String> amenities = new ArrayList<>();
}