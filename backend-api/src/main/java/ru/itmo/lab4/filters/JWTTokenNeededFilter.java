package ru.itmo.lab4.filters;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.inject.Inject;
import ru.itmo.lab4.util.jwt.JwtUtils;

import javax.inject.Named;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;

@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {

    @Inject private Logger logger;
    @Inject private JwtUtils jwtUtils;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        try {
            String authJwtToken = jwtUtils.parseJwt(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION));
            logger.info("#### Filtering request with jwt!:: " + authJwtToken);
            if (authJwtToken == null) {
                logger.severe("#### invalid authorizationHeader : " + authJwtToken);
                throw new NotAuthorizedException("Authorization header must be provided");
            }
            jwtUtils.validateJwtToken(authJwtToken);
        } catch (Exception e) {
            logger.severe("#### invalid token: " + e.getMessage());
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("invalid token!").build());
        }
    }
}
