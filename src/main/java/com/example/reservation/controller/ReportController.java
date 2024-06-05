package com.example.reservation.controller;

import com.example.reservation.dto.EquipmentReportResponse;
import com.example.reservation.dto.ReportResponse;
import com.example.reservation.service.ReportService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService reportService;
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }
    @GetMapping("/room/{id}")
    public ReportResponse getRoomReport(@PathVariable long id) {
        return reportService.getOneRoomReport(id);
    }

    @GetMapping("/equipment")
    public EquipmentReportResponse getEquipmentReport() {
        return reportService.getEquipmentReport();
    }

    @GetMapping("/room")
    public ReportResponse getRoomReport(@Param("startDate") Date startDate, @Param("endDate") Date endDate) {
        return reportService.getReportBetweenDates(startDate, endDate);
    }
}
