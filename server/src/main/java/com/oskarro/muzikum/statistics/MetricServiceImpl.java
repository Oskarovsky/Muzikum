package com.oskarro.muzikum.statistics;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class MetricServiceImpl implements MetricService{

    private ConcurrentMap<String, ConcurrentHashMap<Integer, Integer>> metricMap;
    private final ConcurrentMap<Integer, Integer> statusMetric;
    private ConcurrentMap<String, ConcurrentHashMap<Integer, Integer>> timeMap;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public MetricServiceImpl() {
        super();
        metricMap = new ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>();
        statusMetric = new ConcurrentHashMap<Integer, Integer>();
        timeMap = new ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>();
    }

    @Override
    public void increaseCount(final String request, final int status) {
        //increaseMainMetric(request, status);
        increaseStatusMetric(status);
        //updateTimeMap(status);
    }

    public Map<Integer, Integer> getStatusMetric() {
        return statusMetric;
    }

    private void increaseStatusMetric(final int status) {
        final Integer statusCount = statusMetric.get(status);
        if (statusCount == null) {
            statusMetric.put(status, 1);
        } else {
            statusMetric.put(status, statusCount + 1);
        }
    }


}
