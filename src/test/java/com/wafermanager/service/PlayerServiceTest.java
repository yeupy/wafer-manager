package com.wafermanager.service;

import com.wafermanager.AbstractTest;
import com.wafermanager.entity.Player;
import com.wafermanager.entity.Statistic;
import com.wafermanager.entity.Tier;
import com.wafermanager.mapper.PlayerMapper;
import com.wafermanager.mapper.StatisticMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerServiceTest extends AbstractTest {

    @Autowired
    StatisticMapper statistics;

    @Autowired
    PlayerMapper players;

    @Autowired
    PlayerService playerService;

    @Autowired
    StatisticService statisticService;

    static Player[] playerArr;

    @BeforeAll
    static void beforeAll() {
        playerArr = new Player[20];
    }

    @BeforeEach
    void beforeEach() {
        players.deleteAll();
        statistics.update(new Statistic(0));
        statisticService.afterPropertiesSet();

        assertEquals(0, statisticService.getTotal());
        assertEquals(statistics.read().getTotal(), statisticService.getTotal());
        for (int i = 0, l = playerArr.length; i < l; i++) {
            playerArr[i] = new Player(Integer.valueOf(i).longValue(), i);
            playerService.create(playerArr[i]);
        }
        assertEquals(playerArr.length, statisticService.getTotal());
        assertEquals(statistics.read().getTotal(), statisticService.getTotal());
    }

    @Test
    void create() {
        for (int i = 0, l = playerArr.length; i < l; i++) {
            int _i = i;
            assertThrows(Exception.class, () -> playerService.create(playerArr[_i]));
        }
        assertEquals(playerArr.length, statisticService.getTotal());
        assertEquals(statistics.read().getTotal(), statisticService.getTotal());
    }

    @Test
    void read() {
        for (int i = 0, l = playerArr.length; i < l; i++) {
            assertEquals(playerArr[i], playerService.read(playerArr[i].getId()));
        }

        // non-existent.
        assertNull(playerService.read(-1));
    }

    @Test
    void update() {
        playerArr[0].setMmr(-1);
        assertEquals(1, playerService.update(playerArr[0]));
        assertEquals(playerArr[0], playerService.read(playerArr[0].getId()));

        // non-existent.
        playerArr[0].setId(Long.valueOf(-1));
        assertEquals(0, playerService.update(playerArr[0]));
    }

    @Test
    void delete() {
        for (int i = 0, l = playerArr.length; i < l; i++) {
            int _i = i;
            assertEquals(1, playerService.delete(playerArr[_i].getId()));
        }
        for (int i = 0, l = playerArr.length; i < l; i++) {
            assertNull(playerService.read(playerArr[i].getId()));
        }
        assertEquals(0, statisticService.getTotal());
        assertEquals(statistics.read().getTotal(), statisticService.getTotal());

        // non-existent.
        for (int i = 0, l = playerArr.length; i < l; i++) {
            int _i = i;
            assertEquals(0, playerService.delete(playerArr[_i].getId()));
        }
        assertEquals(0, statisticService.getTotal());
        assertEquals(statistics.read().getTotal(), statisticService.getTotal());
    }

    @Test
    void getRank() {
        for (int i = 0, l = playerArr.length; i < l; i++) {
            assertEquals(l - i, playerService.getRank(playerArr[i].getId()));
        }

        // equal rank.
        playerArr[3].setMmr(playerArr[4].getMmr());
        playerService.update(playerArr[3]);
        assertEquals(playerService.getRank(playerArr[4].getId()), playerService.getRank(playerArr[3].getId()));
    }

    @Test
    void getTier() {
        playerArr = new Player[101];
        beforeEach();
        assertEquals(Tier.CHALLENGER, playerService.getTier(playerArr[100].getId()));

        playerArr = new Player[100];
        beforeEach();
        assertEquals(Tier.MASTER, playerService.getTier(playerArr[99].getId()));
        assertEquals(Tier.DIAMOND, playerService.getTier(playerArr[95].getId()));
        assertEquals(Tier.PLATINUM, playerService.getTier(playerArr[90].getId()));
        assertEquals(Tier.GOLD, playerService.getTier(playerArr[75].getId()));
        assertEquals(Tier.SILVER, playerService.getTier(playerArr[35].getId()));
        assertEquals(Tier.BRONZE, playerService.getTier(playerArr[0].getId()));

        playerArr = new Player[20];
    }

    @Test
    void listTop10() {
        // rank range 1 - 10.
        List<Player> playerList = playerService.listTop10();
        assertEquals(10, playerList.size());
        for (int i = 0; i < 10; i++) {
            Player player = playerList.get(i);
            assertEquals(i + 1, player.getRank());
        }

        // equal rank.
        playerArr[18].setMmr(playerArr[19].getMmr());
        playerService.update(playerArr[18]);
        playerList = playerService.listTop10();
        assertEquals(playerList.get(0).getRank(), playerList.get(1).getRank());
    }

    @Test
    void listNear() {
        // rank range 1 - 6.
        List<Player> playerList = playerService.listNear(playerArr[19].getId());
        assertEquals(6, playerList.size());
        for (int i = 0; i < 6; i++) {
            Player player = playerList.get(i);
            assertEquals(i + 1, player.getRank());
        }

        // rank range 5 - 15.
        playerList = playerService.listNear(playerArr[10].getId());
        assertEquals(11, playerList.size());
        for (int i = 0; i < 11; i++) {
            Player player = playerList.get(i);
            assertEquals(5 + i, player.getRank());
        }

        // rank range 15 - 20.
        playerList = playerService.listNear(playerArr[0].getId());
        assertEquals(6, playerList.size());
        for (int i = 0; i < 6; i++) {
            Player player = playerList.get(i);
            assertEquals(15 + i, player.getRank());
        }
    }
}