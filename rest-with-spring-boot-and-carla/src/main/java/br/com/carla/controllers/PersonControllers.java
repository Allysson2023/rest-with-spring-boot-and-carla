package br.com.carla.controllers;

import br.com.carla.controllers.docs.PersonControllersDocs;
import br.com.carla.data.dto.v1.PersonDTO;
import br.com.carla.data.dto.v2.PersonDTOV2;
import br.com.carla.services.PersonServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name="People", description = "Endpoints for Managing People")
public class PersonControllers implements PersonControllersDocs {

    @Autowired
    private PersonServices services;

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})

    @Override
    public List<PersonDTO> findByAll(){
        return services.findByAll();
    }


    @GetMapping(value = "{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public PersonDTO findById(@PathVariable("id") Long id){

        var person = services.findById(id);
        return person;
    }


    @PostMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})

    @Override
    public PersonDTO create(@RequestBody PersonDTO person){
        return services.create(person);
    }

//    // Este é o DTOV2 do versionamento da Apis, novo campo
//    @PostMapping(value = "/v2",
//            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE },
//            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE })
//    @Operation(summary = "Finds a Person",
//            description = "Find a specific Person by your Post",
//            tags = {"People"},
//            responses = {
//                    @ApiResponse(description = "Sucess",
//                            responseCode = "200",
//                            content =@Content(schema = @Schema(implementation = PersonDTO.class))),
//
//                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
//                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
//                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
//                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
//                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
//            }
//    )
//    public PersonDTOV2 create(@RequestBody PersonDTOV2 person){
//        return services.createV2(person);
//    }

    @PutMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})

    @Override
    public PersonDTO update(@RequestBody PersonDTO person){
        return services.update(person);
    }

    @DeleteMapping(value = "{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        services.delete(id);
        return ResponseEntity.noContent().build();
    }
}
