package rosi.cashman;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import rosi.cashman.events.EBalanceUpdated;

import java.math.BigDecimal;
import java.time.Duration;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventPublisherTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventPublisherTest.class);

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    ApplicationEventProcessor eventPublisher;

    EBalanceUpdated event1 = new EBalanceUpdated(this,"DEVICE_ID", new BigDecimal(10.0f), new BigDecimal(10.0f));
    EBalanceUpdated event2 = new EBalanceUpdated(this,"DEVICE_ID", new BigDecimal(5.0f), new BigDecimal(15.0f));
    EBalanceUpdated event3 = new EBalanceUpdated(this,"DEVICE_ID", new BigDecimal(-10.0f), new BigDecimal(5.0f));

    @Test
    public void testWithVirtualScheduler() {

        StepVerifier
                .withVirtualTime(() -> eventPublisher.getHotEventFlux())
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

        Flux<ApplicationEvent> events = eventPublisher.getHotEventFlux();

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

        Flux<ApplicationEvent> events = eventPublisher.getHotEventFlux().share();

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
