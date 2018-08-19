package rosi.cashman;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

@Configuration
public class EventFeedRouter {

    @Bean
    public RouterFunction<ServerResponse> eventFeedRoutes(EventFeedHandler handler) {

        return RouterFunctions
                .route(
                        GET("/events/feed"), handler::eventFeed
                );

    }
}
