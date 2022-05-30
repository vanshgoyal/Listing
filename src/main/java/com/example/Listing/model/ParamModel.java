package com.example.Listing.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.PublicKey;

@Document(collection = "params")
public class ParamModel {

    @Id
    private String id;

    private String cityId;

    private float paramType;

    private float paramLatitude;

    private float paramLongitude;

    private float paramLeaseType;

    private float paramParking;

    private float paramFurnishing;

    private float paramRent;

    private float paramDeposit;

    private float paramBuildingType;

    private float paramLifestyle;

    private float paramTransit;

    private float paramPhotoCount;

    @Override
    public String toString() {
        return "paramModel{" +
                "id='" + id + '\'' +
                ", cityId='" + cityId + '\'' +
                ", paramType=" + paramType +
                ", paramLatitude=" + paramLatitude +
                ", paramLongitude=" + paramLongitude +
                ", paramLeaseType=" + paramLeaseType +
                ", paramParking=" + paramParking +
                ", paramFurnishing=" + paramFurnishing +
                ", paramRent=" + paramRent +
                ", paramDeposit=" + paramDeposit +
                ", paramBuildingType=" + paramBuildingType +
                ", paramLifestyle=" + paramLifestyle +
                ", paramTransit=" + paramTransit +
                ", paramPhotoCount=" + paramPhotoCount +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public float getParamType() {
        return paramType;
    }

    public void setParamType(float paramType) {
        this.paramType = paramType;
    }

    public float getParamLatitude() {
        return paramLatitude;
    }

    public void setParamLatitude(float paramLatitude) {
        this.paramLatitude = paramLatitude;
    }

    public float getParamLongitude() {
        return paramLongitude;
    }

    public void setParamLongitude(float paramLongitude) {
        this.paramLongitude = paramLongitude;
    }

    public float getParamLeaseType() {
        return paramLeaseType;
    }

    public void setParamLeaseType(float paramLeaseType) {
        this.paramLeaseType = paramLeaseType;
    }

    public float getParamParking() {
        return paramParking;
    }

    public void setParamParking(float paramParking) {
        this.paramParking = paramParking;
    }

    public float getParamFurnishing() {
        return paramFurnishing;
    }

    public void setParamFurnishing(float paramFurnishing) {
        this.paramFurnishing = paramFurnishing;
    }

    public float getParamRent() {
        return paramRent;
    }

    public void setParamRent(float paramRent) {
        this.paramRent = paramRent;
    }

    public float getParamDeposit() {
        return paramDeposit;
    }

    public void setParamDeposit(float paramDeposit) {
        this.paramDeposit = paramDeposit;
    }

    public float getParamBuildingType() {
        return paramBuildingType;
    }

    public void setParamBuildingType(float paramBuildingType) {
        this.paramBuildingType = paramBuildingType;
    }

    public float getParamLifestyle() {
        return paramLifestyle;
    }

    public void setParamLifestyle(float paramLifestyle) {
        this.paramLifestyle = paramLifestyle;
    }

    public float getParamTransit() {
        return paramTransit;
    }

    public void setParamTransit(float paramTransit) {
        this.paramTransit = paramTransit;
    }

    public float getParamPhotoCount() {
        return paramPhotoCount;
    }

    public void setParamPhotoCount(float paramPhotoCount) {
        this.paramPhotoCount = paramPhotoCount;
    }

    public ParamModel(){

    }

    public ParamModel(String id, String cityId, float paramType, float paramLatitude, float paramLongitude, float paramLeaseType, float paramParking, float paramFurnishing, float paramRent, float paramDeposit, float paramBuildingType, float paramLifestyle, float paramTransit, float paramPhotoCount) {
        this.id = id;
        this.cityId = cityId;
        this.paramType = paramType;
        this.paramLatitude = paramLatitude;
        this.paramLongitude = paramLongitude;
        this.paramLeaseType = paramLeaseType;
        this.paramParking = paramParking;
        this.paramFurnishing = paramFurnishing;
        this.paramRent = paramRent;
        this.paramDeposit = paramDeposit;
        this.paramBuildingType = paramBuildingType;
        this.paramLifestyle = paramLifestyle;
        this.paramTransit = paramTransit;
        this.paramPhotoCount = paramPhotoCount;
    }


}
