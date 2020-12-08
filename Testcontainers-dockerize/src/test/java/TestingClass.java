import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class TestingClass {

    //Once per test class
    @Container
    private static final MySQLContainer mysql = new MySQLContainer("mysql:latest");

    @Test
    public void test1() {
        assertTrue(true);
    }

    @Test
    public void test2() {
        assertTrue(mysql.isRunning());
    }
}
