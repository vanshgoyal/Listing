package com.example.Listing.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Map;

@Getter
@Setter
public class CoefficientsDTO {

    @Id
    private String id;

    private String cityId;

    private float deposit;

    private Map<String, Float> type;

    private float latitude;

    private float longitude;

    private Map<String, Float> leaseType;

    private Map<String, Float> parking;

    private Map<String, Float> furnishing;

    private float rent;

    private Map<String, Float> buildingType;

    private float numberOfPhotos;


    public CoefficientsDTO() {
    }

    public CoefficientsDTO(String id, String cityId, float deposit, Map<String, Float> type, float latitude, float longitude, Map<String, Float> leaseType, Map<String, Float> parking, Map<String, Float> furnishing, float rent, Map<String, Float> buildingType, float numberOfPhotos) {
        this.id = id;
        this.cityId = cityId;
        this.deposit = deposit;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.leaseType = leaseType;
        this.parking = parking;
        this.furnishing = furnishing;
        this.rent = rent;
        this.buildingType = buildingType;
        this.numberOfPhotos = numberOfPhotos;
    }
}
