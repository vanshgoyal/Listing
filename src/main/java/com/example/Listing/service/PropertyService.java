package com.example.Listing.service;

import com.example.Listing.model.QualityScore;
import com.example.Listing.model.RelevanceScore;
import javafx.util.Pair;

import java.util.List;

public interface PropertyService {
    // rename to ScoringService
    QualityScore calculateQualityScore(String cityId, String propertyId, String PType);

    RelevanceScore calculateOverallScore(String cityId, String propertyId, String PType);

    RelevanceScore calculateRelevanceScore(String propertyId);

    QualityScore findQualityScoreBypropertyId(String propertyId);

    RelevanceScore findRelevanceScoreBypropertyId(String propertyId);
    Pair<Integer, Integer> executeBulkUpdate(List<QualityScore> arr);
}
