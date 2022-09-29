package io.arrogantprogrammer.pints;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Path("/imperativepints")
public class ImperativePintResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImperativePintResource.class);

    @Inject @RestClient
    ImperativeBeerClient beerClient;

    @GET
    public Beer randomBeer() {
        List<Beer> beers = beerClient.getBeers().stream().collect(Collectors.toList());
        LOGGER.info("retrieved {} beers", beers.size());
        int rand = new Random().nextInt(beers.size() - 1);
        LOGGER.debug("choosing {}", rand);
        return beers.get(rand);
    }
}
