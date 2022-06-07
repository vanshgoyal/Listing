package com.example.Listing.controller;

import com.example.Listing.dto.CoefficientsDTO;
import com.example.Listing.exception.ControllerException;
import com.example.Listing.exception.CustomException;
import com.example.Listing.model.CoefficientModel;
import com.example.Listing.model.MassModel;
import com.example.Listing.repository.RepositoryProperty.PropertyRepository;
import com.example.Listing.service.CoefficientService;
import com.example.Listing.service.PropertyService;
import com.example.Listing.service.RestClientService;
import com.example.Listing.service.SavePropertyScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.Listing.utils.ObjectMapperUtils;
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
    public ResponseEntity<?> saveOrUpdateQualityScore(@PathVariable("cityId") String cityId, @PathVariable("propId") String propertyId){
        MassModel massModel = new MassModel();
        try {
            massModel = propertyService.calculateQualityScore(cityId, propertyId);
        }
        catch (CustomException e){
            return new ResponseEntity<CustomException>(e, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            ControllerException ce = new ControllerException("611","Something went wrong in controller");
            return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
        }
        savePropertyScoreService.savePropertyMass(propertyId, massModel);
        return new ResponseEntity(massModel, HttpStatus.OK);
    }

    @PostMapping (value = "/saveRelevanceScore/{propId}")
    public ResponseEntity<?> saveOrUpdateRelevanceScore(@PathVariable("propId") String propertyId){
        //MassModel massModel = propertyService.calculateRelevanceScore(propertyId);
        MassModel massModel = new MassModel();
        try {
            massModel = propertyService.calculateRelevanceScore(propertyId);
        }
        catch (CustomException e){
            return new ResponseEntity<CustomException>(e, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            ControllerException ce = new ControllerException("611","Something went wrong in controller");
            return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
        }
        savePropertyScoreService.savePropertyMass(propertyId, massModel);
        return new ResponseEntity(massModel, HttpStatus.OK);
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

    @GetMapping(value = "/byPropId/{propId}")
    public ResponseEntity<?> getPropertyMassById(@PathVariable("propId") String propId) {
        try{
            MassModel massModel =  ObjectMapperUtils.map(propertyService.findScoreBypropertyId(propId), MassModel.class);
            return new ResponseEntity(massModel, HttpStatus.OK);
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
        List<MassModel> PropArr = propertyRepository.findAll();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i=0, l=PropArr.size();i<l;i+=batch)
        {

            try {
                MassModel massModel = propertyService.calculateQualityScore("1", PropArr.get(i).getPropertyId()); // todo : relevance score
                savePropertyScoreService.savePropertyMass(PropArr.get(i).getPropertyId(), massModel);
            } catch (Exception e)
            {
                logger.error(e.getMessage());
            }
            int finalI = i;
            executorService.execute(()->{
                propertyService.executeBulkUpdate((ArrayList<MassModel>)PropArr, finalI, Math.min(finalI+batch-1,l-1));
            });
        }

    }

}
