package br.com.carla.services;

import br.com.carla.controllers.PersonControllers;
import br.com.carla.data.dto.v1.PersonDTO;
import br.com.carla.data.dto.v2.PersonDTOV2;
import br.com.carla.exception.RequiredObjectIsNullException;
import br.com.carla.exception.ResouerceNotFoundException;
import static br.com.carla.mapper.ObjectMapper.parseListObjects;
import static br.com.carla.mapper.ObjectMapper.parseObjects;

import br.com.carla.mapper.custom.PersonMapper;
import br.com.carla.model.Person;
import br.com.carla.repository.PersonRepository;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper converter;

    public Page<PersonDTO> findByAll(Pageable pageable){
        logger.info("findByAll on Person!");

        var people = repository.findAll(pageable);

        var peopleWithLinks = people.map(person -> {
            var dto = parseObjects(person, PersonDTO.class);
            addHateoasLinks(dto);
            return dto;
        });
        return peopleWithLinks;
    }

    public PersonDTO findById(Long id){
        logger.info("findById on Person!");

        var entity = repository.findById(id)
                .orElseThrow(()-> new ResouerceNotFoundException("No records Found id..."));
        var dto = parseObjects(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }


    public PersonDTO create(PersonDTO person){

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Create on Person!");
        var entity = parseObjects(person, Person.class);

        var dto = parseObjects(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    // DTOV2 -> novo campo

    public PersonDTOV2 createV2(PersonDTOV2 person){
        logger.info("Create on Person V2!");
        var entity = converter.convertDTOtoEntity(person);

        return converter.convertEntityToDTO(repository.save(entity));

    }
    public PersonDTO update(PersonDTO person){
        if (person == null) throw new RequiredObjectIsNullException();
        logger.info("update on Person!");


        Person entity = repository.findById(person.getId())
                .orElseThrow(()-> new ResouerceNotFoundException("No records Found id..."));

        entity.setNome(person.getNome());
        entity.setSobrenome(person.getSobrenome());
        entity.setAddress(person.getAddress());
        entity.setGende(person.getGende());

        var dto = parseObjects(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;

    }

    public void delete(Long id){
        logger.info("Delete on Person!");

        Person entity = repository.findById(id)
                .orElseThrow(()-> new ResouerceNotFoundException("No records Found id..."));

        repository.delete(entity);
    }

    @Transactional
    public PersonDTO disablePerson(Long id){
        logger.info("disable on Person!");

        repository.findById(id)
                .orElseThrow(()-> new ResouerceNotFoundException("No records Found id..."));

        repository.disablePerson(id);

        var entity = repository.findById(id).get();
        var dto = parseObjects((entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    private void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonControllers.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonControllers.class).findByAll(1, 12, "asc")).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonControllers.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonControllers.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonControllers.class).disablePerson(dto.getId())).withRel("disable").withType("PATCH"));
        dto.add(linkTo(methodOn(PersonControllers.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }

}
