package com.example.Listing.service;

import com.example.Listing.model.FinalWeightModel;
import com.example.Listing.model.PropertyModel;

public class ScoreCalculationService {

        public static float QualityScore(PropertyModel propertyParams, FinalWeightModel coefficientModel) {
            int qualityScore=0;
            qualityScore+= propertyParams.getDeposit()* coefficientModel.getDeposit();
            return qualityScore;
        }
}
