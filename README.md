# PetStoreAPIAutomationRepo

-----Code committed in master branch----------
- FrameWork used: BDD as Cucumber
- Coding Language: Java
- Test Framework: Junit
- API Automation Library: Rest Assured
- Build: Maven
- Test Root Folder contains (package info)--

Features automated as per Feature File:

- AddPet : Adding a Pet into the System
- GetPet: Fetching Added Pet by Status and Added Pet ID
- PurchaseFlowPet: Place Order for pet, find the Purchase Order and Delete Purchase Order

StepDefinitions:

- Step implementation of Features and Scenarios in the feature file
- Hooks class: Specified prerequisites in order for atomic run of tests

TestRunner:

- Runner file with configurable Run options


Main Root Folder contains (package info)--

- pojo: Tightly encapsulated POJO classes for JSON serialization and Deserialization purpose
- requestpaths: Created enum class for defining request resource paths
- specbuilders : Created for base request specification and Logging requests functionality. A config file is created as Data source
- utility : created a utility class for setting values to Pojo objects and Date utility methods
- Reporting: Added plugins for Cucumber net.masterthought reporting, provides a clear view of Run status

Run Instructions:

- Git Repo to be cloned to local, switch to master branch
- For a overall suite run In cmd enter the project directory and hit the command : mvn compile to compile the project and for testrun
- For Parallel Run and run with report In cmd enter the project directory and hit the command : mvn compile to compile the project and for testrun
- For atomic test tun: In cmd enter the project directory and hit the command : mvn test verify -Dcucumber.filter.tags="<@tagName>"

To view the report:
- Go to Target folder inside project directory
- inside that go to cucumber-html-reports folder
- Open the overview-features.html file

