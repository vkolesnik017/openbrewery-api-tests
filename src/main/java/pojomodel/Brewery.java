package pojomodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Brewery {
    private String id;
    private String name;
    private String brewery_type;
    private String city;
    private String country;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrewery_type() {
        return brewery_type;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

}
