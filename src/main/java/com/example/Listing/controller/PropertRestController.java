package com.example.Listing.controller;

import com.example.Listing.dto.ParamDTO;
import com.example.Listing.dto.PropertyDTO;
import com.example.Listing.model.ParamModel;
import com.example.Listing.model.Property;
import com.example.Listing.model.mass_model;
import com.example.Listing.service.ParamService;
import com.example.Listing.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.Listing.utils.ObjectMapperUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/property")
public class PropertRestController {

    @Autowired
    private PropertyService propertyService;
    @Autowired
    private ParamService paramService;

    @GetMapping(value = "/")
    public String Home() {
        return "Hello world";
    }
    @PostMapping (value = "/save/{id}")
    public ResponseEntity<?> saveOrUpdateProperty(@RequestBody PropertyDTO propertyDTO, @PathVariable("id") String city_id) {
        System.out.println(city_id.getClass().getSimpleName());
        System.out.println("--------------------------------------------------------------------------");
        propertyService.saveOrUpdateProperty(ObjectMapperUtils.map(propertyDTO, Property.class),city_id);
        return new ResponseEntity("Property added successfully", HttpStatus.OK);
    }

    @PostMapping (value = "/saveParam")
    public ResponseEntity<?> saveOrUpdateParam(@RequestBody ParamDTO paramDTO) {
        paramService.saveOrUpdateParam(ObjectMapperUtils.map(paramDTO, ParamModel.class));
        return new ResponseEntity("Param added successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/byPropId/{PropId}")
    public mass_model getPropertyById(@PathVariable("PropId") String PropId) {
        return ObjectMapperUtils.map(propertyService.findBypropertyId(PropId), mass_model.class);
    }
    @GetMapping(value = "/byParamId/{ParamId}")
    public ParamModel getParamById(@PathVariable("ParamId") String ParamId) {
        return ObjectMapperUtils.map(paramService.findBycityId(ParamId), ParamModel.class);
    }

    @DeleteMapping(value = "/deleteParam/{id}")
    public ResponseEntity<?> deleteParamByCityId(@PathVariable("id") String city_id) {
        paramService.deleteParamModelById(paramService.findBycityId(city_id).getId());
        return new ResponseEntity("param deleted successfully", HttpStatus.OK);
    }
}
