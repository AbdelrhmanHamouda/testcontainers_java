import jsonij.Value;
import jsonij.parser.JSONParser;
import jsonij.parser.ParserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.net.ssl.HttpsURLConnection;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class TestingClass {

    //Once per test class
    @Container
    private static final MySQLContainer mysql = new MySQLContainer("mysql:latest")
            .withDatabaseName("Nexthink_db")
            .withUsername("MNT_backend")
            .withPassword("FunnyPass");

    @Test
    @DisplayName("Check Junit5 is running")
    public void test1() {
        // Check that Junit is working
        assertTrue(true);
    }

    @Test
    @DisplayName("Check docker container is running")
    public void test2() {
        // Check that the container is running
        assertTrue(mysql.isRunning());
    }

    @Test
    @DisplayName("Check docker db name")
    public void test3() {
        // Check db name
        String databaseName = mysql.getDatabaseName();
        String expectedDatabaseName = "Nexthink_db";
        assertEquals(expectedDatabaseName, databaseName);
    }

    @Test
    @DisplayName("Send a GET request with TestRestTemplate")
    public void test4() {
        // * Send a GET request with TestRestTemplate
        // Create a TestRestTemplate object
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        // Init the endpoint URL
        String endpointURL = "https://jsonplaceholder.typicode.com";
        // Send the request and capture the response
        ResponseEntity<String> response = testRestTemplate.
                getForEntity(endpointURL + "/todos/1", String.class);
        // Print the response status code
        System.out.println("The response status code is: " + response.getStatusCode());
        // Print the response body
        System.out.println("The response body is: " + response.getBody());
        // Assert that the response was 200 OK
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Send a GET request, parse the json response and get the exact key")
    public void test5() {
        // * Send a GET request with TestRestTemplate
        // Create a TestRestTemplate object
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        // Init the endpoint URL
        String endpointURL = "https://jsonplaceholder.typicode.com";
        // Init json value holder
        Value jsonBody = null;
        // Init the Json parser
        JSONParser parser = new JSONParser();
        // Send the request and capture the response
        ResponseEntity<String> response = testRestTemplate.
                getForEntity(endpointURL + "/todos/1", String.class);
        // Parse the response AND handle the ParserException
        try {
            jsonBody = parser.parse(String.valueOf(response.getBody()));
        } catch (ParserException e) {
            e.printStackTrace();
        }
        // Print the exact part of Json
        System.out.println("The 'title' is: " + jsonBody.get("title"));
        // Assert that the response was 200 OK
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Send a POST request and check response")
    public void test6() {
        // Create a TestRestTemplate object
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        // Build a request body as a string!
        String requestBody = "{\"title\": 'foo',\"body\": 'bar',\"userId\": 1}";
        // Init the endpoint URL
        String endpointURL = "https://jsonplaceholder.typicode.com";
        // Send the request and capture the postResponse
        ResponseEntity<String> postResponse = testRestTemplate.postForEntity(endpointURL + "/posts", requestBody, String.class);
        // Print body
        System.out.println("the POST response is " + postResponse.getBody());
        // Send a GET request to query the new id
        ResponseEntity<String> getResponse = testRestTemplate.getForEntity(endpointURL + "/posts/1", String.class);
        // Print the response body
        System.out.println("The GET response is " + getResponse.getBody());
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
    }

    @Test
    @DisplayName("Send a GET request to container and check response")
    public void test7() {
        fail("To be implemented");
    }

    @Test
    @DisplayName("Send a GET request to container and ignore SSL certificate")
    public void test8() {
        // Trust SSL
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        // Create a TestRestTemplate object
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        // Init the endpoint URL
        String endpointURL = "https://jsonplaceholder.typicode.com";
        // Send a GET request to query the new id
        ResponseEntity<String> getResponse = testRestTemplate.getForEntity(endpointURL + "/posts/1", String.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    }

    @Test
    @DisplayName("Setup a kafka container and use it")
    public void test9() {
        fail("To be implemented");
    }
}
