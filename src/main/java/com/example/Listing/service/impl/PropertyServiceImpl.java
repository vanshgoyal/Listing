package com.example.Listing.service.impl;

import com.example.Listing.dto.PropertyDTO;
import com.example.Listing.model.MassModel;
import com.example.Listing.model.CoefficientModel;
import com.example.Listing.model.PropertyModel;
import com.example.Listing.repository.RepositoryProperty.PropertyRepository;
import com.example.Listing.service.*;
import com.example.Listing.utils.ObjectMapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


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
    private SavePropertyScoreService savePropertyScoreService;
    @Autowired
    private RestClientService restClientService;
    @Autowired
    private CoefficientService coefficientService;
    Logger logger = LoggerFactory.getLogger(PropertyServiceImpl.class);

    public PropertyServiceImpl() {
    }

    public void executeBulkUpdate(ArrayList<MassModel> massModelArr, int i, int l)
    {
        for(;i<=l;i++)
        {
            MassModel massModel = calculateQualityScore("1", massModelArr.get(i).getPropertyId());
            savePropertyScoreService.savePropertyMass(massModel.getPropertyId(), massModel);
        }
    }
    @Override
    public MassModel calculateQualityScore(String cityId, String propertyId)  {
        PropertyDTO propertyDTO = null;
        try {
            propertyDTO = restClientService.getPropertyDTO(propertyId);
        } catch (Exception e) {
            logger.error("Unable to find property for id :{}",propertyId);
            return null; // throw a custom exception
        }

        PropertyModel propertyParams = ObjectMapperUtils.map(propertyDTO, PropertyModel.class);
        CoefficientModel propertyCoefficients = coefficientService.findByCityId(cityId);


        float qualityScore = (ScoreCalculationService.qualityScore(propertyParams, propertyCoefficients));

//        propertyMass.setMassVal(qualityScore);
        MassModel.MassModelBuilder builder = MassModel.builder();
        builder.propertyId(propertyParams.getPropertyId());
        builder.massVal(qualityScore);
        return builder.build();
    }

    @Override
    public MassModel calculateRelevanceScore(String propertyId)  {
        PropertyDTO propertyDTO = null;
        try {
            propertyDTO = restClientService.getPropertyDTO(propertyId);
        } catch (Exception e) {
            logger.error("Unable to find property for id :{}",propertyId);
            throw new RuntimeException(e.getMessage());
        }
        PropertyModel propertyParams = ObjectMapperUtils.map(propertyDTO, PropertyModel.class);

        float qualityScore;
        try {
            qualityScore = findScoreBypropertyId(propertyId).getMassVal();
        } catch (Exception e) {
            logger.error("Unable to find Quality Score for id :{}",propertyId);
            throw new RuntimeException(e.getMessage());
        }

        MassModel propertyMass = new MassModel();
        propertyMass.setPropertyId(propertyId);
        float relevanceScore = (ScoreCalculationService.relevanceScore(propertyParams, qualityScore));
        propertyMass.setMassVal(relevanceScore);
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
