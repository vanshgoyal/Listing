package com.example.Listing.service.impl;

import com.example.Listing.model.MassModel;
import com.example.Listing.model.ParamModel;
import com.example.Listing.model.PropertyModel;
import com.example.Listing.repository.RepositoryProperty.PropertyRepository;
import com.example.Listing.service.ParamService;
import com.example.Listing.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

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
    public MassModel saveOrUpdateProperty(PropertyModel property, String cityId) {
        if(propertyRepository.findBypropertyId(property.getPropertyId())!= null)
        {
//            Query query = new Query();
//            Update update = new Update().inc("paramType", paramModel.getParamType());
            Query query = new Query(
                    Criteria.where("propertyId").is(property.getPropertyId()));
            System.out.println(query);
            MassModel propertyMass = new MassModel();
            propertyMass.setId(property.getId());
            propertyMass.setPropertyId(property.getPropertyId());

            ParamModel paramModel = new ParamModel();
            paramModel = paramService.findByCityId(cityId);
            propertyMass.setMassVal(property.getDeposit()* paramModel.getParamDeposit());
            Update update = new Update().set("massVal", propertyMass.getMassVal());

            return mt.findAndModify(query, update, MassModel.class);
        }
        else {
            MassModel propertyMass = new MassModel();
            propertyMass.setId(property.getId());
            propertyMass.setPropertyId(property.getPropertyId());

            ParamModel paramModel = new ParamModel();
            paramModel = paramService.findByCityId(cityId);
            propertyMass.setMassVal(property.getDeposit()* paramModel.getParamDeposit());
            return propertyRepository.save(propertyMass);
        }

    }

    @Override
    public MassModel findBypropertyId(String propertyId) {

        return propertyRepository.findBypropertyId(propertyId);
    }

}
