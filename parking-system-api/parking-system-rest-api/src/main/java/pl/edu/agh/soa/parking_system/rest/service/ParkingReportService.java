package pl.edu.agh.soa.parking_system.rest.service;

import pl.edu.agh.soa.contracts.raport.*;
import pl.edu.agh.soa.controller.ParkingDAO;
import pl.edu.agh.soa.model.Incident;
import pl.edu.agh.soa.model.ParkingUser;
import pl.edu.agh.soa.model.Place;
import pl.edu.agh.soa.model.Ticket;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mateusz Kluska
 */
@Stateless
public class ParkingReportService {
    @Inject
    private ParkingDAO parkingDAO;

    public ReportDTO prepareReport(long startTimeInMillis) {
        LocalDateTime ldt = Instant.ofEpochMilli(startTimeInMillis)
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<Ticket> ticketsAfter = parkingDAO.findTicketsAfter(startTimeInMillis);
        List<Incident> incidentsAfter = parkingDAO.findIncidentsAfter(startTimeInMillis);
        List<ParkingUser> parkingUsersAfter = parkingDAO.findUsersWithIncidentsAfter(startTimeInMillis);
        List<Place> placesWithTickets = parkingDAO.findAllPlacesWithTicketsAfter(startTimeInMillis);
        List<Place> placesWithIncidents = parkingDAO.findAllPlacesWithIncidentsAfter(startTimeInMillis);
        return ReportDTO.builder()
                .reportStartTime(ldt.format(dtf))
                .numberOfIncidents(incidentsAfter.size())
                .tickets(prepareTicketReport(ticketsAfter))
                .users(prepareUsersReport(parkingUsersAfter))
                .places(preparePlacesReport(placesWithIncidents, placesWithTickets))
                .areas(prepareAreasReport(placesWithIncidents, placesWithTickets))
                .build();
    }

    private Set<PlaceReportDTO> preparePlacesReport(List<Place> placesWithIncidents, List<Place> placesWithTickets) {
        Map<Long, Set<Incident>> placeIdIncidentMap = preparePlaceIncidentData(placesWithIncidents);
        Map<Long, Set<Ticket>> placeIdTicketMap = preparePlaceTicketData(placesWithTickets);
        List<Long> placeIds = parkingDAO.getPlaceIds();
        return placeIds.stream()
                .filter(p -> placeIdIncidentMap.containsKey(p) || placeIdTicketMap.containsKey(p))
                .map(id -> getPlaceReportDTO(placeIdIncidentMap, placeIdTicketMap, id))
                .collect(Collectors.toSet());

    }

    private PlaceReportDTO getPlaceReportDTO(Map<Long, Set<Incident>> placeIdIncidentMap, Map<Long, Set<Ticket>> placeIdTicketMap, Long placeId) {
        Set<Incident> incidents = placeIdIncidentMap.getOrDefault(placeId, new HashSet<>());
        Set<Ticket> tickets = placeIdTicketMap.getOrDefault(placeId, new HashSet<>());
        return PlaceReportDTO.builder()
                .placeNumber(placeId)
                .numberOfIncidents(incidents.size())
                .numberOfTickets(tickets.size())
                .paidTimeInMinutes(tickets.stream()
                        .mapToLong(t -> t.getDurationInMillis() / 1000 / 60)
                        .sum())
                .build();
    }

    private TicketReportDTO prepareTicketReport(List<Ticket> tickets) {
        LongSummaryStatistics stats = tickets.stream()
                .mapToLong(t -> t.getDurationInMillis() / 1000 / 60)
                .summaryStatistics();

        return TicketReportDTO.builder()
                .numberOfTickets(tickets.size())
                .totalSumOfPaidTimeInMinutes(stats.getSum())
                .avgTicketDurationTimeInMinutes(stats.getAverage())
                .maxTicketDurationInMinutes(stats.getMax())
                .minTicketDurationInMinutes(stats.getMin())
                .build();
    }

    private Set<UserReportDTO> prepareUsersReport(List<ParkingUser> users) {
        return users.stream()
                .map(this::toUserReport)
                .collect(Collectors.toSet());
    }

