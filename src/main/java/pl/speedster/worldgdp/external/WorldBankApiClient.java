package pl.speedster.worldgdp.external;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.speedster.worldgdp.model.Country;
import pl.speedster.worldgdp.model.CountryGDP;

public class WorldBankApiClient {

    String GDP_URL = "http://api.worldbank.org/countries/%s/indicators/NY.GDP.MKTP.CD?"
            + "format=json&date=2008:2018";

    public List<CountryGDP> getGDP(String countryCode) throws ParseException {
        RestTemplate worldBankRestTmplt = new RestTemplate();
        ResponseEntity<String> responseEntity
                = worldBankRestTmplt.getForEntity(String.format(GDP_URL, countryCode), String.class);
        //the second element is the actual data and its an array of object
        JSONParser parser = new JSONParser();
        JSONArray responseData = (JSONArray) parser.parse(responseEntity.getBody());
        JSONArray countryDataArray = (JSONArray) responseData.get(1);

        List<CountryGDP> data = new ArrayList<>();
        JSONObject countryDataYearWise = null;
        for (int index = 0; index < countryDataArray.size(); index++) {
            countryDataYearWise = (JSONObject) countryDataArray.get(index);

            String valueStr = "0";
            if (countryDataYearWise.get("value") != null) {
                valueStr = countryDataYearWise.get("value").toString();
            }
            String yearStr = countryDataYearWise.get("date").toString();
            CountryGDP gdp = new CountryGDP();
            gdp.setValue(valueStr != null ? Double.valueOf(valueStr) : null);
            gdp.setYear(Short.valueOf(yearStr));
            data.add(gdp);
        }
        return data;
    }
}
