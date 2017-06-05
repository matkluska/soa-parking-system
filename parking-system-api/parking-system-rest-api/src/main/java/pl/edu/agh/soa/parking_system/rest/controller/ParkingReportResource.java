package pl.edu.agh.soa.parking_system.rest.controller;

import pl.edu.agh.soa.contracts.raport.ReportDTO;
import pl.edu.agh.soa.parking_system.rest.service.ParkingReportService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * @author Mateusz Kluska
 */
@Path("/report")
public class ParkingReportResource {
    private static final long MONTH_MILLIS = 1000 * 60 * 60 * 24 * 30L;
    private static final long WEEK_MILLIS = 1000 * 60 * 60 * 24 * 7L;
    private static final long DAY_MILLIS = 1000 * 60 * 60 * 24L;
    private static final long HOUR_MILLIS = 1000 * 60 * 60L;

    @Inject
    private ParkingReportService reportService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ReportDTO getReportJson(@QueryParam("months") int months,
                                   @QueryParam("weeks") int weeks,
                                   @QueryParam("days") int days,
                                   @QueryParam("hours") int hours) {
        return getReportDTO(months, weeks, days, hours);
    }

    @GET
    @Path("/xml")
    @Produces(MediaType.APPLICATION_XML)
    public ReportDTO getReportXml(@QueryParam("months") int months,
                                  @QueryParam("weeks") int weeks,
                                  @QueryParam("days") int days,
                                  @QueryParam("hours") int hours) {
        return getReportDTO(months, weeks, days, hours);
    }

    private ReportDTO getReportDTO(@QueryParam("months") int months, @QueryParam("weeks") int weeks, @QueryParam("days") int days, @QueryParam("hours") int hours) {
        long now = System.currentTimeMillis();
        now -= MONTH_MILLIS * months;
        now -= WEEK_MILLIS * weeks;
        now -= DAY_MILLIS * days;
        now -= HOUR_MILLIS * hours;

        return reportService.prepareReport(now);
    }
}