    private UserReportDTO toUserReport(ParkingUser user) {
        return UserReportDTO.builder()
                .numberOfIncidents(user.getIncidents().size())
                .areaNumber(user.getArea().getAreaId())
                .username(user.getUsername())
                .build();
    }

    private Map<Long, Set<Incident>> preparePlaceIncidentData(List<Place> placesWithIncidents) {
        Map<Long, Set<Incident>> placeIdIncidentMap = new HashMap<>();

        placesWithIncidents.forEach(p -> {
            long placeId = p.getPlaceId();
            placeIdIncidentMap.putIfAbsent(placeId, p.getIncidents());
            placeIdIncidentMap.computeIfPresent(placeId, (k, v) -> {
                v.addAll(p.getIncidents());
                return v;
            });
        });

        return placeIdIncidentMap;
    }

    private Map<Long, Set<Ticket>> preparePlaceTicketData(List<Place> placesWithTickets) {
        Map<Long, Set<Ticket>> placeIdTicketMap = new HashMap<>();

        placesWithTickets.forEach(p -> {
            long placeId = p.getPlaceId();
            placeIdTicketMap.putIfAbsent(placeId, p.getTickets());
            placeIdTicketMap.computeIfPresent(placeId, (k, v) -> {
                v.addAll(p.getTickets());
                return v;
            });
        });

        return placeIdTicketMap;
    }

    private Set<AreaReportDTO> prepareAreasReport(List<Place> placesWithIncidents, List<Place> placesWithTickets) {
        Map<Long, Set<Incident>> areaIdIncidentMap = prepareAreaIncidentData(placesWithIncidents);
        Map<Long, Set<Ticket>> areaIdTicketMap = prepareAreaTicketData(placesWithTickets);
        List<Long> areaIds = parkingDAO.getAreaIds();
        return areaIds.stream()
                .filter(p -> areaIdIncidentMap.containsKey(p) || areaIdTicketMap.containsKey(p))
                .map(id -> getAreaReportDTO(areaIdIncidentMap, areaIdTicketMap, id))
                .collect(Collectors.toSet());

    }

    private AreaReportDTO getAreaReportDTO(Map<Long, Set<Incident>> placeIdIncidentMap, Map<Long, Set<Ticket>> placeIdTicketMap, Long areaId) {
        Set<Incident> incidents = placeIdIncidentMap.getOrDefault(areaId, new HashSet<>());
        Set<Ticket> tickets = placeIdTicketMap.getOrDefault(areaId, new HashSet<>());
        return AreaReportDTO.builder()
                .areaNumber(areaId)
                .numberOfIncidents(incidents.size())
                .numberOfTickets(tickets.size())
                .paidTimeInMinutes(tickets.stream()
                        .mapToLong(t -> t.getDurationInMillis() / 1000 / 60)
                        .sum())
                .build();
    }


    private Map<Long, Set<Incident>> prepareAreaIncidentData(List<Place> placesWithIncidents) {
        Map<Long, Set<Incident>> areaIdIncidentMap = new HashMap<>();

        placesWithIncidents.forEach(p -> {
            long areaId = p.getParkingMeter().getArea().getAreaId();
            areaIdIncidentMap.putIfAbsent(areaId, p.getIncidents());
            areaIdIncidentMap.computeIfPresent(areaId, (k, v) -> {
                v.addAll(p.getIncidents());
                return v;
            });
        });

        return areaIdIncidentMap;
    }

    private Map<Long, Set<Ticket>> prepareAreaTicketData(List<Place> placesWithTickets) {
        Map<Long, Set<Ticket>> areaIdTicketMap = new HashMap<>();

        placesWithTickets.forEach(p -> {
            long areaId = p.getParkingMeter().getArea().getAreaId();
            areaIdTicketMap.putIfAbsent(areaId, p.getTickets());
            areaIdTicketMap.computeIfPresent(areaId, (k, v) -> {
                v.addAll(p.getTickets());
                return v;
            });
        });

        return areaIdTicketMap;
    }
}
