package com.oskarro.muzikum.monitor;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Monitor area exists to verify the operation of the application.
 * Implemented methods show page statistics
 * */

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/stat")
public class MetricController {

    MetricService metricService;

    public MetricController(MetricServiceImpl metricService) {
        this.metricService = metricService;
    }

    /**
     * Response body contains information about the number of returned statuses
     * */
    @GetMapping(value = "/status-metric")
    @ResponseBody
    public Map<Integer, Integer> getStatusMetric() {
        return metricService.getStatusMetric();
    }

    /**
     * Response body contains query counter for individual endpoints
     * */
    @GetMapping(value = "/metric")
    @ResponseBody
    public Map<String, ConcurrentHashMap<Integer, Integer>> getMetric() {
        return metricService.getFullMetric();
    }

    /**
     * Response body contains metrics data which can be used in gui
     * */
    @GetMapping(value = "/metric-data")
    @ResponseBody
    public Object[][] getMetricData() {
        return metricService.getGraphData();
    }



}
