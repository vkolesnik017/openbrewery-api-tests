Brewery API Autotests
---------------------
Project Overview

This project contains automated tests for the Search Breweries endpoint of
Open Brewery DB API

The goal of the project is to demonstrate how REST API test automation can be applied
using Java + REST Assured

Tech Stack
--------------------
 - Java 21
 - Maven
 - REST Assured
 - JUnit 5
 - Jackson (POJO mapping)

Implemented Test Scenarios (Automated)
--------------------------------------
The following 5 core scenarios were selected as the most valuable for the Search Breweries endpoint
and are fully automated in this project:

1. Search breweries by city returns valid objects

 - Verifies that searching by a valid city returns non-empty results

 - Validates key response fields (id, name, country, city)

2. All returned breweries match the query city

 - Ensures that every returned brewery contains the requested city in the response

3. Brewery IDs are unique on a single page

 - Verifies data integrity by checking that each brewery has a unique id

4. Pagination returns different results for different pages

 - Confirms that changing the page parameter affects the result set

5. Search with non-existing query returns empty result

 - Verifies correct behavior when no breweries match the search query

 Additional Scenarios for a Complete Test Suite (Not Implemented)
-----------------------------------------------------------------
The following scenarios should be included in a full-scale test suite:

 - Validation of per_page limits (minimum / maximum values)
 - Validation of optional fields (e.g. website_url, postal_code)
 - Response schema validation
 - Performance checks for large result sets
 - Negative tests for invalid parameter types
 - Rate limiting behavior
 - Security-related checks (unexpected input, special characters)
 - These scenarios are documented but intentionally not automated to keep the scope focused.



Test Automation Approach for List Breweries API
------------------------------------------------

The List Breweries endpoint returns a collection of breweries and supports multiple optional query parameters for filtering, sorting, and pagination.
Due to the variety of input combinations and a structured response, this endpoint is well-suited for API test automation using Java + REST Assured.

Automation Approach
-------------------

The automation approach for this endpoint is based on the following principles:

- Data-driven testing for query parameters
- Separation of concerns:
- Request building
- Response validation
- Test data management
- Reusable request specifications (base URI, headers, common configs)
-  assertions using REST Assured + Hamcrest matchers
   Test Design Techniques

The following test design techniques are applied:
-------------------------------------------------
1. Equivalence Partitioning
   - Valid vs invalid query parameter values
   - Supported vs unsupported brewery types
   - Existing vs non-existing filter values
2. Boundary Value Analysis
   - page values (e.g. 1, very large numbers)
   - per_page values (default = 50, max = 200, values above max)

3. Negative Testing
   - Invalid data types for parameters
   - Unsupported enum values
   - Incorrect formats (e.g. malformed coordinates for by_dist)