package lt.vgtu.hw1.config;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.web.server.LocalServerPort;

public abstract class RestAssuredConfigurer {

    @LocalServerPort
    private int port;

    protected RequestSpecification requestSpec;

    @BeforeEach
    public void setUp() {
        requestSpec = RestAssured.given().baseUri("http://localhost:" + port);
    }
}
