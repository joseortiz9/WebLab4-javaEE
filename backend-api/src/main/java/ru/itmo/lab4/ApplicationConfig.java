package ru.itmo.lab4;

import ru.itmo.lab4.filters.CorsFilter;
import ru.itmo.lab4.filters.JWTTokenNeededFilter;
import ru.itmo.lab4.rest.AuthEndpoint;
import ru.itmo.lab4.rest.PointsEndpoint;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class ApplicationConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(PointsEndpoint.class);
        classes.add(AuthEndpoint.class);
        classes.add(CorsFilter.class);
        classes.add(JWTTokenNeededFilter.class);
        return classes;
    }
}
