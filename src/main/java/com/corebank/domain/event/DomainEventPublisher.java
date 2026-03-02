package com.corebank.domain.event;

/**
 * <h2>Domain Event Publisher Contract</h2>
 * <p>
 * Abstraction for publishing domain events from the business core.
 * The implementation resides in the infrastructure layer, maintaining
 * the <b>Inward Dependency Rule</b>.
 * </p>
 *
 * <h3>Design Decision</h3>
 * <p>
 * By defining this interface in the domain layer, we decouple the act of
 * raising an event from the mechanism of delivery (Spring ApplicationEvent,
 * Kafka, RabbitMQ, etc.). This allows the domain to remain framework-ignorant.
 * </p>
 */
public interface DomainEventPublisher {

    /**
     * Publishes a domain event to all registered consumers.
     *
     * @param event the domain event to publish
     */
    void publish(DomainEvent event);
}
