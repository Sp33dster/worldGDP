package pl.speedster.worldgdp.controller.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.speedster.worldgdp.dao.CityDAO;
import pl.speedster.worldgdp.model.City;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/cities")
public class CityAPIController {

    @Autowired
    CityDAO cityDAO;

    @GetMapping("/{countryCode}")
    public ResponseEntity<?> getCities(@PathVariable String countryCode,
                                       @RequestParam(name="pageNo", defaultValue = "1") Integer pageNo){
        try {
            return new ResponseEntity<>(cityDAO.getCities(countryCode,pageNo), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println("Error while getting cities for country: {}" +
                    countryCode+ ex);
            return new ResponseEntity<>("Error while getting cities", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{countryCode}")
    public ResponseEntity<?> addCity(@PathVariable String countryCode,
                                     @Valid @RequestBody City city){
        try {
            cityDAO.addCity(countryCode, city);
            return new ResponseEntity<>(city, HttpStatus.CREATED);
        } catch (Exception ex) {
            System.out.println("Error while adding city: {} to country: {}" +
                    city + countryCode + ex);
            return new ResponseEntity<>("Error while adding city", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{cityID}")
    public ResponseEntity<?> deleteCity(@PathVariable Long cityId){
        try {
            cityDAO.deleteCity(cityId);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            System.out.println("Error occured while deleting city : {}" + cityId + ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occured while deleting the city: " + cityId);
        }
    }
}
