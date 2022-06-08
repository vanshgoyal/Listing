package com.example.Listing.service;

import com.example.Listing.model.MassModel;
import com.example.Listing.model.QualityScore;
import com.example.Listing.model.RelevanceScore;

import java.util.ArrayList;
import java.util.List;

public interface PropertyService {
// rename to ScoringService
    QualityScore calculateQualityScore(String cityId, String propertyId, String PType);

    RelevanceScore calculateRelevanceScore(String propertyId);

    QualityScore findQualityScoreBypropertyId(String propertyId);

    RelevanceScore findRelevanceScoreBypropertyId(String propertyId);
    void executeBulkUpdate(List<QualityScore> arr);
}
