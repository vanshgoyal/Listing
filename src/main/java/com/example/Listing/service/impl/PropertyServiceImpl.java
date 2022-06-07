package com.example.Listing.service.impl;

import com.example.Listing.dto.PropertyDTO;
import com.example.Listing.exception.CustomException;
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
        } catch (IllegalArgumentException e) {
            logger.error("given Property id is null, please send some id to be searched :{}",propertyId);
            throw new CustomException("606","given employee id is null, please send some id to be searched" + e.getMessage());
        }
        catch (java.util.NoSuchElementException e){
            logger.error("Given property id doesnot exist in DB :{}",propertyId);
            throw new CustomException("607","given Property id does not exist in DB "+propertyId+". " + e.getMessage());
        }catch (Exception e) {
            logger.error("Something went wrong in Rest APi call layer while fetching QualityScore.:{}",propertyId);
            throw new CustomException("609","Something went wrong in Service layer while fetching Quality Score of id " +propertyId+". " + e.getMessage());
        }

        PropertyModel propertyParams = ObjectMapperUtils.map(propertyDTO, PropertyModel.class);
        CoefficientModel propertyCoefficients = coefficientService.findByCityId(cityId);

        float qualityScore = (ScoreCalculationService.qualityScore(propertyParams, propertyCoefficients));

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
        } catch (IllegalArgumentException e) {
            logger.error("given Property id is null, please send some id to be searched :{}",propertyId);
            throw new CustomException("606","given employee id is null, please send some id to be searched" + e.getMessage());
        }
        catch (java.util.NoSuchElementException e){
            logger.error("Given property id doesnot exist in DB :{}",propertyId);
            throw new CustomException("607","given Property id does not exist in DB "+propertyId+". " + e.getMessage());
        }catch (Exception e) {
            logger.error("Something went wrong in Rest APi call layer while fetching QualityScore.:{}",propertyId);
            throw new CustomException("609","Something went wrong in Service layer while fetching Quality Score of id " +propertyId+". " + e.getMessage());
        }

        PropertyModel propertyParams = ObjectMapperUtils.map(propertyDTO, PropertyModel.class);

        float qualityScore;
        try {
            qualityScore = findScoreBypropertyId(propertyId).getMassVal();
        } catch (Exception e) {
            logger.error("Unable to find Quality Score for id :{}",propertyId);
            throw new CustomException("607","No quality score found for this Id "+e.getMessage());
        }

        MassModel propertyMass = new MassModel();
        propertyMass.setPropertyId(propertyId);
        float relevanceScore = (ScoreCalculationService.relevanceScore(propertyParams, qualityScore));
        propertyMass.setMassVal(relevanceScore);
        return propertyMass;
    }

    @Override
    public MassModel findScoreBypropertyId(String propertyId) {
        if(propertyId.isEmpty() || propertyId.length() == 0 ) {
            throw new CustomException("601","Please send a proper property id");
        }
        MassModel massModel = propertyRepository.findBypropertyId(propertyId);
        if (massModel != null) {
            return massModel;
        } else {
            logger.error("No property by this Id available. Please add the the property");
            throw new RuntimeException();
        }
    }
}
