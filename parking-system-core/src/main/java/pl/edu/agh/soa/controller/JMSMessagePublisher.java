package pl.edu.agh.soa.controller;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.jms.*;

/**
 * @author Mateusz Kluska
 */
@ApplicationScoped
public class JMSMessagePublisher implements MessagePublisher {
    private static final String TICKET_ID = "ticketId";
    private static final String TICKET_PLACE_ID = "ticketPlaceId";
    private static final String PLACE_ID = "placeId";
    private static final int PLACE_MINUTE_OFFSET = 1;

    @Resource(lookup = "java:/jms/soa/TicketConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "java:/jms/queue/TicketQueue")
    private Destination queue;

    @Override
    public void publishTicket(long ticketId, long delayInMinutes, long ticketPlaceId) {
        try {
            try (QueueConnection connection = (QueueConnection) connectionFactory.createConnection("jmsuser", "test")) {
                try (QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE)) {
                    try (MessageProducer producer = session.createProducer(queue)) {
                        TextMessage msg = session.createTextMessage();
                        msg.setLongProperty(TICKET_ID, ticketId);
                        msg.setLongProperty(TICKET_PLACE_ID, ticketPlaceId);
                        producer.setDeliveryDelay(delayInMinutes * 60 * 1000);
                        producer.send(msg);
                    }
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void publishPlace(long placeId) {
        try {
            try (QueueConnection connection = (QueueConnection) connectionFactory.createConnection("jmsuser", "test")) {
                try (QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE)) {
                    try (MessageProducer producer = session.createProducer(queue)) {
                        TextMessage msg = session.createTextMessage();
                        msg.setLongProperty(PLACE_ID, placeId);
                        producer.setDeliveryDelay(PLACE_MINUTE_OFFSET * 60 * 1000);
                        producer.send(msg);
                    }
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}