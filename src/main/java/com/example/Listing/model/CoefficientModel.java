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

    private Map<String, Float> coefficientType;

    private float coefficientLatitude;

    private float coefficientLongitude;

    private Map<String, Float> coefficientLeaseType;

    private Map<String, Float> coefficientParking;

    private Map<String, Float> coefficientFurnishing;

    private float coefficientRent;

    private float coefficientDeposit;

    private Map<String, Float> coefficientBuildingType;





    public CoefficientModel(){

    }

    @Override
    public String toString() {
        return "CoefficientModel{" +
                "id='" + id + '\'' +
                ", cityId='" + cityId + '\'' +
                ", coefficientType=" + coefficientType +
                ", coefficientLatitude=" + coefficientLatitude +
                ", coefficientLongitude=" + coefficientLongitude +
                ", coefficientLeaseType=" + coefficientLeaseType +
                ", coefficientParking=" + coefficientParking +
                ", coefficientFurnishing=" + coefficientFurnishing +
                ", coefficientRent=" + coefficientRent +
                ", coefficientDeposit=" + coefficientDeposit +
                ", coefficientBuildingType=" + coefficientBuildingType +
                '}';
    }

    public CoefficientModel(String id, String cityId, Map<String, Float> coefficientType, float coefficientLatitude, float coefficientLongitude, Map<String, Float> coefficientLeaseType, Map<String, Float> coefficientParking, Map<String, Float> coefficientFurnishing, float coefficientRent, float coefficientDeposit, Map<String, Float> coefficientBuildingType) {
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
