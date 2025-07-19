package uce.edu.controller;

import java.time.Duration;

import io.smallrye.jwt.build.Jwt;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/generate")
public class TokerController {
    @GET
    @Path("")
    public String generar() {
        return Jwt.issuer("http://uce.edu.ec").upn("jfpila@uce.edu.ec").groups("admin")
                .expiresIn(Duration.ofSeconds(30)).sign();

    }
}
