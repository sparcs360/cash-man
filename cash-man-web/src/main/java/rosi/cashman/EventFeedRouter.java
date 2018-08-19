package rosi.cashman;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.function.Predicate;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;

@Configuration
public class EventFeedRouter {

    @Bean
    public RouterFunction<ServerResponse> eventFeedRoutes(EventFeedHandler handler) {

        return RouterFunctions
                .route(
                        GET("/events/feed")
                                .and(queryParamProvided("venueId")), handler::eventsByVenue
                ).andRoute(
                        GET("/events/feed"), handler::allEvents
                );

    }

    public static RequestPredicate queryParamProvided(String name) {
        return request -> {
            boolean present = request.queryParam(name).isPresent();
            return present;
        };
    }
}
