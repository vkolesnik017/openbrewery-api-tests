package api;


import base.BaseTest;
import client.BreweryClient;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojomodel.Brewery;
import pojomodel.BreweryQuery;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
public class TestSearchBreweries extends BaseTest {

    private static final String CHICAGO = "Chicago";
    private static final String INVALID_REQUEST = "invalidRequest";

    @Test
    @DisplayName("Verify that the search respects the perPage limit")
    void testSearchPerPageLimit() {
        log.info("Create a BreweryQuery with perPage = 5");
        BreweryQuery query = new BreweryQuery(CHICAGO);
        query.setPerPage(5);

        log.info("Execute search");
        List<Brewery> breweries = BreweryClient.searchBreweries(query);

        log.info("Verify that number of breweries returned does not exceed per_page limit");
        assertThat(breweries)
                .hasSizeLessThanOrEqualTo(5);
    }

    @Test
    @DisplayName("Verify that breweries returned match the valid query city")
    void testSearchByValidQuery() {
        log.info("Create a BreweryQuery for the city");
        BreweryQuery query = new BreweryQuery(CHICAGO);

        log.info("Execute search");
        List<Brewery> breweries = BreweryClient.searchBreweries(query);

        log.info("Verify that list is not empty");
        assertThat(breweries)
                .as("List of breweries should not be empty")
                .isNotEmpty();

        log.info("Verify that all breweries have the correct city");
        SoftAssertions softly = new SoftAssertions();
        breweries.forEach(brewery ->
                softly.assertThat(brewery.getCity())
                        .as("City for brewery id=%s", brewery.getId())
                        .containsIgnoringCase(CHICAGO)
        );
        softly.assertAll();
    }

    @Test
    @DisplayName("Verify that pagination limits the number of results")
    void testPaginationLimitsResults() {
        log.info("Create a BreweryQuery with per_page = 200");
        BreweryQuery query = new BreweryQuery("brew", null, 200);

        log.info("Execute search");
        List<Brewery> breweries = BreweryClient.searchBreweries(query);

        log.info("Assert that the number of results does not exceed 200");
        assertThat(breweries)
                .as("Number of breweries should not exceed 200")
                .hasSizeLessThanOrEqualTo(200);
    }

    @Test
    @DisplayName("Verify pagination returns different results for different pages")
    void testPaginationReturnsDifferentResults() {
        log.info("Create BreweryQuery's for page_1 and page_2");
        BreweryQuery page1 = new BreweryQuery("brew", 1);
        BreweryQuery page2 = new BreweryQuery("brew", 2);
        List<Brewery> breweriesPage1 = BreweryClient.searchBreweries(page1);
        List<Brewery> breweriesPage2 = BreweryClient.searchBreweries(page2);

        log.info("Check that the pages are completely different");
        assertThat(breweriesPage1)
                .as("Pages should contain different breweries")
                .isNotEqualTo(breweriesPage2);
    }

    @Test
    @DisplayName("Verify that each brewery on the page has a unique ID")
    void testBreweryIdsAreUniqueOnPage() {
        log.info("Create a BreweryQuery for the city ");
        BreweryQuery query = new BreweryQuery(CHICAGO);

        log.info("Search breweries using BreweryClient");
        List<Brewery> breweries = BreweryClient.searchBreweries(query);

        log.info("Extract brewery IDs");
        List<String> ids = breweries.stream()
                .map(Brewery::getId)
                .toList();

        log.info("Put IDs into a Set to remove duplicates");
        Set<String> uniqueIds = new HashSet<>(ids);

        log.info("Verify that all brewery IDs are unique");
        assertThat(uniqueIds)
                .hasSameSizeAs(ids)
                .as("There are duplicate brewery IDs on the page");
    }

    @Test
    @DisplayName("Verify that searching with a non-existing query returns an empty list")
    void testSearchWithNonExistingQueryReturnsEmptyList() {

        log.info("Create a BreweryQuery with an invalid/non-existing value");
        BreweryQuery query = new BreweryQuery(INVALID_REQUEST);

        log.info("Execute search");
        List<Brewery> breweries = BreweryClient.searchBreweries(query);

        log.info("Verify that list is empty");
        assertThat(breweries)
                .as("List of breweries should be empty")
                .isEmpty();
    }
}
