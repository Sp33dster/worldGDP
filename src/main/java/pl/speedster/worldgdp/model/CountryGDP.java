package pl.speedster.worldgdp.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter

public class CountryGDP {
    private Short year;
    private Double value;

    public void setYear(Short year) {
        this.year = year;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
