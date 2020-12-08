import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class TestingClass {

    //Once per test class
    @Container
    private static final MySQLContainer mysql = new MySQLContainer("mysql:latest")
            .withDatabaseName("Nexthink_db")
            .withUsername("MNT_backend")
            .withPassword("FunnyPass");

    @Test
    public void test1() {
        // Check that Junit is working
        assertTrue(true);
    }

    @Test
    public void test2() {
        // Check that the container is running
        assertTrue(mysql.isRunning());
    }

    @Test
    public void test3() {
        // Check db name
        String databaseName = mysql.getDatabaseName();
        String expectedDatabaseName = "Nexthink_db";
        assertEquals(expectedDatabaseName, databaseName);
    }
}
