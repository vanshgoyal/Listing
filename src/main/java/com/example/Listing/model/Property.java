package com.example.Listing.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "properties")
public class Property {

    @Id
    private String id;

    private String propertyId;

    private int type;

    private float latitude;

    private float longitude;

    private int leaseType;

    private int parking;

    private int furnishing;

    private int rent;

    private int deposit;

    private int buildingType;

    private int lifestyle;

    private int transit;

    private int photoCount;

    public String getId(){
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
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

    public int getLeaseType() {
        return leaseType;
    }

    public void setLeaseType(int leaseType) {
        this.leaseType = leaseType;
    }

    public int getParking() {
        return parking;
    }

    public void setParking(int parking) {
        this.parking = parking;
    }

    public int getFurnishing() {
        return furnishing;
    }

    public void setFurnishing(int furnishing) {
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

    public int getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(int buildingType) {
        this.buildingType = buildingType;
    }

    public int getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(int lifestyle) {
        this.lifestyle = lifestyle;
    }

    public int getTransit() {
        return transit;
    }

    public void setTransit(int transit) {
        this.transit = transit;
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
    }


    public Property() {
    }

    public Property(String id, String propertyId, int type, float latitude, float longitude, int leaseType, int parking, int furnishing, int rent, int deposit, int buildingType, int lifestyle, int transit, int photoCount) {
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
        this.lifestyle = lifestyle;
        this.transit = transit;
        this.photoCount = photoCount;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id='" + id + '\'' +
                ", propertyId='" + propertyId + '\'' +
                ", type=" + type +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", leaseType=" + leaseType +
                ", parking=" + parking +
                ", furnishing=" + furnishing +
                ", rent=" + rent +
                ", deposit=" + deposit +
                ", buildingType=" + buildingType +
                ", lifestyle=" + lifestyle +
                ", transit=" + transit +
                ", photoCount=" + photoCount +
                '}';
    }
}
