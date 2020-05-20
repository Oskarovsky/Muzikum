package com.oskarro.muzikum.statistics;

import java.util.Map;

public interface MetricService {

    void increaseCount(final String request, final int status);

    Map<Integer, Integer> getStatusMetric();

/*    Map getFullMetric();

    Map getStatusMetric();

    Object[][] getGraphData();*/


}
