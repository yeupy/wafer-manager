package com.leaderboard.mapper;

import com.leaderboard.entity.Statistic;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StatisticMapper {

    Statistic read();

    int update(Statistic statistic);
}
