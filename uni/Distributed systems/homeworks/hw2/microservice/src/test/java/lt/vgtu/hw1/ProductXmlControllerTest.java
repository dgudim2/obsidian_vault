package lt.vgtu.hw1;

import lt.vgtu.hw1.config.RestAssuredConfigurer;
import lt.vgtu.hw1.controllers.ProductXmlController;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.http.ContentType.TEXT;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductXmlControllerTest extends RestAssuredConfigurer {

    @Autowired
    private ProductXmlController productXmlController;

    @Test
    @Order(1)
    void contextLoads() {
        assertThat(productXmlController).isNotNull();
    }

    @Test
    @Order(2)
    void shouldSaveXmlAndReturnSuccess() {
        requestSpec
                .contentType(TEXT)
                .when()
                .get("/mapXml/1")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(3)
    void shouldReturnBadRequestWithInvalidId() {
        requestSpec
                .contentType(JSON)
                .when()
                .get("/mapXml/-1")
                .then()
                .statusCode(404);
    }
}