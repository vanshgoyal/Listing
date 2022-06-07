package com.example.Listing.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "relevanceScore")
@Builder
@Getter
@Setter
public class RelevanceScore {
    @Id
    private String id;

    private String propertyId;

    private float relevanceScore;

    public RelevanceScore(){

    }

    public RelevanceScore(String id, String propertyId, float relevanceScore) {
        this.id = id;
        this.propertyId = propertyId;
        this.relevanceScore = relevanceScore;
    }

    @Override
    public String toString() {
        return "RelevanceScore{" +
                "id='" + id + '\'' +
                ", propertyId='" + propertyId + '\'' +
                ", relevanceScore=" + relevanceScore +
                '}';
    }


}
