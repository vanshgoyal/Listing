package com.example.Listing.service;

import com.example.Listing.model.ParamModel;
import com.example.Listing.model.PropertyModel;

public class QualityScoreCalculation {

        public static float QualityScore(PropertyModel propertyParams, ParamModel paramModel) {
            int qualityScore=0;
            qualityScore+= propertyParams.getDeposit()* paramModel.getParamDeposit();
            return qualityScore;
        }
}
