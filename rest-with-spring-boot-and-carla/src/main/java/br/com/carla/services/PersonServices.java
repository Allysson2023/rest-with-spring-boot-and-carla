package br.com.carla.services;

import br.com.carla.data.dto.PersonDTO;
import br.com.carla.exception.ResouerceNotFoundException;
import static br.com.carla.mapper.ObjectMapper.parseListObjects;
import static br.com.carla.mapper.ObjectMapper.parseObjects;
import br.com.carla.model.Person;
import br.com.carla.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    public List<PersonDTO> findByAll(){
        logger.info("findByAll on Person!");

        return parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id){
        logger.info("findById on Person!");

        var entity = repository.findById(id)
                .orElseThrow(()-> new ResouerceNotFoundException("No records Found id..."));
        return parseObjects(entity, PersonDTO.class);
    }


    public PersonDTO create(PersonDTO person){
        logger.info("Create on Person!");
        var entity = parseObjects(person, Person.class);

        return parseObjects(repository.save(entity), PersonDTO.class);

    }

    public PersonDTO update(PersonDTO person){
        logger.info("update on Person!");


        Person entity = repository.findById(person.getId())
                .orElseThrow(()-> new ResouerceNotFoundException("No records Found id..."));

        entity.setNome(person.getNome());
        entity.setSobrenome(person.getSobrenome());
        entity.setAddress(person.getAddress());
        entity.setGende(person.getGende());

        return parseObjects(repository.save(entity), PersonDTO.class);

    }

    public void delete(Long id){
        logger.info("Delete on Person!");

        Person entity = repository.findById(id)
                .orElseThrow(()-> new ResouerceNotFoundException("No records Found id..."));

        repository.delete(entity);
    }

}
