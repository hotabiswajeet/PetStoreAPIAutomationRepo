Feature: Purchase flows for Pet

  @PlaceOrderPet
  Scenario: Post Request for Placing a order for a Pet
    When User calls "PlaceOrder" API with "Post" Request
    Then API call is success with statusCode as "200"
    And verify the response parameters are the same as passed in Body
