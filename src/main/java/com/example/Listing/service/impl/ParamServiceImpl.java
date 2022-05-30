package com.example.Listing.service.impl;

import com.example.Listing.model.ParamModel;
import com.example.Listing.model.Property;
import com.example.Listing.model.mass_model;
import com.example.Listing.repository.RepositoryParam.ParamRepository;
import com.example.Listing.repository.RepositoryProperty.PropertyRepository;
import com.example.Listing.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ragcrix
 */
@Service
public class ParamServiceImpl  implements ParamService {
    @Autowired
    private ParamRepository paramRepository;

    @Override
    public ParamModel saveOrUpdateParam(ParamModel paramModel) {
        return paramRepository.save(paramModel);
    }

    @Override
    public ParamModel findBycityId(String propertyId) {

        return paramRepository.findBycityId(propertyId);
    }
}
