Feature: Purchase flows for Pet

  @PlaceOrderPet
  Scenario: Post Request for Placing a order for a Pet
    When User calls "PlaceOrder" API with "Post" Request
    Then API call is success with statusCode as "200"
    And verify the response parameters are the same as passed in Body


 @FindPurchaseOrder
 Scenario: Get Purchase Order by ID
   When User calls "GetPurchaseOrder" API with "Get" Request
   Then API call is success with statusCode as "200"
   And response parameters are same as for Place Order Request


  @DeletePurchaseOrder
  Scenario: Delete Purchase Order by ID
    When User calls "DeletePurchaseOrder" API with "Delete" Request
    Then API call is success with statusCode as "200"
    And  In case of successful "DeletePurchaseOrder" request "message" in response is equal to "ID"
    And  In case of successful "DeletePurchaseOrder" request "type" in response is equal to "unknown"
    When User calls "GetPurchaseOrder" API with "Get" Request
    Then In case of successful "GetPurchaseOrder" request "message" in response is equal to "Order not found"
    And  In case of successful "GetPurchaseOrder" request "type" in response is equal to "error"



