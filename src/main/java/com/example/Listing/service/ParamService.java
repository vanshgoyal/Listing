package com.example.Listing.service;

import com.example.Listing.model.ParamModel;

public interface ParamService {
    ParamModel saveOrUpdateParam(ParamModel paramModel);

    ParamModel findBycityId(String cityId);
}
