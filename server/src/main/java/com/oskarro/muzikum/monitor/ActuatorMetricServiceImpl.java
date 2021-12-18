package com.oskarro.muzikum.monitor;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ActuatorMetricServiceImpl implements ActuatorMetricService {

    private final MeterRegistry publicMetrics;
    private final List<ArrayList<Integer>> statusMetricsByMinute;
    private final List<String> statusList;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public ActuatorMetricServiceImpl(MeterRegistry publicMetrics) {
        super();
        statusMetricsByMinute = new ArrayList<>();
        statusList = new ArrayList<>();
        this.publicMetrics = publicMetrics;
    }

    @Override
    public Object[][] getGraphData() {
        final Date current = new Date();
        final int colCount = statusList.size() + 1;
        final int rowCount = statusMetricsByMinute.size() + 1;
        final Object[][] result = new Object[rowCount][colCount];
        result[0][0] = "Time";
        int j = 1;

        for (final String status : statusList) {
            result[0][j] = status;
            j++;
        }

        for (int i = 1; i < rowCount; i++) {
            result[i][0] = dateFormat.format(new Date(current.getTime() - (60000L * (rowCount - i))));
        }

        List<Integer> minuteOfStatuses;
        List<Integer> last = new ArrayList<>();

        for (int i = 1; i < rowCount; i++) {
            minuteOfStatuses = statusMetricsByMinute.get(i - 1);
            for (j = 1; j <= minuteOfStatuses.size(); j++) {
                result[i][j] = minuteOfStatuses.get(j - 1) - (last.size() >= j ? last.get(j - 1) : 0);
            }
            while (j < colCount) {
                result[i][j] = 0;
                j++;
            }
            last = minuteOfStatuses;
        }
        return result;
    }

    @Scheduled(fixedDelay = 60000)
    private void exportMetrics() {
        final ArrayList<Integer> lastMinuteStatuses = initializeStatuses(statusList.size());

        for (final Meter counterMetric : publicMetrics.getMeters()) {
            updateMetrics(counterMetric, lastMinuteStatuses);
        }

        statusMetricsByMinute.add(lastMinuteStatuses);
    }


    private ArrayList<Integer> initializeStatuses(final int size) {
        final ArrayList<Integer> counterList = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            counterList.add(0);
        }
        return counterList;
    }

    private void updateMetrics(final Meter counterMetric, final ArrayList<Integer> statusCount) {
        String status = "";
        int index = -1;
        int oldCount = 0;

        if (counterMetric.getId().getName().contains("counter.status.")) {
            status = counterMetric.getId().getName().substring(15, 18); // example 404, 200
            appendStatusIfNotExist(status, statusCount);
            index = statusList.indexOf(status);
            oldCount = statusCount.get(index) == null ? 0 : statusCount.get(index);
            statusCount.set(index, (int)((Counter) counterMetric).count() + oldCount);
        }
    }

    private void appendStatusIfNotExist(final String status, final ArrayList<Integer> statusCount) {
        if (!statusList.contains(status)) {
            statusList.add(status);
            statusCount.add(0);
        }
    }


}
