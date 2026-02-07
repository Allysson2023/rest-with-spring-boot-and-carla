package br.com.carla.controllers;

import br.com.carla.controllers.docs.BookControllersDocs;
import br.com.carla.data.dto.BookDTO;
import br.com.carla.unittests.services.BookServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book/v1")
@Tag(name="Book", description = "Endpoints for Managing Book")
public class BookControllers implements BookControllersDocs {

    @Autowired
    private BookServices services;

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})

    @Override
    public List<BookDTO> findByAll(){
        return services.findByAll();
    }


    @GetMapping(value = "{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public BookDTO findById(@PathVariable("id") Long id){

        var book = services.findById(id);
        return book;
    }


    @PostMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})

    @Override
    public BookDTO create(@RequestBody BookDTO book){
        return services.create(book);
    }

//    // Este é o DTOV2 do versionamento da Apis, novo campo
//    @PostMapping(value = "/v2",
//            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE },
//            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE })
//    @Operation(summary = "Finds a Book",
//            description = "Find a specific Book by your Post",
//            tags = {"Book"},
//            responses = {
//                    @ApiResponse(description = "Sucess",
//                            responseCode = "200",
//                            content =@Content(schema = @Schema(implementation = BookDTO.class))),
//
//                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
//                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
//                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
//                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
//                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
//            }
//    )
//    public BookDTOV2 create(@RequestBody BookDTOV2 book){
//        return services.createV2(book);
//    }

    @PutMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})

    @Override
    public BookDTO update(@RequestBody BookDTO book){
        return services.update(book);
    }

    @DeleteMapping(value = "{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        services.delete(id);
        return ResponseEntity.noContent().build();
    }
}
