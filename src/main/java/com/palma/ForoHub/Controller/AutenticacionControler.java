package com.palma.ForoHub.Controller;

import com.palma.ForoHub.Models.DatosLogin;
import com.palma.ForoHub.Models.DatosTokenJWT;
import com.palma.ForoHub.Models.Usuario;
import com.palma.ForoHub.Service.TokenServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class AutenticacionControler {
    @Autowired
    private TokenServicio tokenServicio;

    @Autowired
    private AuthenticationManager manager;

    public AutenticacionControler(AuthenticationManager manager) {
        this.manager = manager;
    }

    @PostMapping
    public ResponseEntity inicioSesion(@RequestBody @Valid DatosLogin datosLogin){
        var tokenAutenticacion = new UsernamePasswordAuthenticationToken(datosLogin.email(), datosLogin.password());
        var autenticacion = manager.authenticate(tokenAutenticacion);
        var tokenJWT = tokenServicio.generarToken((Usuario) autenticacion.getPrincipal());

        return ResponseEntity.ok(new DatosTokenJWT(tokenJWT));
    }
}
