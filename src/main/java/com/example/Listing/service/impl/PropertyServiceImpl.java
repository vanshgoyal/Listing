package com.example.Listing.service.impl;

import com.example.Listing.dto.PropertyDTO;
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
    Logger logger = LoggerFactory.getLogger(PropertyServiceImpl.class);

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
        CoefficientModel propertyCoefficients = paramService.findByCityId(cityId);
        MassModel propertyMass = new MassModel();

        propertyMass.setPropertyId(propertyParams.getPropertyId());

        float quality_score = (ScoreCalculationService.qualityScore(propertyParams, propertyCoefficients));
        float relevanceScore = (ScoreCalculationService.relevanceScore(propertyParams, quality_score));

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
