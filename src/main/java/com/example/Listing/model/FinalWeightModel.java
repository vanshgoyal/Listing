package com.example.Listing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FinalWeightModel {
    @Id
    private String id;

    private String cityId;

    private float Type;

    private float Latitude;

    private float Longitude;


    private float LeaseType;

    private float Parking;

    private float Furnishing;

    private float Rent;

    private float deposit;

    private float BuildingType;


    public FinalWeightModel(){

    }


    public FinalWeightModel(String id, String cityId, float type, float latitude, float longitude, float leaseType, float parking, float furnishing, float rent, float deposit, float buildingType, float lifestyle, float transit, float photoCount) {
        this.id = id;
        this.cityId = cityId;
        Type = type;
        Latitude = latitude;
        Longitude = longitude;
        LeaseType = leaseType;
        Parking = parking;
        Furnishing = furnishing;
        Rent = rent;
        this.deposit = deposit;
        BuildingType = buildingType;
    }

}
