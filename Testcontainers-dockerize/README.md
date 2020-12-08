# Testcontainers dockerize your test environment

The goal for this repo is to explore how to enable testconatiners to be triggered with every test class. 
I will also try to write a proper java HTTP agent to allow a simple use case instead of the overly complicated java http clients.

# Expected behaviour

For this project, the idea is to get a testcontainer running in the testing class and enable the tests to interact with it.


# How to run

### Step1 Setup dependencies

Make sure that ```build.gradle``` have the following dependencies.
For more info:
- Junit5 gradle dependencies: https://www.testcontainers.org/quickstart/junit_5_quickstart/
- Mysql testcontainer: https://www.testcontainers.org/modules/databases/mysql/
- Mysql driver: https://mvnrepository.com/artifact/mysql/mysql-connector-java
  - To show dependency; click on a version >> Gradle
```shell
dependencies {
    # Match the latest available version.
    def junitJupiterVersion = '5.6.0'
    # The below will make sure that junit5 and testcontainers are ready to use
    testCompile "org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion"
    testCompile "org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion"
    testRuntimeOnly "org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion"
    testCompile "org.testcontainers:testcontainers:1.15.0"
    testCompile "org.testcontainers:junit-jupiter:1.15.0"
    testCompile "org.testcontainers:mysql:1.15.0"
    testImplementation "org.testcontainers:junit-jupiter:1.15.0"
    # To enable logging
    compile"org.slf4j:slf4j-simple:1.7.30"
    # The mysql driver "database driver are not provided by testcontainers"
    compile group: 'mysql', name: 'mysql-connector-java', version: '8.0.22'
}
```


### Step2 Run the tests
```shell
./gradlew clean test
```


## General notes  

- For this project I am using ```TestRestTemplate``` from ```springframework``` to handle requests as it is a much better fit for my needs.  
- The project is using ```jsonij``` for json parsing
- For the POST requests, I found out that it is possible to supply a valid "string" that is a json viable.
- A good endpoint to use as a practise for GET/PSOT/etc requests is: https://jsonplaceholder.typicode.com/  
- A good source on using ```TestRestTemplate``` standalone without the whole framework is: TestRestTemplate
- To get the ```TestRestTemplate``` dependency, go to https://jar-download.com/artifacts/org.springframework.boot/spring-boot-test/2.4.0 and chose the needed version.
- A good github resource on ```TestRestTemplate``` https://github.com/eugenp/tutorials/tree/master/spring-resttemplate
