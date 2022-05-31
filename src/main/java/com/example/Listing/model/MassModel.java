package com.example.Listing.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "masses")
public class MassModel {
    @Id
    private String id;

    private String propertyId;

    private float massVal;

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

    public float getMassVal() {
        return massVal;
    }

    public void setMassVal(float massVal) {
        this.massVal = massVal;
    }

    public MassModel(){

    }

    public MassModel(String id, String propertyId, float massVal) {
        this.id = id;
        this.propertyId = propertyId;
        this.massVal = massVal;
    }

    @Override
    public String toString() {
        return "MassModel{" +
                "id='" + id + '\'' +
                ", propertyId='" + propertyId + '\'' +
                ", massVal=" + massVal +
                '}';
    }


}
