package com.example.Listing.service;

import com.example.Listing.model.QualityScore;
import com.example.Listing.model.RelevanceScore;

public interface SavePropertyScoreService {

    void savePropertyQualityScore(String propertyId, QualityScore qualityScore);

    void savePropertyRelevanceScore(String propertyId, RelevanceScore relevanceScore);
}