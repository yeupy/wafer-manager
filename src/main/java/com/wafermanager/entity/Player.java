package com.wafermanager.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("player")
public class Player {

    private Long id;
    private Integer mmr;

    @JsonProperty(access= JsonProperty.Access.READ_ONLY)
    private Integer rank;

    @JsonProperty(access= JsonProperty.Access.READ_ONLY)
    private Tier tier;

    public Player(Long id, Integer mmr) {
        this.id = id;
        this.mmr = mmr;
    }

}