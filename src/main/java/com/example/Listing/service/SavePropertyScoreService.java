package com.example.Listing.service;

import com.example.Listing.model.QualityScore;
import com.example.Listing.model.RelevanceScore;
import org.springframework.http.ResponseEntity;

public interface SavePropertyScoreService {

    void savePropertyQualityScore(String propertyId, QualityScore qualityScore);

    ResponseEntity<?> savePropertyRelevanceScore(String propertyId, RelevanceScore relevanceScore);
}