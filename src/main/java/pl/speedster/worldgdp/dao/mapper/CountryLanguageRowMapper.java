package pl.speedster.worldgdp.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import pl.speedster.worldgdp.model.CountryLanguage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryLanguageRowMapper implements RowMapper<CountryLanguage> {
    public CountryLanguage mapRow(ResultSet rs, int rowNum) throws SQLException{
        CountryLanguage countryLanguage = new CountryLanguage();
        countryLanguage.setCountryCode(rs.getString("countrycode"));
        countryLanguage.setIsOfficial(rs.getString("isOfficial"));
        countryLanguage.setLanguage(rs.getString("language"));
        countryLanguage.setPercentage(rs.getDouble("percentage"));
        return countryLanguage;
    }
}
