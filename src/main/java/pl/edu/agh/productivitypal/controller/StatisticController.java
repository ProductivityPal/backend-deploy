package pl.edu.agh.productivitypal.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.productivitypal.config.Jwt;
import pl.edu.agh.productivitypal.dto.ReportInfoDto;
import pl.edu.agh.productivitypal.model.EnergyLevelInfo;
import pl.edu.agh.productivitypal.service.StatisticService;

import java.time.LocalDateTime;
import java.util.List;

import static pl.edu.agh.productivitypal.config.SecurityConstant.AUTHORIZATION_HEADER;

@RestController
@RequestMapping(value = "/statistic")
public class StatisticController {
    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public ResponseEntity<ReportInfoDto> getInfoReport(@RequestHeader(AUTHORIZATION_HEADER) Jwt jwt,
                                                       @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                       @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate){
        System.out.println(startDate);
        return ResponseEntity.ok(statisticService.getInfoReport(jwt, startDate, endDate));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/energyLevel")
    public ResponseEntity<List<EnergyLevelInfo>> getEnergyLevelReport(@RequestHeader(AUTHORIZATION_HEADER) Jwt jwt){
        return ResponseEntity.ok(statisticService.getEnergyLevelReport(jwt));
    }

}
