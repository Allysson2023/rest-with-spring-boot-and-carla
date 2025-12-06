package br.com.carla;

import br.com.carla.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    public Person findById(String Id){
        logger.info("findById on Person!");

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setNome("Allysson");
        person.setSobrenome("Carlos");
        person.setAddress("Fortaleza");
        person.setGende("Male");
        return person;
    }

    public List<Person> findByAll(){
        logger.info("findByAll on Person!");

        List<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < 8; i++){
            Person person = mockPerson(i);
            persons.add(person);
        }

        return persons;
    }

    public Person create(Person person){
        logger.info("Create on Person!");

        return person;

    }

    public Person update(Person person){
        logger.info("update on Person!");

        return person;

    }

    public void delete(String Id){
        logger.info("Delete on Person!");

    }

    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setNome("FastName " + i);
        person.setSobrenome("Last " + i);
        person.setAddress("Cidade " + i);
        person.setGende("Gende " + i);
        return person;
    }
}
