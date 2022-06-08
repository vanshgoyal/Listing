package com.example.Listing.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertyDTO {
    @Id
    private String id;

    private String propertyId;

    private String type;

    private float latitude;

    private float longitude;

    private String leaseType;

    private String parking;

    private String furnishing;

    private int rent;

    private int deposit;

    private String buildingType;

    private int numberOfPhotos;

    public PropertyDTO() {

    }


    public PropertyDTO(String id, String propertyId, String type, float latitude, float longitude, String leaseType, String parking, String furnishing, int rent, int deposit, String buildingType, int numberOfPhotos) {
        this.id = id;
        this.propertyId = propertyId;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.leaseType = leaseType;
        this.parking = parking;
        this.furnishing = furnishing;
        this.rent = rent;
        this.deposit = deposit;
        this.buildingType = buildingType;
        this.numberOfPhotos = numberOfPhotos;
    }

    @Override
    public String toString() {
        return "PropertyModel{" +
                "id='" + id + '\'' +
                ", propertyId='" + propertyId + '\'' +
                ", type='" + type + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", leaseType='" + leaseType + '\'' +
                ", parking='" + parking + '\'' +
                ", furnishing='" + furnishing + '\'' +
                ", rent=" + rent +
                ", deposit=" + deposit +
                ", buildingType='" + buildingType + '\'' +
                ", numberOfPhotos='" + numberOfPhotos + '\'' +
                '}';
    }
}
