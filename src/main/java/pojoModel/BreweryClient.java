package pojoModel;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

import java.util.List;

public class BreweryClient {
    private static final String BASE_URL = "https://api.openbrewerydb.org/v1";

    public static RequestSpecification createSpecification(BreweryQuery query) {

        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .log(LogDetail.ALL);

        if (query.getQuery() != null) {
            builder.addQueryParam("query", query.getQuery());
        }
        if (query.getPage() != null) {
            builder.addQueryParam("page", query.getPage());
        }
        if (query.getPerPage() != null) {
            builder.addQueryParam("per_page", query.getPerPage());
        }

        return builder.build();
    }

    public static List<Brewery> searchBreweries(BreweryQuery queryParams) {
        return RestAssured
                .given()
                .spec(createSpecification(queryParams))
                .when()
                .get("/breweries/search")
                .then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<Brewery>>() {
                });
    }
}
