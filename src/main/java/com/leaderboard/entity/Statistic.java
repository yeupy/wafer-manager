package com.leaderboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("stat")
public class Statistic {

    @JsonIgnore
    private final int id = 0;

    @JsonProperty(value="totalPlayerCount")
    private Integer total;
}
