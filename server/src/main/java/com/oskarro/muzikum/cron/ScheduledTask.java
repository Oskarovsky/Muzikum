package com.oskarro.muzikum.cron;

import com.oskarro.muzikum.user.UserService;
import com.oskarro.muzikum.video.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Component enables to schedule tasks to run periodically at a specified date/time.
 * And it's naturally a great tool for automating lots of process runs.
 * Cron runs as a daemon process. This means it only needs to be started once, so it will keep running in the background.
 *
 * */

@Slf4j
@Component
public class ScheduledTask {

    private final UserService userService;
    private final VideoService videoService;

    public ScheduledTask(UserService userService, VideoService videoService) {
        this.userService = userService;
        this.videoService = videoService;
    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Scheduled(cron = "0 0 0 1 * ?")
    public void resetMonthlyStatsJob() {
        log.info("Monthly stats reset correctly {}", dateFormat.format(new Date()));
        userService.resetMonthlyStatsForUploading();
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void resetWeeklyStatsJob() {
        log.info("Weekly stats reset correctly {}", dateFormat.format(new Date()));
        userService.resetWeeklyStatsForUploading();
    }

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void updateYoutubeStatistics() {
        log.info("Youtube statistics updating {}", dateFormat.format(new Date()));
        videoService.updateVideoStatistics();
    }

}
