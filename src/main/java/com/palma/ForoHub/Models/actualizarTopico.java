package com.palma.ForoHub.Models;

public record actualizarTopico(
        String titulo,
        String mensaje,
        String status,
        String autor,
        String curso
) {
}
