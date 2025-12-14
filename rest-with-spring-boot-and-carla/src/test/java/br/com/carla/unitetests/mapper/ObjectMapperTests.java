package br.com.carla.unitetests.mapper;
import static br.com.carla.mapper.ObjectMapper.parseListObjects;
import static br.com.carla.mapper.ObjectMapper.parseObjects;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import br.com.carla.data.dto.v1.PersonDTO;
import br.com.carla.unitetests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.carla.model.Person;

public class ObjectMapperTests {
    MockPerson inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockPerson();
    }

    @Test
    public void parseEntityToDTOTest() {
        PersonDTO output = parseObjects(inputObject.mockEntity(), PersonDTO.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First Name Test0", output.getNome());
        assertEquals("Last Name Test0", output.getSobrenome());
        assertEquals("Address Test0", output.getAddress());
        assertEquals("Male", output.getGende());
    }

    @Test
    public void parseEntityListToDTOListTest() {
        List<PersonDTO> outputList = parseListObjects(inputObject.mockEntityList(), PersonDTO.class);
        PersonDTO outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("First Name Test0", outputZero.getNome());
        assertEquals("Last Name Test0", outputZero.getSobrenome());
        assertEquals("Address Test0", outputZero.getAddress());
        assertEquals("Male", outputZero.getGende());

        PersonDTO outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("First Name Test7", outputSeven.getNome());
        assertEquals("Last Name Test7", outputSeven.getSobrenome());
        assertEquals("Address Test7", outputSeven.getAddress());
        assertEquals("Female", outputSeven.getGende());

        PersonDTO outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("First Name Test12", outputTwelve.getNome());
        assertEquals("Last Name Test12", outputTwelve.getSobrenome());
        assertEquals("Address Test12", outputTwelve.getAddress());
        assertEquals("Male", outputTwelve.getGende());
    }

    @Test
    public void parseDTOToEntityTest() {
        Person output = parseObjects(inputObject.mockDTO(), Person.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First Name Test0", output.getNome());
        assertEquals("Last Name Test0", output.getSobrenome());
        assertEquals("Address Test0", output.getAddress());
        assertEquals("Male", output.getGende());
    }

    @Test
    public void parserDTOListToEntityListTest() {
        List<Person> outputList = parseListObjects(inputObject.mockDTOList(), Person.class);
        Person outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("First Name Test0", outputZero.getNome());
        assertEquals("Last Name Test0", outputZero.getSobrenome());
        assertEquals("Address Test0", outputZero.getAddress());
        assertEquals("Male", outputZero.getGende());

        Person outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("First Name Test7", outputSeven.getNome());
        assertEquals("Last Name Test7", outputSeven.getSobrenome());
        assertEquals("Address Test7", outputSeven.getAddress());
        assertEquals("Female", outputSeven.getGende());

        Person outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("First Name Test12", outputTwelve.getNome());
        assertEquals("Last Name Test12", outputTwelve.getSobrenome());
        assertEquals("Address Test12", outputTwelve.getAddress());
        assertEquals("Male", outputTwelve.getGende());
    }
}