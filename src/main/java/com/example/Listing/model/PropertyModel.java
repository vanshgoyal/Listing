package com.example.Listing.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "properties")
public class PropertyModel {

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

    private long activationDate;

    private int numberOFContacts;

    private boolean sponsored;




    public PropertyModel() {

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
                ", numberOfPhotos=" + numberOfPhotos +
                ", activationDate=" + activationDate +
                ", numberOFContacts=" + numberOFContacts +
                ", sponsored=" + sponsored +
                '}';
    }

    public PropertyModel(String id, String propertyId, String type, float latitude, float longitude, String leaseType, String parking, String furnishing, int rent, int deposit, String buildingType, int numberOfPhotos, long activationDate, int numberOFContacts, boolean sponsored) {
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
        this.activationDate = activationDate;
        this.numberOFContacts = numberOFContacts;
        this.sponsored = sponsored;
    }
}
