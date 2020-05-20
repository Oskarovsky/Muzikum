package com.oskarro.muzikum.monitor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface MetricService {

    void increaseCount(final String request, final int status);

    Map<Integer, Integer> getStatusMetric();

    Map<String, ConcurrentHashMap<Integer, Integer>> getFullMetric();

    Object[][] getGraphData();


}
