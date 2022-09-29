package io.arrogantprogrammer.pints;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Path("/pints")
public class ImperativePintResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImperativePintResource.class);

    @Inject @RestClient
    ImperativeBeerClient beerClient;

    @Inject @RestClient
    ReactiveBeerClient reactiveBeerClient;

    @GET
    @Path("/imperative")
    public Beer imperativeRandomBeer() {
        List<Beer> beers = new ArrayList<>();
        int page = 1;
        boolean morePages = true;
        while(morePages){
            List<Beer> beerList = beerClient.getBeersPage(page);
            if (beerList.isEmpty()) {
                morePages = false;
            }else{
                beers.addAll(beerList);
                page++;
            }
        }
        LOGGER.info("retrieved {} beers", beers.size());
        return beers.get(new Random().nextInt(beers.size()));
    }

    @GET
    @Path("/reactive")
    public Uni<Beer> reactiveRandomBeer() {
        return Multi.createBy()
                .repeating()
                .uni(AtomicInteger::new, page ->
                        reactiveBeerClient.getBeersPage(page.incrementAndGet())
                                .onFailure()
                                .recoverWithUni((Uni.createFrom().item(Collections.emptyList())))
                )
                .until(List::isEmpty)
                .onItem()
                .disjoint()
                .collect()
                .asList()
                .onItem()
                .transform(beerList -> {
                    return(Beer) beerList.get(new Random().nextInt(beerList.size()));
                });
    }
}
