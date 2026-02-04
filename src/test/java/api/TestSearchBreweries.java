package api;


import base.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojomodel.Brewery;
import client.BreweryClient;
import pojomodel.BreweryQuery;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
public class TestSearchBreweries extends BaseTest {

    private static final String CHICAGO = "Chicago";
    private static final String INVALID_REQUEST = "invalidRequest";

    @Test
    @DisplayName("Verify valid breweries are returned for a city")
    void testSearchBreweriesByCity() {
        log.info("Create a BreweryQuery for the city");
        BreweryQuery query = new BreweryQuery(CHICAGO);

        log.info("Execute search");
        List<Brewery> breweries = BreweryClient.searchBreweries(query);

        log.info("Verify that list is not empty");
        assertThat(breweries.size(), greaterThan(0));

        log.info("Check attributes of the first brewery");
        Brewery firstBrewery = breweries.get(0);
        assertThat(firstBrewery.getId(), notNullValue());
        assertThat(firstBrewery.getName(), notNullValue());
        assertThat(firstBrewery.getCountry(), notNullValue());
        assertThat(firstBrewery.getCity()).isEqualTo(CHICAGO);
    }

    @Test
    @DisplayName("Verify that breweries returned match the valid query city")
    void testSearchByValidQuery() {
        log.info("Create a BreweryQuery for the city");
        BreweryQuery query = new BreweryQuery(CHICAGO);

        log.info("Execute search");
        List<Brewery> breweries = BreweryClient.searchBreweries(query);

        log.info("Verify that list is not empty");
        assertThat(breweries.size(), greaterThan(0));

        log.info("Verify that all breweries have the correct city");
        breweries.forEach(brewery -> assertThat(brewery.getCity(), containsStringIgnoringCase(CHICAGO)));
    }

    @Test
    @DisplayName("Verify that pagination limits the number of results")
    void testPaginationLimitsResults() {
        log.info("Create a BreweryQuery with per_page = 200");
        BreweryQuery query = new BreweryQuery("brew", null, 200);

        log.info("Execute search");
        List<Brewery> breweries = BreweryClient.searchBreweries(query);

        log.info("Assert that the number of results does not exceed 200");
        assertThat(breweries.size(), lessThanOrEqualTo(200));
    }

    @Test
    @DisplayName("Verify that different pages return different results")
    void testDifferentPagesReturnDifferentResults() {
        log.info("Create BreweryQuery's for page_1 and page_2");
        BreweryQuery page1 = new BreweryQuery("brew", 1);
        BreweryQuery page2 = new BreweryQuery("brew", 2);
        List<Brewery> breweriesPage1 = BreweryClient.searchBreweries(page1);
        List<Brewery> breweriesPage2 = BreweryClient.searchBreweries(page2);

        String firstIdPage1 = breweriesPage1.get(0).getId();
        String firstIdPage2 = breweriesPage2.get(0).getId();

        log.info("Check that the first brewery ID on page 1 differs from page 2");
        assertThat(firstIdPage1).isNotEqualTo(firstIdPage2);

    }

    @Test
    @DisplayName("Verify that each brewery on the page has a unique ID")
    void testBreweryIdsAreUniqueOnPage() {
        log.info("Create a BreweryQuery for the city ");
        BreweryQuery query = new BreweryQuery(CHICAGO);

        log.info("Search breweries using BreweryClient");
        List<Brewery> breweries = BreweryClient.searchBreweries(query);

        Set<String> ids = new HashSet<>();

        log.info("Check that all brewery IDs are unique");
        breweries.forEach(brewery -> {
            boolean added = ids.add(brewery.getId());
            assertThat(added)
                    .as("Duplicate ID found: %s", brewery.getId())
                    .isTrue();
        });
    }

    @Test
    @DisplayName("Verify that searching with a non-existing query returns an empty list")
    void testSearchWithNonExistingQueryReturnsEmptyList() {

        log.info("Create a BreweryQuery with an invalid/non-existing value");
        BreweryQuery query = new BreweryQuery(INVALID_REQUEST);

        log.info("Execute search");
        List<Brewery> breweries = BreweryClient.searchBreweries(query);

        log.info("Verify that list is empty");
        assertThat(breweries.size(), equalTo(0));
    }
}
