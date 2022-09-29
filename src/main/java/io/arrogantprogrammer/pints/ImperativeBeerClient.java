package io.arrogantprogrammer.pints;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

@RegisterRestClient(configKey = "imperative-punkapi")
public interface ImperativeBeerClient {

    @GET
    @Path("/beers")
    List<Beer> getBeers();

    @GET
    @Path("/beers")
    List<Beer> getBeersPage(@QueryParam("page") int page);
}
