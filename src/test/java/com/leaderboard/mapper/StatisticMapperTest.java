package com.leaderboard.mapper;

import com.leaderboard.AbstractTest;
import com.leaderboard.entity.Statistic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticMapperTest extends AbstractTest {

    @Autowired
    StatisticMapper statistics;

    @BeforeEach
    void beforeEach() {
        statistics.update(new Statistic(0));
    }

    @Test
    void read() {
        assertEquals(0, statistics.read().getTotal());
    }

    @Test
    void update() {
        assertEquals(1, statistics.update(new Statistic(1)));
        assertEquals(1, statistics.read().getTotal());
        assertEquals(1, statistics.update(new Statistic(2)));
        assertEquals(2, statistics.read().getTotal());
    }
}