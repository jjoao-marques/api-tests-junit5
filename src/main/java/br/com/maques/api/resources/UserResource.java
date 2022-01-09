package br.com.maques.api.resources;

import br.com.maques.api.domain.User;
import br.com.maques.api.domain.dto.UserDTO;
import br.com.maques.api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user")
public class UserResource {


    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(mapper.map(userService.findById(id), UserDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> listDTO = userService.findAll().stream().map(x -> mapper.map(x, UserDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO obj) {
        User newObj = userService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO objDto, @PathVariable Long id) {
        objDto.setId(id);
        User newObj = userService.update(objDto);
        return  ResponseEntity.ok().body(mapper.map(newObj, UserDTO.class));
    }

}
