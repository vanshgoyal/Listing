package com.example.Listing.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "masses")
public class mass_model {
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

    public mass_model(){

    }

    public mass_model(String id, String propertyId, float massVal) {
        this.id = id;
        this.propertyId = propertyId;
        this.massVal = massVal;
    }

    @Override
    public String toString() {
        return "mass_model{" +
                "id='" + id + '\'' +
                ", propertyId='" + propertyId + '\'' +
                ", massVal=" + massVal +
                '}';
    }


}
