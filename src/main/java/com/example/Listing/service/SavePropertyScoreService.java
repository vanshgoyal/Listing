package com.example.Listing.service;

import com.example.Listing.model.MassModel;
import com.example.Listing.model.QualityScore;
import com.example.Listing.model.RelevanceScore;

public interface SavePropertyScoreService {

   public void savePropertyQualityScore(String propertyId, QualityScore qualityScore);

   public void savePropertyRelevanceScore(String propertyId, RelevanceScore relevanceScore);
}