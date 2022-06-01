package com.example.Listing.service.impl;

import com.example.Listing.dto.PropertyDTO;
import com.example.Listing.model.FinalWeightModel;
import com.example.Listing.model.MassModel;
import com.example.Listing.model.CoefficientModel;
import com.example.Listing.model.PropertyModel;
import com.example.Listing.repository.RepositoryProperty.PropertyRepository;
import com.example.Listing.service.ParamService;
import com.example.Listing.service.PropertyService;
import com.example.Listing.service.ScoreCalculationService;
import com.example.Listing.service.RestClientService;
import com.example.Listing.utils.ObjectMapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
    private RestClientService restClientService;
    @Autowired
    private ParamService paramService;
    Logger logger = LoggerFactory.getLogger(ParamServiceImpl.class);

    @Override

    public MassModel calculatePropertyScore(String cityId, String propertyId)  {
        PropertyDTO propertyDTO = null;
        try {
            propertyDTO = restClientService.getPropertyDTO(propertyId);
        } catch (Exception e) {
            logger.error("Unable to ind property for id :{}",propertyId);
            throw new RuntimeException(e.getMessage());


        }
        PropertyModel propertyParams = ObjectMapperUtils.map(propertyDTO, PropertyModel.class);
        CoefficientModel paramModel = paramService.findByCityId(cityId);


        FinalWeightModel finalWeightModel = ObjectMapperUtils.map(paramModel, FinalWeightModel.class);
        //logger.error(String.valueOf(finalWeightModel.getDeposit()));
        finalWeightModel.setDeposit(paramModel.getCoefficientDeposit());
        finalWeightModel.setRent(paramModel.getCoefficientRent());
        finalWeightModel.setLatitude(paramModel.getCoefficientLatitude());
        finalWeightModel.setLongitude(paramModel.getCoefficientLongitude());
        logger.error(String.valueOf(finalWeightModel.getDeposit()));

        if(propertyParams.getType() != null) {
            finalWeightModel.setType(paramModel.getCoefficientType().get(propertyParams.getType()));
        }
        if(propertyParams.getBuildingType() != null) {
            finalWeightModel.setBuildingType(paramModel.getCoefficientBuildingType().get(propertyParams.getBuildingType()));
        }

        if(propertyParams.getFurnishing() != null) {
            finalWeightModel.setFurnishing(paramModel.getCoefficientFurnishing().get(propertyParams.getFurnishing()));
        }
        //logger.error("55555555555555555555555555555555555555555555555555");
        if(propertyParams.getParking() != null) {
            finalWeightModel.setParking(paramModel.getCoefficientParking().get(propertyParams.getParking()));
        }
        //logger.error("55555555555555555555555555555555555555555555555555");
        MassModel propertyMass = new MassModel();

        propertyMass.setPropertyId(propertyParams.getPropertyId());
        propertyMass.setMassVal(ScoreCalculationService.QualityScore(propertyParams, finalWeightModel));
        // relevance score

        return propertyMass;

    }

    @Override
    public MassModel findScoreBypropertyId(String propertyId) {
        MassModel massModel = propertyRepository.findBypropertyId(propertyId);
        if (massModel != null) {
            return massModel;
        } else {
            logger.error("No property by this Id available. Please add the the property");
            return new MassModel();

        }

    }

}
