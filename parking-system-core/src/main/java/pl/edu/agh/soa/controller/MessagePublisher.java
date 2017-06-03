package pl.edu.agh.soa.controller;

/**
 * @author Mateusz Kluska
 */
public interface MessagePublisher {
    void publishTicket(long ticketId, long delayInMinutes, long ticketPlaceId);

    void publishPlace(long placeId);
}
