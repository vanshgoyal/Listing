package com.example.Listing.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "qualityScore")
@Builder
@Getter
@Setter
public class QualityScore {
    @Id
    private String id;

    private String propertyId;

    private float qualityScore;

    public QualityScore() {

    }

    public QualityScore(String id, String propertyId, float qualityScore) {
        this.id = id;
        this.propertyId = propertyId;
        this.qualityScore = qualityScore;
    }

    @Override
    public String toString() {
        return "QualityScore{" +
                "id='" + id + '\'' +
                ", propertyId='" + propertyId + '\'' +
                ", qualityScore=" + qualityScore +
                '}';
    }


}
