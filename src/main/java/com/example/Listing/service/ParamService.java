package com.example.Listing.service;

import com.example.Listing.model.CoefficientModel;

public interface ParamService {
    CoefficientModel saveOrUpdateParam(CoefficientModel paramModel);

    void deleteParamModelById(String id);

    CoefficientModel findByCityId(String cityId);
}
