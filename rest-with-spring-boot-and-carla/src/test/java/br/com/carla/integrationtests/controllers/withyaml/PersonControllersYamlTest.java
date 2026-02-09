package br.com.carla.integrationtests.controllers.withyaml;

import br.com.carla.config.TestConfigs;
import br.com.carla.integrationtests.controllers.withyaml.mapper.YAMLMapper;
import br.com.carla.integrationtests.dto.Person;
import br.com.carla.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllersYamlTest extends AbstractIntegrationTest {

    private static RequestSpecification specification ;
    private static YAMLMapper objectMapper;
    private static Person person;

    @BeforeAll
    static void setUp() {

        objectMapper = new YAMLMapper();

        person = new Person();
    }

    @Test
    @Order(1)
    void createTest() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
            .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
            .setBasePath("/api/person/v1")
            .setPort(TestConfigs.SERVER_POR)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();

        var creadPerson = given().config(
                        RestAssuredConfig.config()
                                .encoderConfig(EncoderConfig
                                        .encoderConfig()
                                        .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                ).spec(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .body(person, objectMapper)
                .when()
                .post()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(Person.class, objectMapper);

        person = creadPerson;


        assertNotNull(creadPerson.getId());
        assertTrue(creadPerson.getId() > 0);

        assertEquals("Allysson",creadPerson.getNome());
        assertEquals("Carlos",creadPerson.getSobrenome());
        assertEquals("Fortaleza - Ceara - Brasil",creadPerson.getAddress());
        assertEquals("Male",creadPerson.getGende());
        assertTrue(creadPerson.getEnabled());

    }

    @Test
    @Order(2)
    void updateTest() throws JsonProcessingException {
        person.setSobrenome("Teste do Update");

        var creadPerson = given().config(
                        RestAssuredConfig.config()
                                .encoderConfig(EncoderConfig
                                        .encoderConfig()
                                        .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                ).spec(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .body(person, objectMapper)
                .when()
                .put()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(Person.class, objectMapper);

        person = creadPerson;


        assertNotNull(creadPerson.getId());
        assertTrue(creadPerson.getId() > 0);

        assertEquals("Allysson",creadPerson.getNome());
        assertEquals("Teste do Update",creadPerson.getSobrenome());
        assertEquals("Fortaleza - Ceara - Brasil",creadPerson.getAddress());
        assertEquals("Male",creadPerson.getGende());
        assertTrue(creadPerson.getEnabled());

    }


    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {

        var creadPerson = given().config(
                RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig
                                .encoderConfig()
                                .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
        ).spec(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .pathParam("id", person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(Person.class, objectMapper);

        person = creadPerson;


        assertNotNull(creadPerson.getId());
        assertTrue(creadPerson.getId() > 0);

        assertEquals("Allysson",creadPerson.getNome());
        assertEquals("Teste do Update",creadPerson.getSobrenome());
        assertEquals("Fortaleza - Ceara - Brasil",creadPerson.getAddress());
        assertEquals("Male",creadPerson.getGende());
        assertTrue(creadPerson.getEnabled());

    }

    @Test
    @Order(4)
    void disableTest() throws JsonProcessingException {

        var creadPerson = given().config(
                        RestAssuredConfig.config()
                                .encoderConfig(EncoderConfig
                                        .encoderConfig()
                                        .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                ).spec(specification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .pathParam("id", person.getId())
                .when()
                .patch("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(Person.class, objectMapper);

        person = creadPerson;


        assertNotNull(creadPerson.getId());
        assertTrue(creadPerson.getId() > 0);

        assertEquals("Allysson",creadPerson.getNome());
        assertEquals("Teste do Update",creadPerson.getSobrenome());
        assertEquals("Fortaleza - Ceara - Brasil",creadPerson.getAddress());
        assertEquals("Male",creadPerson.getGende());
        assertFalse(creadPerson.getEnabled());

    }

    @Test
    @Order(5)
    void deleteTest() throws JsonProcessingException {

        given(specification)
                .pathParam("id", person.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);

    }


    @Test
    @Order(6)
    void findAllTest() throws JsonProcessingException {

        var response = given().config(
                        RestAssuredConfig.config()
                                .encoderConfig(EncoderConfig
                                        .encoderConfig()
                                        .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                ).spec(specification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(Person[].class, objectMapper);

        List<Person> people = Arrays.asList(response);

        Person personOne = people.get(0);


        assertNotNull(personOne.getId());
        assertTrue(personOne.getId() > 0);

        assertEquals("Allysson",personOne.getNome());
        assertEquals("Carlos",personOne.getSobrenome());
        assertEquals("Fortaleza",personOne.getAddress());
        assertEquals("Male",personOne.getGende());
        assertTrue(personOne.getEnabled());

        Person personFour = people.get(1);


        assertNotNull(personFour.getId());
        assertTrue(personFour.getId() > 0);

        assertEquals("Silva",personFour.getNome());
        assertEquals("Carlos",personFour.getSobrenome());
        assertEquals("Fortaleza - Ceara - Brasil",personFour.getAddress());
        assertEquals("Male",personFour.getGende());
        assertTrue(personFour.getEnabled());

    }


    private void mockPerson() {
        person.setNome("Allysson");
        person.setSobrenome("Carlos");
        person.setAddress("Fortaleza - Ceara - Brasil");
        person.setGende("Male");
        person.setEnabled(true);
    }
}