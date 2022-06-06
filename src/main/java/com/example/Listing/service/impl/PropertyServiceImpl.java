package com.example.Listing.service.impl;

import com.example.Listing.dto.PropertyDTO;
import com.example.Listing.model.MassModel;
import com.example.Listing.model.CoefficientModel;
import com.example.Listing.model.PropertyModel;
import com.example.Listing.repository.RepositoryProperty.PropertyRepository;
import com.example.Listing.service.CoefficientService;
import com.example.Listing.service.PropertyService;
import com.example.Listing.service.ScoreCalculationService;
import com.example.Listing.service.RestClientService;
import com.example.Listing.service.SavePropertyScoreService;
import com.example.Listing.utils.ObjectMapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;


import java.util.ArrayList;

import static com.example.Listing.service.QualityScoreCalculation.QualityScore;
import static java.lang.Integer.parseInt;

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
    private CoefficientService coefficientService;
    Logger logger = LoggerFactory.getLogger(PropertyServiceImpl.class);
    @Autowired
    private PropertyService propertyService;

    @Autowired
    private SavePropertyScoreService savePropertyScoreService;
    Logger logger = LoggerFactory.getLogger(LoggingController.class);

    public void executeBulkUpdate(ArrayList<MassModel> massModelArr, int i, int l)
    {
        for(;i<=l;i++)
        {
            MassModel massModel = propertyService.calculatePropertyScore("1", massModelArr.get(i).getPropertyId());
            savePropertyScoreService.savePropertyMass(massModel.getPropertyId(), massModel);
        }
    }

    public PropertyServiceImpl() {
    }

    @Override

    public MassModel calculateQualityScore(String cityId, String propertyId)  {
        PropertyDTO propertyDTO = null;
        try {
            propertyDTO = restClientService.getPropertyDTO(propertyId);
        } catch (Exception e) {
            logger.error("Unable to find property for id :{}",propertyId);
            throw new RuntimeException(e.getMessage());
        }

        PropertyModel propertyParams = ObjectMapperUtils.map(propertyDTO, PropertyModel.class);
        CoefficientModel propertyCoefficients = coefficientService.findByCityId(cityId);
        MassModel propertyMass = new MassModel();

        propertyMass.setPropertyId(propertyParams.getPropertyId());

        float qualityScore = (ScoreCalculationService.qualityScore(propertyParams, propertyCoefficients));

        propertyMass.setMassVal(qualityScore);
        return propertyMass;
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
            logger.error("Unable to find property for id :{}",propertyId);
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
            logger.error("No Coefficients by this Id available. Please add the the coefficients");
            return new MassModel();
        }
    }
}
