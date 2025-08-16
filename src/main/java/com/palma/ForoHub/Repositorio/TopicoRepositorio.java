package com.palma.ForoHub.Repositorio;

import com.palma.ForoHub.Models.Topicos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicoRepositorio extends JpaRepository<Topicos, Long> {

    //busqueda parcial de topico
    List<Topicos> findByTituloContainingIgnoreCase(String palabra);

    boolean existsByTituloAndMensaje(String titulo, String mensaje);
}
