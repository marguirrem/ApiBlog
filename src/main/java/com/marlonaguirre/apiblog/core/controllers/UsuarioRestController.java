package com.marlonaguirre.apiblog.core.controllers;

import com.marlonaguirre.apiblog.core.models.entity.Usuario;
import com.marlonaguirre.apiblog.core.models.services.IUsuarioService;
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
public class UsuarioRestController {

    public final IUsuarioService usuarioService;

    public UsuarioRestController(IUsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuarios")
    public List<Usuario> index(){
        return this.usuarioService.findAll();
    }

    @GetMapping("/usuarios/page/{page}")
    public Page<Usuario> index(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page,3);
        return this.usuarioService.findAll(pageable);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        Usuario usuarioEncontrado = this.usuarioService.findById(id);
        if (usuarioEncontrado == null){
            response.put("mensaje", "Usuario no encontrado");
            return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<Usuario>(usuarioEncontrado, HttpStatus.OK);
    }

    @PostMapping("/usuarios")
    public ResponseEntity<?> create (@Valid @RequestBody Usuario elemento, BindingResult result){
        Usuario nuevo = null;
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
            nuevo = this.usuarioService.save(elemento);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar el registro en la base de datos.");
            response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El usuario ha sido creado con éxito.");
        response.put("usuario", nuevo);
        return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    @PutMapping("/usuarios/{id}")
    public  ResponseEntity<?> update (@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id){

        Map<String, Object> response = new HashMap<>();
        Usuario update = this.usuarioService.findById(id);
        Usuario usuarioUpdate = null;

        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (update == null){
            response.put("mensaje", "Error: no se puede editar el usuario ID "
                    + id.toString()
                    + " no existe en la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            update.setNombre(usuario.getNombre());
            update.setApellido(usuario.getApellido());
            update.setCorreo(usuario.getCorreo());
            update.setUsuario(usuario.getUsuario());
            update.setEstado(usuario.getEstado());

            usuarioUpdate = this.usuarioService.save(update);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al actualizar los datos");
            response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El usuario ha sido actualizado correctamente.");
        response.put("rol", usuarioUpdate);
        return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("usuarios/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try{
            this.usuarioService.delete(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al eliminar el usuario en la base datos.");
            response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El usuario ha sido eliminado correctamente.");
        return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
