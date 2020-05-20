package com.oskarro.muzikum.statistics;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/stat")
public class MetricController {

    MetricService metricService;

    public MetricController(MetricServiceImpl metricService) {
        this.metricService = metricService;
    }

    @GetMapping(value = "/status-metric")
    @ResponseBody
    public Map<Integer, Integer> getStatusMetric() {
        System.out.println(metricService.getStatusMetric());
        return metricService.getStatusMetric();
    }


}
