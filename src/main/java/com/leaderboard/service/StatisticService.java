package com.leaderboard.service;

import com.leaderboard.mapper.StatisticMapper;
import com.leaderboard.entity.Statistic;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticService implements InitializingBean {

    private final StatisticMapper statistics;
    private static Statistic stat;

    public int increaseTotal(int result) {
        stat.setTotal(stat.getTotal() + result);
        return statistics.update(stat);
    }

    public int decreaseTotal(int result) {
        stat.setTotal(stat.getTotal() - result);
        return statistics.update(stat);
    }

    public int getTotal() {
        return stat.getTotal();
    }

    public Statistic getStatistic() {
        return statistics.read();
    }

    @Override
    public void afterPropertiesSet() {
        stat = statistics.read();
    }
}
