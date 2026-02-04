package pojomodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Brewery {
    private String id;
    private String name;
    private String city;
    private String country;
    @JsonProperty("brewery_type")
    private String breweryType;
}
