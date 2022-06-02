package com.example.Listing.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@Setter
@Document(collection = "params")
public class CoefficientModel {

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



    public CoefficientModel(){

    }

    @Override
    public String toString() {
        return "CoefficientModel{" +
                "id='" + id + '\'' +
                ", cityId='" + cityId + '\'' +
                ", deposit=" + deposit +
                ", type=" + type +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", leaseType=" + leaseType +
                ", parking=" + parking +
                ", furnishing=" + furnishing +
                ", rent=" + rent +
                ", buildingType=" + buildingType +
                ", numberOfPhotos='" + numberOfPhotos + '\'' +
                '}';
    }

    public CoefficientModel(String id, String cityId, Map<String, Float> type, float latitude, float longitude, Map<String, Float> leaseType, Map<String, Float> parking, Map<String, Float> furnishing, float rent, float deposit, Map<String, Float> buildingType, float numberOfPhotos) {
        this.id = id;
        this.cityId = cityId;
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
}
