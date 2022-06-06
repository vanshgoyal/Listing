package com.example.Listing.service;

import com.example.Listing.model.CoefficientModel;

public interface CoefficientService {
    void saveOrUpdateCoefficient(CoefficientModel paramModel);

    void deleteParamModelById(String id);

    CoefficientModel findByCityId(String cityId);
}
