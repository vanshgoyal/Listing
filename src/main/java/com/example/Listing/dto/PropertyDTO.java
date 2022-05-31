package com.example.Listing.dto;

import org.springframework.data.annotation.Id;

public class PropertyDTO {
    @Id
    private String id;

    private String propertyId;

    private String type;

    private float latitude;

    private float longitude;

    private String leaseType;

    private String  parking;

    private String furnishing;

    private int rent;

    private int deposit;

    private String buildingType;

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
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getLeaseType() {
        return leaseType;
    }

    public void setLeaseType(String leaseType) {
        this.leaseType = leaseType;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getFurnishing() {
        return furnishing;
    }

    public void setFurnishing(String furnishing) {
        this.furnishing = furnishing;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }
    public PropertyDTO(){

    }
    public PropertyDTO(String id, String propertyId, String type, float latitude, float longitude, String leaseType, String parking, String furnishing, int rent, int deposit, String buildingType) {
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
    }
}
