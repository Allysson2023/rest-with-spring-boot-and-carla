package br.com.carla.unittests.services;

import br.com.carla.controllers.BookControllers;
import br.com.carla.data.dto.BookDTO;
import br.com.carla.exception.RequiredObjectIsNullException;
import br.com.carla.exception.ResouerceNotFoundException;
import br.com.carla.mapper.custom.PersonMapper;
import br.com.carla.model.Book;
import br.com.carla.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import static br.com.carla.mapper.ObjectMapper.parseListObjects;
import static br.com.carla.mapper.ObjectMapper.parseObjects;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(BookServices.class.getName());

    @Autowired
    BookRepository repository;

    @Autowired
    PersonMapper converter;

    public List<BookDTO> findByAll(){
        logger.info("findByAll on Book!");

        var books = parseListObjects(repository.findAll(), BookDTO.class);
        books.forEach(this::addHateoasLinks);
        return books;
    }

    public BookDTO findById(Long id){
        logger.info("findById on Book!");

        var entity = repository.findById(id)
                .orElseThrow(()-> new ResouerceNotFoundException("No records Found id..."));
        var dto = parseObjects(entity, BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }


    public BookDTO create(BookDTO book){

        if (book == null) throw new RequiredObjectIsNullException();

        logger.info("Create on Book!");
        var entity = parseObjects(book, Book.class);

        var dto = parseObjects(repository.save(entity), BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

//    // DTOV2 -> novo campo
//
//    public BookDTOV2 createV2(BookDTOV2 book){
//        logger.info("Create on Book V2!");
//        var entity = converter.convertDTOtoEntity(book);
//
//        return converter.convertEntityToDTO(repository.save(entity));
//
//    }
    public BookDTO update(BookDTO book){
        if (book == null) throw new RequiredObjectIsNullException();
        logger.info("update on Book!");


        Book entity = repository.findById(book.getId())
                .orElseThrow(()-> new ResouerceNotFoundException("No records Found id..."));

        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        var dto = parseObjects(repository.save(entity), BookDTO.class);
        addHateoasLinks(dto);
        return dto;

    }

    public void delete(Long id){
        logger.info("Delete on Book!");

        Book entity = repository.findById(id)
                .orElseThrow(()-> new ResouerceNotFoundException("No records Found id..."));

        repository.delete(entity);
    }

    private void addHateoasLinks(BookDTO dto) {
        dto.add(linkTo(methodOn(BookControllers.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BookControllers.class).findByAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(BookControllers.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(BookControllers.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(BookControllers.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }

}
