package ru.itmo.lab4.rest;

import ru.itmo.lab4.filters.JWTTokenNeeded;
import ru.itmo.lab4.models.PointEntity;
import ru.itmo.lab4.models.UserEntity;
import ru.itmo.lab4.payload.JwtResponse;
import ru.itmo.lab4.payload.ObjWithMsgResponse;
import ru.itmo.lab4.payload.PointRequest;
import ru.itmo.lab4.repositories.PointsRepository;
import ru.itmo.lab4.repositories.UserRepository;
import ru.itmo.lab4.util.jwt.JwtUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path("")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class PointsEndpoint {

    @Inject private PointsRepository repository;
    @Inject private UserRepository userRepository;
    @Inject private JwtUtils jwtUtils;

    @GET
    @Path("/points")
    @JWTTokenNeeded
    public Response getAllPoints(@HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        try {
            return Response.ok(repository.findAllByUser(getUserFromJwt(token))).build();
        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/points")
    @JWTTokenNeeded
    public Response addPoint(@HeaderParam(HttpHeaders.AUTHORIZATION) String token, @Valid PointRequest pointRequest) {
        try {
            PointEntity newPoint =
                    new PointEntity(pointRequest.getX(), pointRequest.getY(), pointRequest.getR(), getUserFromJwt(token));
            if (newPoint.getR() < 0)
                return Response.status(Response.Status.BAD_REQUEST).entity("R can not be negative!").build();
            PointEntity PointWithID = repository.create(newPoint);

            return Response.ok(new ObjWithMsgResponse<PointEntity>("PointEntity saved Successfully!", PointWithID)).build();
        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }
    }

    //TODO find another pattern to access the user sent
    private UserEntity getUserFromJwt(String token) {
        String authJwtToken = jwtUtils.parseJwt(token);
        return userRepository.findByUser(jwtUtils.getUserNameFromJwtToken(authJwtToken));
    }
}
