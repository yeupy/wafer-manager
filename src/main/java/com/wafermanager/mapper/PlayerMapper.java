package com.wafermanager.mapper;

import com.wafermanager.entity.Player;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlayerMapper {

    int create(Player player);

    Player read(long id);

    int update(Player player);

    int delete(long id);

    @Delete("DELETE FROM player")
    int deleteAll();

    int getRank(Player player, int total);

    List<Player> listBelow(Player player, int limit, int total);

    List<Player> listAbove(Player player, int limit, int total);

    List<Player> listTop(int limit, int total);

}
