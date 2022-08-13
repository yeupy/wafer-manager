package com.wafermanager.entity;

import org.apache.ibatis.type.Alias;

@Alias("tier")
public enum Tier {
    CHALLENGER,
    MASTER,
    DIAMOND,
    PLATINUM,
    GOLD,
    SILVER,
    BRONZE
}
