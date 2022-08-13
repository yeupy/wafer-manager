package com.wafermanager.mapper;

import com.wafermanager.AbstractTest;
import com.wafermanager.entity.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerMapperTest extends AbstractTest {

    @Autowired
    PlayerMapper players;

    static Player[] playerArr;

    @BeforeAll
    static void beforeAll() {
        playerArr = new Player[5];
    }

    @BeforeEach
    void beforeEach() {
        players.deleteAll();
        for (int i = 0, l = playerArr.length; i < l; i++) {
            playerArr[i] = new Player(Integer.valueOf(i).longValue(), i);
            players.create(playerArr[i]);
        }
    }

    @Test
    void create() {
        for (int i = 0, l = playerArr.length; i < l; i++) {
            int _i = i;
            assertThrows(Exception.class, () -> players.create(playerArr[_i]));
        }
    }

    @Test
    void read() {
        for (int i = 0, l = playerArr.length; i < l; i++) {
            assertEquals(playerArr[i], players.read(playerArr[i].getId()));
        }

        // non-existent.
        assertNull(players.read(-1));
    }

    @Test
    void update() {
        playerArr[0].setMmr(-1);
        assertEquals(1, players.update(playerArr[0]));
        assertEquals(playerArr[0], players.read(playerArr[0].getId()));

        // non-existent.
        playerArr[0].setId(Long.valueOf(-1));
        assertEquals(0, players.update(playerArr[0]));
    }

    @Test
    void delete() {
        for (int i = 0, l = playerArr.length; i < l; i++) {
            assertEquals(1, players.delete(playerArr[i].getId()));
        }
        for (int i = 0, l = playerArr.length; i < l; i++) {
            assertNull(players.read(playerArr[i].getId()));
        }

        // non-existent.
        assertEquals(0, players.delete(-1));
    }

    @Test
    void getRank() {
        assertEquals(5, players.getRank(playerArr[0], playerArr.length));
        assertEquals(4, players.getRank(playerArr[1], playerArr.length));
        assertEquals(3, players.getRank(playerArr[2], playerArr.length));
        assertEquals(2, players.getRank(playerArr[3], playerArr.length));
        assertEquals(1, players.getRank(playerArr[4], playerArr.length));

        // equal rank.
        playerArr[3].setMmr(playerArr[4].getMmr());
        players.update(playerArr[3]);
        assertEquals(players.getRank(playerArr[4], playerArr.length), players.getRank(playerArr[3], playerArr.length));
    }

    @Test
    void listBelow() {
        // rank range 2 - 4.
        List<Player> playerList = players.listBelow(playerArr[4], 3, playerArr.length);
        assertEquals(3, playerList.size());
        assertEquals(3, playerList.get(0).getMmr());
        assertEquals(2, playerList.get(1).getMmr());
        assertEquals(1, playerList.get(2).getMmr());

        // rank range 3 - 5.
        playerList = players.listBelow(playerArr[3], 3, playerArr.length);
        assertEquals(3, playerList.size());
        assertEquals(2, playerList.get(0).getMmr());
        assertEquals(1, playerList.get(1).getMmr());
        assertEquals(0, playerList.get(2).getMmr());

        // equal rank 3 - 3 - 5.
        playerArr[1].setMmr(2);
        players.update(playerArr[1]);
        playerList = players.listBelow(playerArr[3], 3, playerArr.length);
        assertEquals(2, playerList.get(0).getMmr());
        assertEquals(2, playerList.get(1).getMmr());
        assertEquals(0, playerList.get(2).getMmr());
    }

    @Test
    void listAbove() {
        // rank range 1 - 3.
        List<Player> playerList = players.listAbove(playerArr[1], 3, playerArr.length);
        assertEquals(3, playerList.size());
        assertEquals(4, playerList.get(0).getMmr());
        assertEquals(3, playerList.get(1).getMmr());
        assertEquals(2, playerList.get(2).getMmr());

        // rank range 2 - 4.
        playerList = players.listAbove(playerArr[0], 3, playerArr.length);
        assertEquals(3, playerList.size());
        assertEquals(3, playerList.get(0).getMmr());
        assertEquals(2, playerList.get(1).getMmr());
        assertEquals(1, playerList.get(2).getMmr());

        // equal rank 1 - 1 - 3.
        playerArr[3].setMmr(4);
        players.update(playerArr[3]);
        playerList = players.listAbove(playerArr[1], 3, playerArr.length);
        assertEquals(3, playerList.size());
        assertEquals(4, playerList.get(0).getMmr());
        assertEquals(4, playerList.get(1).getMmr());
        assertEquals(2, playerList.get(2).getMmr());
    }

    @Test
    void listTop() {
        // rank range 1 - 3.
        List<Player> playerList = players.listTop(3, playerArr.length);
        assertEquals(3, playerList.size());
        assertEquals(4, playerList.get(0).getMmr());
        assertEquals(3, playerList.get(1).getMmr());
        assertEquals(2, playerList.get(2).getMmr());
    }

}