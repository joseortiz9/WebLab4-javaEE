package ru.itmo.lab4.rest;

import ru.itmo.lab4.filters.JWTTokenNeeded;
import ru.itmo.lab4.models.UserEntity;
import ru.itmo.lab4.payload.AuthRequest;
import ru.itmo.lab4.payload.JwtResponse;
import ru.itmo.lab4.repositories.UserRepository;
import ru.itmo.lab4.util.PasswordUtils;
import ru.itmo.lab4.util.jwt.JwtUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.io.Serializable;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path("/auth")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class AuthEndpoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Context private UriInfo uriInfo;
    @Inject private JwtUtils jwtUtils;
    @Inject private UserRepository repository;

    @POST
    @Path("/check_session")
    @JWTTokenNeeded
    public Response checkSession() {
        return Response.ok("welcome back!").build();
    }

    @POST
    @Path("/login")
    public Response authenticateUser(@Valid AuthRequest authRequest) {
        try {
            UserEntity userAuthenticated = repository.findByUserAndPass(authRequest);
            String token = jwtUtils.generateJwtToken(userAuthenticated.getUsername(), uriInfo.getAbsolutePath().toString());
            return Response.ok(new JwtResponse(token, userAuthenticated.getUsername())).build();
        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/register")
    public Response create(@Valid AuthRequest authRequest) {
        if (repository.findByUser(authRequest.getUsername()) != null) {
            return Response.status(UNAUTHORIZED).entity("Username is already taken!").build();
        }

        try {
            UserEntity newUser = new UserEntity(authRequest.getUsername(), PasswordUtils.digestPassword(authRequest.getPassword()));
            repository.create(newUser);
            return authenticateUser(authRequest);
        } catch (Exception e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
