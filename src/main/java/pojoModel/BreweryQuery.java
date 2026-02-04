package pojoModel;

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

    public String getQuery() {
        return query;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getPerPage() {
        return perPage;
    }
}
