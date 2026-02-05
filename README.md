Brewery API Autotests
---------------------
This project contains a small set of automated API tests for the  
**Search Breweries** endpoint of the OpenBreweryDB API.

The goal of this project is to show how REST API testing can be organized
using Java and REST Assured, with readable tests and simple structure,
not on full API coverage.

Tech Stack
--------------------
- Java 21
- Maven
- REST Assured
- JUnit 5
- AssertJ (including SoftAssertions)
- Jackson (POJO mapping)
- SLF4J logging

Implemented Test Scenarios (Automated)
--------------------------------------
For the **Search Breweries** endpoint, I selected several scenarios that
cover the part of the method's functionality.

The following scenarios are automated for the **Search Breweries** endpoint.

 1. Search respects per_page limit
- Executes search with a specific perPage value
- Verifies that the number of returned breweries does not exceed the limit

 2. Search by valid city returns matching breweries
- Executes search using a valid city name
- Verifies that the response is not empty
- Checks that all returned breweries belong to the requested city

 3. Pagination does not exceed maximum result limit
- Executes search with a large perPage value
- Verifies that the API does not return more items than allowed

4. Different pages return different results
- Executes the same search query with different page values
- Verifies that result sets for different pages are not identical

5. Each brewery on page has unique ID
- Extracts all brewery IDs from the response
- Verifies that there are no duplicate IDs on a single page

6. Search with non-existing query returns empty result
- Executes search with a query value that should not match any breweries
- Verifies that the API returns an empty list

Additional Scenarios (Not Implemented)
-----------------------------------------------------------------
The following scenarios should be included in a full-scale test suite:

- Boundary checks for perPage (minimum, maximum, values above max)
- Validation of optional response fields
- Negative tests for invalid parameter types
- Special characters and unexpected input values
- Basic performance checks for large result sets


Test Automation Approach for List Breweries API
------------------------------------------------

The **List Breweries** endpoint returns a list of breweries and allows using
different query parameters. Because of this, it can be covered with
automated API tests that validate filtering, pagination and response data.

Automation Approach
-------------------
For this endpoint, the goal is to keep tests simple and readable.
Request parameters and responses can be mapped to POJOs. Common request configuration (base URL, logging) should be reused to
reduce duplication.

Most tests would focus on checking that filtering and pagination work as expected
and that the returned data is correct.

Test Design Techniques
-------------------------------------------------
1. Equivalence Partitioning
   - Valid vs invalid query parameter values
   - Existing vs non-existing filter values
2. Boundary Value Analysis
   - page values (e.g. 1, very large numbers)
   - perPage values (default = 50, max = 200, values above max)

3. Negative Testing
   - Invalid data types for parameters
   - Unsupported values
   - Incorrect formats 

Estimated Effort
----------------------------------------------
- Test design & analysis: ~3-4 hours
- Test implementation: ~6-8 hours
- Refactoring & stabilization: ~2 hours

  Total: 2 working days