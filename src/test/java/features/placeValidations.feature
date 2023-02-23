Feature: Validate Place API's

  @AddPlace
  Scenario Outline: Verify if place is successfully added with AddPlaceAPI
    Given Add Place Payload with "<name>" "<language>" "<address>"
    When user calls "addPlaceAPI" with "post" http request
    Then the API call is successful with status code 200
    And "status" in response body "OK"
    And "scope" in response body "APP"
    And Verify place_Id created maps to "<name>" using "getPlaceAPI"

  Examples:
    | name    | language | address            |
    | AAhouse | English  | World cross center |
   # | BBhouse | Bulgarian| 6th september 126  |


  @DeletePlace
  Scenario: Verify if Delete Place functionality is working
    Given DeletePlace Payload
    When user calls "deletePlaceAPI" with "post" http request
    Then the API call is successful with status code 200
    And "status" in response body "OK"