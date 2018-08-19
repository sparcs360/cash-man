package rosi.cashman;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import rosi.cashman.events.EBalanceUpdated;
import rosi.cashman.events.EventBase;

import java.math.BigDecimal;
import java.time.Duration;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationEventPublisherTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationEventPublisherTest.class);

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    ApplicationEventProcessor eventPublisher;

    EBalanceUpdated event1 = new EBalanceUpdated(this,"VENUE_ID","DEVICE_ID", new BigDecimal(10.0f), new BigDecimal(10.0f));
    EBalanceUpdated event2 = new EBalanceUpdated(this,"VENUE_ID","DEVICE_ID", new BigDecimal(5.0f), new BigDecimal(15.0f));
    EBalanceUpdated event3 = new EBalanceUpdated(this,"VENUE_ID","DEVICE_ID", new BigDecimal(-10.0f), new BigDecimal(5.0f));

    @Test
    public void testWithVirtualScheduler() {

        StepVerifier
                .withVirtualTime(() -> eventPublisher.getEventFeed())
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(1))
                .then(() -> {
                    applicationEventPublisher.publishEvent(event1);
                })
                .expectNext(event1)
                .then(() -> {
                    applicationEventPublisher.publishEvent(event2);
                    applicationEventPublisher.publishEvent(event3);
                })
                .expectNext(event2, event3)
                .thenAwait(Duration.ofSeconds(1))
                .expectNoEvent(Duration.ofSeconds(1))
                .thenCancel()
                .verify();
    }

    @Test
    public void testWithActualScheduler() {

        Flux<EventBase> events = eventPublisher.getEventFeed();

        events.subscribe(e -> LOGGER.debug(e.toString()));
        events.subscribe(e -> LOGGER.debug(e.toString()));
        events.subscribe(e -> LOGGER.debug(e.toString()));

        StepVerifier
                .create(events)
                .expectSubscription()
                .then(() -> {
                    applicationEventPublisher.publishEvent(event1);
                })
                .expectNext(event1)
                .then(() -> {
                    applicationEventPublisher.publishEvent(event2);
                    applicationEventPublisher.publishEvent(event3);
                })
                .expectNext(event2, event3)
                .thenCancel()
                .verify();
    }

    @Test
    public void testWithSharedActualScheduler() {

        Flux<EventBase> events = eventPublisher.getEventFeed().share();

        events.subscribe(e -> LOGGER.debug(e.toString()));
        events.subscribe(e -> LOGGER.debug(e.toString()));
        events.subscribe(e -> LOGGER.debug(e.toString()));

        StepVerifier
                .create(events)
                .expectSubscription()
                .then(() -> {
                    applicationEventPublisher.publishEvent(event1);
                })
                .expectNext(event1)
                .then(() -> {
                    applicationEventPublisher.publishEvent(event2);
                    applicationEventPublisher.publishEvent(event3);
                })
                .expectNext(event2, event3)
                .thenCancel()
                .verify();
    }
}
