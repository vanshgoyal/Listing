package com.example.Listing.controller;

import com.example.Listing.dto.CoefficientsDTO;
import com.example.Listing.exception.ControllerException;
import com.example.Listing.exception.CustomException;
import com.example.Listing.model.CoefficientModel;
import com.example.Listing.model.MassModel;
import com.example.Listing.model.QualityScore;
import com.example.Listing.model.RelevanceScore;
import com.example.Listing.repository.RepositoryProperty.PropertyRepository;
import com.example.Listing.service.CoefficientService;
import com.example.Listing.service.PropertyService;
import com.example.Listing.service.RestClientService;
import com.example.Listing.service.SavePropertyScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.Listing.utils.ObjectMapperUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@RestController
@RequestMapping("/property")
public class PropertyRestController {

    Logger logger = LoggerFactory.getLogger(PropertyRestController.class);
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private RestClientService restClientService;
    @Autowired
    private SavePropertyScoreService savePropertyScoreService;
    @Autowired
    PropertyRepository propertyRepository;
    @Autowired
    private CoefficientService coefficientService;
    private Class<? extends org.json.simple.JSONObject> JSONObject;

    @GetMapping(value = "/")
    public String  Home() throws Exception {
        return "Hello World";
    }

    // propertyType for extensibility
    @PostMapping (value = "/saveQualityScore/{cityId}/{propId}")
    public ResponseEntity<?> saveOrUpdateQualityScore(@PathVariable("cityId") String cityId, @PathVariable("propId") String propertyId,@RequestParam("PType") String PType){
        System.out.println(PType);
        QualityScore qualityScore = new QualityScore();
        try {
            qualityScore = propertyService.calculateQualityScore(cityId, propertyId, PType);
        }
        catch (CustomException e){
            return new ResponseEntity<CustomException>(e, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            ControllerException ce = new ControllerException("611","Something went wrong in controller");
            return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
        }
        savePropertyScoreService.savePropertyQualityScore(propertyId, qualityScore);
        return new ResponseEntity(qualityScore, HttpStatus.OK);
    }

    @PostMapping (value = "/saveRelevanceScore/{propId}")
    public ResponseEntity<?> saveOrUpdateRelevanceScore(@PathVariable("propId") String propertyId){
        RelevanceScore relevanceScore = new RelevanceScore();
        try {
            relevanceScore = propertyService.calculateRelevanceScore(propertyId);
        }
        catch (CustomException e){
            return new ResponseEntity<CustomException>(e, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            ControllerException ce = new ControllerException("611","Something went wrong in controller");
            return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
        }
        savePropertyScoreService.savePropertyRelevanceScore(propertyId, relevanceScore);
        return new ResponseEntity(relevanceScore, HttpStatus.OK);
    }

    @PostMapping (value = "/saveParam")
    public ResponseEntity<?> saveOrUpdateParam(@RequestBody CoefficientsDTO coefficientsDTO) {
        try {
            CoefficientModel coefficientModel = ObjectMapperUtils.map(coefficientsDTO, CoefficientModel.class);
            coefficientService.saveOrUpdateCoefficient(ObjectMapperUtils.map(coefficientsDTO, CoefficientModel.class));
            return new ResponseEntity(coefficientModel, HttpStatus.OK);
        }catch (CustomException e) {
            ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
            return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            ControllerException ce = new ControllerException("611","Something went wrong in controller");
            return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "/qualityScorebyPropId/{propId}")
    public ResponseEntity<?> getQualityScoreById(@PathVariable("propId") String propId) {
        try{
            QualityScore qualityScore =  ObjectMapperUtils.map(propertyService.findQualityScoreBypropertyId(propId), QualityScore.class);
            return new ResponseEntity(qualityScore, HttpStatus.OK);
        }
        catch (CustomException e) {
            ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
            return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            ControllerException ce = new ControllerException("607","No property with this id found");
            return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "/relevanceScorebyPropId/{propId}")
    public ResponseEntity<?> getRelevanceScoreById(@PathVariable("propId") String propId) {
        try{
            RelevanceScore relevanceScore =  ObjectMapperUtils.map(propertyService.findRelevanceScoreBypropertyId(propId), RelevanceScore.class);
            return new ResponseEntity(relevanceScore, HttpStatus.OK);
        }
        catch (CustomException e) {
            ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
            return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            ControllerException ce = new ControllerException("607","No property with this id found");
            return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "/byParamId/{paramId}")
    public ResponseEntity<?> getCoefficientByCityId(@PathVariable("paramId") String paramId) {
        try{
            CoefficientModel coefficientModel =  ObjectMapperUtils.map(coefficientService.findByCityId(paramId), CoefficientModel.class);
            return new ResponseEntity(coefficientModel, HttpStatus.OK);
        }
        catch (CustomException e) {
            ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
            return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            ControllerException ce = new ControllerException("607","No coefficients with this cityId found");
            return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/deleteParam/{id}")
    public ResponseEntity<?> deleteParamByCityId(@PathVariable("id") String cityId) {
        try {
            CoefficientModel coefficientModel = coefficientService.findByCityId(cityId);
            coefficientService.deleteParamModelById(coefficientModel.getId());
            return new ResponseEntity("param deleted successfully", HttpStatus.OK);
        }catch (CustomException e) {
            ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
            return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            ControllerException ce = new ControllerException("611","Some error happened in service layer");
            return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/bulkUpdate")
    // output statistics with failure and success count
    public void bulkUpdate (@RequestParam("batch") int batch)
    { // List<String> prpertyId ; cityId
//        String PType = "Rent";
//        List<QualityScore> PropArr = propertyRepository.findAll();
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        for(int i=0, l=PropArr.size();i<l;i+=batch)
//        {
//
//            try {
//                QualityScore qualityScore = propertyService.calculateQualityScore("1", PropArr.get(i).getPropertyId(),PType); // todo : relevance score
//                savePropertyScoreService.savePropertyQualityScore(PropArr.get(i).getPropertyId(), qualityScore);
//            } catch (Exception e)
//            {
//                logger.error(e.getMessage());
//            }
//            int finalI = i;
//            executorService.execute(()->{
//                propertyService.executeBulkUpdate((ArrayList<QualityScore>)PropArr, finalI, Math.min(finalI+batch-1,l-1));
//            });
//        }
        int pageNumber=0;
        Page<QualityScore> page;
        do{
            page = propertyRepository.findAll(PageRequest.of(pageNumber, batch));
            List<QualityScore> PropArr = page.getContent();
            propertyService.executeBulkUpdate(PropArr);
        }while(!page.isLast());

    }

}