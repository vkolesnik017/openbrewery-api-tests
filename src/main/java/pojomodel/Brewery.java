package pojomodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Brewery {
    private String id;
    private String name;
    private String brewery_type;
    private String city;
    private String country;
}
