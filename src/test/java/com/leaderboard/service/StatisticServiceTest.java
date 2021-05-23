package com.leaderboard.service;

import com.leaderboard.AbstractTest;
import com.leaderboard.entity.Statistic;
import com.leaderboard.mapper.StatisticMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticServiceTest extends AbstractTest {

    @Autowired
    StatisticService statisticService;

    @Autowired
    StatisticMapper statistics;

    @BeforeEach
    void beforeEach() {
        statistics.update(new Statistic(0));
        statisticService.afterPropertiesSet();
    }

    @Test
    void getStatistic() {
        assertEquals(statistics.read(), statisticService.getStatistic());
    }

    @Test
    void increaseTotal() {
        assertEquals(1, statisticService.increaseTotal(1));
        assertEquals(1, statisticService.getTotal());
        assertEquals(statistics.read().getTotal(), statisticService.getTotal());
        assertEquals(1, statisticService.increaseTotal(1));
        assertEquals(2, statisticService.getTotal());
        assertEquals(statistics.read().getTotal(), statisticService.getTotal());
        assertEquals(1, statisticService.increaseTotal(2));
        assertEquals(4, statisticService.getTotal());
        assertEquals(statistics.read().getTotal(), statisticService.getTotal());
    }

    @Test
    void decreaseTotal() {
        statisticService.increaseTotal(5);
        assertEquals(1, statisticService.decreaseTotal(1));
        assertEquals(4, statisticService.getTotal());
        assertEquals(statistics.read().getTotal(), statisticService.getTotal());
        assertEquals(1, statisticService.decreaseTotal(1));
        assertEquals(3, statisticService.getTotal());
        assertEquals(statistics.read().getTotal(), statisticService.getTotal());
        assertEquals(1, statisticService.decreaseTotal(3));
        assertEquals(0, statisticService.getTotal());
        assertEquals(statistics.read().getTotal(), statisticService.getTotal());
    }

    @Test
    void getTotal() {
        assertEquals(0, statisticService.getTotal());
    }
}