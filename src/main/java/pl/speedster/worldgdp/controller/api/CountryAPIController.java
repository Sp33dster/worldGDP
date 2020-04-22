package pl.speedster.worldgdp.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.speedster.worldgdp.dao.CountryDAO;
import pl.speedster.worldgdp.external.WorldBankApiClient;
import pl.speedster.worldgdp.model.Country;

import javax.validation.Valid;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/countries")
@Slf4j
public class CountryAPIController {

    @Autowired
    CountryDAO countryDAO;
    @Autowired
    WorldBankApiClient worldBankApiClient;

    @GetMapping
    public ResponseEntity<?> getCountries(
            @RequestParam(name="search", required = false) String searchTerm,
            @RequestParam(name="continent", required = false) String continent,
            @RequestParam(name="region", required = false) String region,
            @RequestParam(name = "pageNo", required = false) Integer pageNo
    ){
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("search", searchTerm);
            params.put("continent", continent);
            params.put("region", region);
            if ( pageNo != null ) {
                params.put("pageNo", pageNo.toString());
            }

            List<Country> countries = countryDAO.getCountries(params);
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("list", countries);
            response.put("count", countryDAO.getCountriesCount(params));
            return ResponseEntity.ok(response);
        }catch(Exception ex) {
            System.out.println("Error while getting countries"+ ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while getting countries");
        }
    }

    @PostMapping(value = "/{countryCode}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> editCountry(
            @PathVariable String countryCode, @Valid @RequestBody Country country ){
        try {
            countryDAO.editCountryDetail(countryCode, country);
            Country countryFromDb = countryDAO.getCountryDetail(countryCode);
            return ResponseEntity.ok(countryFromDb);
        }catch(Exception ex) {
            System.out.println("Error while editing the country: {} with data: {}"+ countryCode+ country+ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while ediiting the country");
        }
    }

    @GetMapping("/{countryCode}/gdp")
    public ResponseEntity<?> getGDP(@PathVariable String countryCode){
        try {
            return ResponseEntity.ok(worldBankApiClient.getGDP(countryCode));
        }catch(Exception ex) {
            System.out.println("Error while getting GDP for country: {}"+ countryCode+ ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while getting the GDP");
        }
    }
}
