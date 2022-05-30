package com.example.Listing.service.impl;

import com.example.Listing.model.ParamModel;
import com.example.Listing.model.Property;
import com.example.Listing.model.mass_model;
import com.example.Listing.repository.RepositoryProperty.PropertyRepository;
import com.example.Listing.service.ParamService;
import com.example.Listing.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @author ragcrix
 */
@Service
public class  PropertyServiceImpl implements PropertyService {

    @Autowired
    private MongoTemplate mt;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private ParamService paramService;

    @Override
    public mass_model saveOrUpdateProperty(Property property) {
        if(propertyRepository.findBypropertyId(property.getPropertyId())!= null)
        {
//            Query query = new Query();
//            Update update = new Update().inc("paramType", paramModel.getParamType());
            Query query = new Query(
                    Criteria.where("propertyId").is(property.getPropertyId()));
            System.out.println(query);
            mass_model property_mass = new mass_model();
            property_mass.setId(property.getId());
            property_mass.setPropertyId(property.getPropertyId());

            ParamModel paramModel = new ParamModel();
            paramModel = paramService.findBycityId("1");
            property_mass.setMassVal(property.getDeposit()* paramModel.getParamDeposit());
            Update update = new Update().set("massVal", property_mass.getMassVal());

            return mt.findAndModify(query, update, mass_model.class);
        }
        else {
            mass_model property_mass = new mass_model();
            property_mass.setId(property.getId());
            property_mass.setPropertyId(property.getPropertyId());

            ParamModel paramModel = new ParamModel();
            paramModel = paramService.findBycityId("1");
            property_mass.setMassVal(property.getDeposit()* paramModel.getParamDeposit());
            return propertyRepository.save(property_mass);
        }

    }

    @Override
    public mass_model findBypropertyId(String propertyId) {

        return propertyRepository.findBypropertyId(propertyId);
    }

}
