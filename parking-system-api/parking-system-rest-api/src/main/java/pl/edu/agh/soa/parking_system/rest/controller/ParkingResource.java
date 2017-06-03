package pl.edu.agh.soa.parking_system.rest.controller;

import pl.edu.agh.soa.contracts.AreaDTO;
import pl.edu.agh.soa.parking_system.rest.service.ParkingService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Mateusz Kluska
 */
@Path("/areas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParkingResource {
    @Inject
    private ParkingService parkingService;

    @GET
    public List<AreaDTO> gerAreaList() {
        return parkingService.getAreas();
    }

    @GET
    @Path("/{id}")
    public AreaDTO getArea(@PathParam("id") long id) {
        return parkingService.getArea(id);
    }

    @GET
    @Path("/xml")
    @Produces(MediaType.APPLICATION_XML)
    public List<AreaDTO> gerAreaListXML() {
        return parkingService.getAreas();
    }

    @GET
    @Path("/xml/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public AreaDTO getAreaXML(@PathParam("id") long id) {
        return parkingService.getArea(id);
    }
}
