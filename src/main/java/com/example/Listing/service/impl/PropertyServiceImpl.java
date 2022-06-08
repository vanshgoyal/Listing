package com.example.Listing.service.impl;

import com.example.Listing.dto.PropertyDTO;
import com.example.Listing.exception.CustomException;
import com.example.Listing.model.CoefficientModel;
import com.example.Listing.model.PropertyModel;
import com.example.Listing.model.QualityScore;
import com.example.Listing.model.RelevanceScore;
import com.example.Listing.repository.RepositoryProperty.PropertyRepository;
import com.example.Listing.repository.RepositoryProperty.RelevanceRepository;
import com.example.Listing.service.*;
import com.example.Listing.utils.ObjectMapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;


/**
 * @author ragcrix
 */
@Service
public class PropertyServiceImpl implements PropertyService {

    Logger logger = LoggerFactory.getLogger(PropertyServiceImpl.class);
    @Autowired
    private MongoTemplate mt;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private RelevanceRepository relevanceRepository;
    @Autowired
    private SavePropertyScoreService savePropertyScoreService;
    @Autowired
    private RestClientService restClientService;
    @Autowired
    private CoefficientService coefficientService;

    public PropertyServiceImpl() {
    }

    public AbstractMap.SimpleEntry<Integer, Integer> executeBulkUpdate(List<QualityScore> arr)
    {
        String PType = "Rent";
        int success=0, fail=0;
        for(QualityScore curr: arr)
        {
            RelevanceScore relevanceScore = calculateRelevanceScore(curr.getPropertyId());
            ResponseEntity<?> res =  savePropertyScoreService.savePropertyRelevanceScore(curr.getPropertyId(), relevanceScore);
            if(res.getStatusCode()== HttpStatus.OK)
                success++;
            else
                fail++;
        }
        return new AbstractMap.SimpleEntry<Integer, Integer>(success, fail);
    }

    @Override
    public QualityScore calculateQualityScore(String cityId, String propertyId, String PType) {
        PropertyDTO propertyDTO = null;
        try {
            propertyDTO = restClientService.getPropertyDTO(propertyId);
        } catch (IllegalArgumentException e) {
            logger.error("given Property id is null, please send some id to be searched :{}", propertyId);
            throw new CustomException("606", "given employee id is null, please send some id to be searched" + e.getMessage());
        } catch (java.util.NoSuchElementException e) {
            logger.error("Given property id doesnot exist in DB :{}", propertyId);
            throw new CustomException("607", "given Property id does not exist in DB " + propertyId + ". " + e.getMessage());
        } catch (Exception e) {
            logger.error("Something went wrong in Rest APi call layer while fetching QualityScore.:{}", propertyId);
            throw new CustomException("609", "Something went wrong in Service layer while fetching Quality Score of id " + propertyId + ". " + e.getMessage());
        }

        PropertyModel propertyParams = ObjectMapperUtils.map(propertyDTO, PropertyModel.class);
        CoefficientModel propertyCoefficients = coefficientService.findByCityId(cityId);

        float qualityScore = (ScoreCalculationService.qualityScore(propertyParams, propertyCoefficients));

        QualityScore.QualityScoreBuilder builder = QualityScore.builder();
        builder.propertyId(propertyParams.getPropertyId());
        builder.qualityScore(qualityScore);
        return builder.build();
    }

    @Override
    public RelevanceScore calculateRelevanceScore(String propertyId) {
        PropertyDTO propertyDTO = null;
        try {
            propertyDTO = restClientService.getPropertyDTO(propertyId);
        } catch (IllegalArgumentException e) {
            logger.error("given Property id is null, please send some id to be searched :{}", propertyId);
            throw new CustomException("606", "given employee id is null, please send some id to be searched" + e.getMessage());
        } catch (java.util.NoSuchElementException e) {
            logger.error("Given property id doesnot exist in DB :{}", propertyId);
            throw new CustomException("607", "given Property id does not exist in DB " + propertyId + ". " + e.getMessage());
        } catch (Exception e) {
            logger.error("Something went wrong in Rest APi call layer while fetching Relevance score.:{}", propertyId);
            throw new CustomException("609", "Something went wrong in Service layer while fetching relevamce Score of id " + propertyId + ". " + e.getMessage());
        }

        PropertyModel propertyParams = ObjectMapperUtils.map(propertyDTO, PropertyModel.class);

        float qualityScore;
        try {
            qualityScore = findQualityScoreBypropertyId(propertyId).getQualityScore();
        } catch (Exception e) {
            logger.error("Unable to find Quality Score for id :{}", propertyId);
            throw new CustomException("607", "No quality score found for this Id " + e.getMessage());
        }

        float relevanceScore = (ScoreCalculationService.relevanceScore(propertyParams, qualityScore));

        RelevanceScore.RelevanceScoreBuilder builder = RelevanceScore.builder();
        builder.propertyId(propertyParams.getPropertyId());
        builder.relevanceScore(relevanceScore);
        return builder.build();
    }

    @Override
    public RelevanceScore calculateOverallScore(String cityId, String propertyId, String PType) {
        PropertyDTO propertyDTO = null;
        try {
            propertyDTO = restClientService.getPropertyDTO(propertyId);
        } catch (IllegalArgumentException e) {
            logger.error("given Property id is null, please send some id to be searched :{}", propertyId);
            throw new CustomException("606", "given employee id is null, please send some id to be searched" + e.getMessage());
        } catch (java.util.NoSuchElementException e) {
            logger.error("Given property id doesnot exist in DB :{}", propertyId);
            throw new CustomException("607", "given Property id does not exist in DB " + propertyId + ". " + e.getMessage());
        } catch (Exception e) {
            logger.error("Something went wrong in Rest APi call layer while fetching QualityScore.:{}", propertyId);
            throw new CustomException("609", "Something went wrong in Service layer while fetching Quality Score of id " + propertyId + ". " + e.getMessage());
        }

        PropertyModel propertyParams = ObjectMapperUtils.map(propertyDTO, PropertyModel.class);
        CoefficientModel propertyCoefficients = coefficientService.findByCityId(cityId);

        float qualityScore = (ScoreCalculationService.qualityScore(propertyParams, propertyCoefficients));

        float relevanceScore = (ScoreCalculationService.relevanceScore(propertyParams, qualityScore));

        RelevanceScore.RelevanceScoreBuilder builder = RelevanceScore.builder();
        builder.propertyId(propertyParams.getPropertyId());
        builder.relevanceScore(relevanceScore);
        return builder.build();
    }

    @Override
    public QualityScore findQualityScoreBypropertyId(String propertyId) {
        if (propertyId.isEmpty() || propertyId.length() == 0) {
            throw new CustomException("601", "Please send a proper property id");
        }
        QualityScore qualityScore = propertyRepository.findBypropertyId(propertyId);
        if (qualityScore != null) {
            return qualityScore;
        } else {
            logger.error("No property by this Id available. Please add the the property");
            throw new RuntimeException();
        }
    }

    @Override
    public RelevanceScore findRelevanceScoreBypropertyId(String propertyId) {
        if (propertyId.isEmpty() || propertyId.length() == 0) {
            throw new CustomException("601", "Please send a proper property id");
        }
        RelevanceScore relevanceScore = relevanceRepository.findBypropertyId(propertyId);
        if (relevanceScore != null) {
            return relevanceScore;
        } else {
            logger.error("No property by this Id available. Please add the the property");
            throw new RuntimeException();
        }
    }
}