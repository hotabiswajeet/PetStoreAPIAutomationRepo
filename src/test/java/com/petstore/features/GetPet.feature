Feature: Retrieving a Pet

  @GetPetByID
  Scenario: Doing a Get Request to Retrieve Pet by ID

    When User calls "GetPetByID" API with "Get" Request
    Then API call is success with statusCode as "200"
    And  ID in response is same as ID provided
    And  Name in response is same as Name provided


  @GetPetByStatus
  Scenario: Doing a Get Request to Retrieve Pet by Status

    When User calls "GetPetByStatus" API with "Get" Request
    Then API call is success with statusCode as "200"
    And  response contains pet with provided ID and Name


