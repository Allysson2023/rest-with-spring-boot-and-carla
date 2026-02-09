package br.com.carla.integrationtests.controllers.withjson;

import br.com.carla.config.TestConfigs;
import br.com.carla.integrationtests.dto.Person;
import br.com.carla.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllersTest extends AbstractIntegrationTest {

    private static RequestSpecification specification ;
    private static ObjectMapper objectMapper;
    private static Person person;

    @BeforeAll
    static void setUp() {

        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        person = new Person();
    }

    @Test
    @Order(1)
    void create() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
            .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
            .setBasePath("/api/person/v1")
            .setPort(TestConfigs.SERVER_POR)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(person)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        Person creadPerson = objectMapper.readValue(content, Person.class);
        person = creadPerson;


        assertNotNull(creadPerson.getId());
        assertNotNull(creadPerson.getNome());
        assertNotNull(creadPerson.getSobrenome());
        assertNotNull(creadPerson.getAddress());
        assertNotNull(creadPerson.getGende());

        assertTrue(creadPerson.getId() > 0);

        assertEquals("Allysson",creadPerson.getNome());
        assertEquals("Carlos",creadPerson.getSobrenome());
        assertEquals("Fortaleza - Ceara - Brasil",creadPerson.getAddress());
        assertEquals("Male",creadPerson.getGende());

    }

    @Test
    @Order(2)
    void createWithWrongOrigin() throws JsonProcessingException {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SEMERU)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_POR)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(person)
                .when()
                .post()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request",content);
    }

    @Test
    @Order(3)
    void findById() throws JsonProcessingException {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_POR)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        Person creadPerson = objectMapper.readValue(content, Person.class);
        person = creadPerson;


        assertNotNull(creadPerson.getId());
        assertNotNull(creadPerson.getNome());
        assertNotNull(creadPerson.getSobrenome());
        assertNotNull(creadPerson.getAddress());
        assertNotNull(creadPerson.getGende());

        assertTrue(creadPerson.getId() > 0);

        assertEquals("Allysson",creadPerson.getNome());
        assertEquals("Carlos",creadPerson.getSobrenome());
        assertEquals("Fortaleza - Ceara - Brasil",creadPerson.getAddress());
        assertEquals("Male",creadPerson.getGende());



    }
    @Test
    @Order(4)
    void findByIdWithWrongOrigin() throws JsonProcessingException {

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SEMERU)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_POR)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request",content);

    }

    private void mockPerson() {
        person.setNome("Allysson");
        person.setSobrenome("Carlos");
        person.setAddress("Fortaleza - Ceara - Brasil");
        person.setGende("Male");
    }
}