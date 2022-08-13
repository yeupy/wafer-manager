package com.wafermanager.mapper;

import com.wafermanager.entity.Statistic;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StatisticMapper {

    Statistic read();

    int update(Statistic statistic);
}
