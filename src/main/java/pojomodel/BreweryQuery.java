package pojomodel;

import lombok.Getter;

@Getter
public class BreweryQuery {
    private String query;
    private Integer page;
    private Integer perPage;

    public BreweryQuery(String query) {
        this.query = query;
    }

    public BreweryQuery(String query, Integer page) {
        this.query = query;
        this.page = page;
    }

    public BreweryQuery(String query, Integer page, Integer perPage) {
        this.query = query;
        this.page = page;
        this.perPage = perPage;
    }
}
