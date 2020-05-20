package com.oskarro.muzikum.statistics;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
        return metricService.getStatusMetric();
    }

    @GetMapping(value = "/metric")
    @ResponseBody
    public Map<String, ConcurrentHashMap<Integer, Integer>> getMetric() {
        return metricService.getFullMetric();
    }



}
