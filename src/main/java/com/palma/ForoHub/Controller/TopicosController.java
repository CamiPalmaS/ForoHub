package com.palma.ForoHub.Controller;

import com.palma.ForoHub.Models.DatosTopico;
import com.palma.ForoHub.Models.Topicos;
import com.palma.ForoHub.Models.actualizarTopico;
import com.palma.ForoHub.Repositorio.TopicoRepositorio;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
    private final TopicoRepositorio repository;

    public TopicosController(TopicoRepositorio repository) {
        this.repository = repository;
    }
    //metodos GET para listado de topicos
    @GetMapping
    public List<Topicos> listarTopicos(){
        System.out.println("Todos los tópicos");

        return repository.findAll();
    }

    //por ID
    @GetMapping("/{id}")
    public ResponseEntity<Topicos> obtenerPorId(@PathVariable Long id){
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //por busqueda parcial del titulo: /buscar?titulo=${palabra}
    @GetMapping("/buscar")
    public List<Topicos> buscarPorTitulo(@RequestParam String titulo){
        return repository.findByTituloContainingIgnoreCase(titulo);
    }

    //metodo POST para crear nuevos topicos
    @Transactional
    @PostMapping
    public ResponseEntity<?> crearTopico(@Valid @RequestBody DatosTopico datos) {
        // Verificar duplicados
        boolean existe = repository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje());
        if (existe) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ya existe un tópico con el mismo título y mensaje");
        }

        Topicos topico = new Topicos(
                datos.titulo(),
                datos.mensaje(),
                datos.status(),
                datos.autor(),
                datos.curso()
        );

        Topicos guardado = repository.save(topico);

        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    //metodo PUT
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Topicos> actualizarTopico(
            @PathVariable Long id,
            @RequestBody actualizarTopico datos) {

        Optional<Topicos> optionalTopico = repository.findById(id);

        if (optionalTopico.isPresent()) {
            Topicos topico = optionalTopico.get();

            // Aplicar las mismas validaciones de negocio que en el POST si es necesario

            if (datos.titulo() != null) topico.setTitulo(datos.titulo());
            if (datos.mensaje() != null) topico.setMensaje(datos.mensaje());
            if (datos.status() != null) topico.setStatus(datos.status());
            if (datos.autor() != null) topico.setAutor(datos.autor());
            if (datos.curso() != null) topico.setCurso(datos.curso());

            Topicos actualizado = repository.save(topico);
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //metodo DELETE por id
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Optional<Topicos> topicoOptional = repository.findById(id);

        if (topicoOptional.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
