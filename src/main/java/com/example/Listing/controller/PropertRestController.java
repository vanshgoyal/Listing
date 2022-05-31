package com.example.Listing.controller;

import com.example.Listing.dto.ParamDTO;
import com.example.Listing.dto.PropertyDTO;
import com.example.Listing.model.MassModel;
import com.example.Listing.model.ParamModel;
import com.example.Listing.model.PropertyModel;
import com.example.Listing.service.ParamService;
import com.example.Listing.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.Listing.utils.ObjectMapperUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/property")
public class PropertRestController {

    //Logger logger = LoggerFactory.getLogger(HomeResource.class);
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private ParamService paramService;

    @GetMapping(value = "/")
    public String Home() {
        return "Hello world";
    }
    @PostMapping (value = "/save/{id}")
    public ResponseEntity<?> saveOrUpdateProperty(@RequestBody PropertyDTO propertyDTO, @PathVariable("id") String cityId) {
        System.out.println(propertyDTO);
        propertyService.saveOrUpdateProperty(ObjectMapperUtils.map(propertyDTO, PropertyModel.class),cityId);
        return new ResponseEntity("PropertyModel added successfully", HttpStatus.OK);
    }

    @PostMapping (value = "/saveParam")
    public ResponseEntity<?> saveOrUpdateParam(@RequestBody ParamDTO paramDTO) {
        paramService.saveOrUpdateParam(ObjectMapperUtils.map(paramDTO, ParamModel.class));
        return new ResponseEntity("Param added successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/byPropId/{propId}")
    public MassModel getPropertyById(@PathVariable("propId") String propId) {
        return ObjectMapperUtils.map(propertyService.findBypropertyId(propId), MassModel.class);
    }
    @GetMapping(value = "/byParamId/{paramId}")
    public ParamModel getParamById(@PathVariable("paramId") String paramId) {
        return ObjectMapperUtils.map(paramService.findByCityId(paramId), ParamModel.class);
    }

    @DeleteMapping(value = "/deleteParam/{id}")
    public ResponseEntity<?> deleteParamByCityId(@PathVariable("id") String cityId) {
        paramService.deleteParamModelById(paramService.findByCityId(cityId).getId());
        return new ResponseEntity("param deleted successfully", HttpStatus.OK);
    }
}
