package br.com.carla.mapper.custom;

import br.com.carla.data.dto.v2.PersonDTOV2;
import br.com.carla.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {

    public PersonDTOV2 convertEntityToDTO(Person person){

        PersonDTOV2 dto = new PersonDTOV2();
        dto.setId(person.getId());
        dto.setNome(person.getNome());
        dto.setSobrenome(person.getSobrenome());
        dto.setBirthDay(new Date());
        dto.setAddress(person.getAddress());
        dto.setGende(person.getGende());
        return dto;
    }

    public Person convertDTOtoEntity(PersonDTOV2 person){

        Person entity = new Person();
        entity.setId(person.getId());
        entity.setNome(person.getNome());
        entity.setSobrenome(person.getSobrenome());
        //entity.setBirthDay(new Date());
        entity.setAddress(person.getAddress());
        entity.setGende(person.getGende());
        return entity;
    }
}
