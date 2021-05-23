package com.leaderboard.controller;

import com.leaderboard.service.StatisticService;
import com.leaderboard.entity.Statistic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping("/stat")
    public ResponseEntity<Statistic> stat() {
        return ResponseEntity.ok(statisticService.getStatistic());
    }

}
