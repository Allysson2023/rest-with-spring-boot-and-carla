package br.com.carla.services;

import br.com.carla.exception.ResouerceNotFoundException;
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

    public List<Person> findByAll(){
        logger.info("findByAll on Person!");

        return repository.findAll();
    }

    public Person findById(Long id){
        logger.info("findById on Person!");

        return repository.findById(id)
                .orElseThrow(()-> new ResouerceNotFoundException("No records Found id..."));
    }


    public Person create(Person person){
        logger.info("Create on Person!");

        return repository.save(person);

    }

    public Person update(Person person){
        logger.info("update on Person!");


        Person entity = repository.findById(person.getId())
                .orElseThrow(()-> new ResouerceNotFoundException("No records Found id..."));

        entity.setNome(person.getNome());
        entity.setSobrenome(person.getSobrenome());
        entity.setAddress(person.getAddress());
        entity.setGende(person.getGende());

        return repository.save(person);

    }

    public void delete(Long id){
        logger.info("Delete on Person!");

        Person entity = repository.findById(id)
                .orElseThrow(()-> new ResouerceNotFoundException("No records Found id..."));

        repository.delete(entity);
    }

}
