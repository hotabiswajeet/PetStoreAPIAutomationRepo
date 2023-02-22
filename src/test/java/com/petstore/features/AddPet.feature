Feature: Adding a Pet

@AddPet
Scenario Outline: Doing a Post Request to add a Pet into the System
Given User adds a pet with "<ID>" and "<Name>"
And User provides "<CategoryID>" and "<CategoryName>"
When User calls "AddPet" API with "Post" Request
Then API call is success with statusCode as "200"
And ID in response is same as ID provided

  Examples:
    |ID         |Name            | CategoryID         |CategoryName            |
    |130         |Nemo          | 20              |Fish                   |


