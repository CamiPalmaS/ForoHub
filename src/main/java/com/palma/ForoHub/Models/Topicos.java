package com.palma.ForoHub.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "topicos",
        uniqueConstraints = @UniqueConstraint(columnNames = {"titulo", "mensaje"}))
@Getter
@Setter
@AllArgsConstructor
public class Topicos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @NotBlank(message = "El estado del tópico es obligatorio")
    private String status;

    //estos serán string hasta que se trabaje en crear las entidades de cada uno
    @NotBlank(message = "El autor es obligatorio")
    private String autor;

    @NotBlank(message = "El curso es obligatorio")
    private String curso;

    // Constructor vacío para JPA
    public Topicos() {

    }

    public Topicos(String titulo, String mensaje, String status, String autor, String curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.status = status;
        this.autor = autor;
        this.curso = curso;
    }
}
