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

    private Map<String, Float> coefficientType;

    private float coefficientLatitude;

    private float coefficientLongitude;

    private float coefficientLeaseType;

    private float coefficientParking;

    private float coefficientFurnishing;

    private float coefficientRent;

    private float coefficientDeposit;

    private float coefficientBuildingType;




    public CoefficientsDTO(){
    }

    public CoefficientsDTO(String id, String cityId, Map<String, Float> coefficientType, float coefficientLatitude, float coefficientLongitude, float coefficientLeaseType, float coefficientParking, float coefficientFurnishing, float coefficientRent, float coefficientDeposit, float coefficientBuildingType, float coefficientLifestyle, float coefficientTransit, float coefficientPhotoCount) {
        this.id = id;
        this.cityId = cityId;
        this.coefficientType = coefficientType;
        this.coefficientLatitude = coefficientLatitude;
        this.coefficientLongitude = coefficientLongitude;
        this.coefficientLeaseType = coefficientLeaseType;
        this.coefficientParking = coefficientParking;
        this.coefficientFurnishing = coefficientFurnishing;
        this.coefficientRent = coefficientRent;
        this.coefficientDeposit = coefficientDeposit;
        this.coefficientBuildingType = coefficientBuildingType;
    }
}
