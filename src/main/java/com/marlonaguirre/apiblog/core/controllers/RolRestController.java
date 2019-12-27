package com.marlonaguirre.apiblog.core.controllers;


import com.marlonaguirre.apiblog.core.models.entity.Rol;
import com.marlonaguirre.apiblog.core.models.services.IRolService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins="*")
public class RolRestController {

    public final IRolService rolService;

    public RolRestController(IRolService rolService){
        this.rolService = rolService;
    }

    @GetMapping("/roles")
    public List<Rol> index(){
        return this.rolService.findAll();
    }

    @GetMapping("/roles/page/{page}")
    public Page<Rol> index(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page,3);
        return this.rolService.findAll(pageable);
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        Rol  rolEncontrado = this.rolService.findById(id);
        if (rolEncontrado == null){
            response.put("mensaje", "Rol no encontrado");
            return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<Rol>(rolEncontrado, HttpStatus.OK);
    }

    @PostMapping("/roles")
    public ResponseEntity<?> create (@Valid @RequestBody Rol elemento, BindingResult result){
        Rol nuevo = null;
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            nuevo = this.rolService.save(elemento);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El rol ha sido creado con éxito");
        response.put("rol", nuevo);
        return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/roles/{id}")
    public  ResponseEntity<?> update (@Valid @RequestBody Rol rol, BindingResult result, @PathVariable Long id){

        Map<String, Object> response = new HashMap<>();
        Rol update = this.rolService.findById(id);
        Rol rolUpdate = null;

        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (update == null){
            response.put("mensaje", "Error: no se puede editar el rol ID "
                    + id.toString()
                    + " no existe en la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            update.setEstado(rol.getEstado());
            rolUpdate = this.rolService.save(update);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al actualizar los datos");
            response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El rol ha sido actualizado correctamente!!!");
        response.put("rol", rolUpdate);
        return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("roles/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try{
            this.rolService.delete(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al eliminar el rol en la base datos");
            response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El rol ha sido eliminado correctamente!!!");
        return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
