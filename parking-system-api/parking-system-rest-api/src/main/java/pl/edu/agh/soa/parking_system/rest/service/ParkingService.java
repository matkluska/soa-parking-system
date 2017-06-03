package pl.edu.agh.soa.parking_system.rest.service;

import com.google.common.collect.Sets;
import pl.edu.agh.soa.contracts.AreaDTO;
import pl.edu.agh.soa.contracts.PlaceDTO;
import pl.edu.agh.soa.controller.ParkingDAO;
import pl.edu.agh.soa.model.Place;
import pl.edu.agh.soa.model.Ticket;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Mateusz Kluska
 */
@Stateless
public class ParkingService {
    @Inject
    private ParkingDAO parkingDAO;

    public List<AreaDTO> getAreas() {
        Map<Long, Set<PlaceDTO>> areaPlaceMap = getAreaIdPlaceDTOMap();
        return areaPlaceMap.entrySet().stream()
                .map(this::toAreaDTO)
                .collect(Collectors.toList());
    }

    public AreaDTO getArea(long areaId) {
        Map<Long, Set<PlaceDTO>> areaPlaceMap = getAreaIdPlaceDTOMap();
        return areaPlaceMap.entrySet().stream()
                .filter(e -> e.getKey() == areaId)
                .map(this::toAreaDTO)
                .findAny()
                .orElseThrow(NotFoundException::new);
    }

    private Map<Long, Set<PlaceDTO>> getAreaIdPlaceDTOMap() {
        List<Place> places = parkingDAO.findAllPlaces();
        Map<Long, Set<PlaceDTO>> areaPlaceMap = new HashMap<>();
        places.forEach(p -> {
            long areaId = p.getParkingMeter().getArea().getAreaId();
            areaPlaceMap.putIfAbsent(areaId, Sets.newHashSet(toPlaceDTO(p)));
            areaPlaceMap.computeIfPresent(areaId, (k, v) -> {
                v.add(toPlaceDTO(p));
                return v;
            });
        });
        return areaPlaceMap;
    }

    private AreaDTO toAreaDTO(Map.Entry<Long, Set<PlaceDTO>> e) {
        return AreaDTO.builder()
                .areaNumber(e.getKey())
                .places(e.getValue())
                .build();
    }

    private PlaceDTO toPlaceDTO(Place place) {
        boolean isPaid = place.getTickets().stream().anyMatch(Ticket::isValid);
        return PlaceDTO.builder()
                .placeNumber(place.getPlaceId())
                .isBusy(place.isBusy())
                .isPaid(isPaid)
                .build();
    }
}
