package com.wafermanager.service;

import com.wafermanager.AbstractTest;
import com.wafermanager.entity.MasterData;
import com.wafermanager.mapper.MasterDataMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MasterDataServiceTest extends AbstractTest {

    @Autowired
    MasterDataService masterDataService;

    @Autowired
    MasterDataMapper masters;

    static MasterData[] masterDataArr;

    @BeforeAll
    static void beforeAll() {
        masterDataArr = new MasterData[10];
    }

    @BeforeEach
    void beforeEach() {
        for (int i = 0, l = masterDataArr.length; i < l; i++) {
            masterDataArr[i] = new MasterData(String.valueOf(i), i, i, i, String.valueOf(i), LocalDateTime.now());
            masters.create(masterDataArr[i]);
        }
    }

    @AfterEach
    void afterEach() {
        for (int i = 0, l = masterDataArr.length; i < l; i++) {
            masters.delete(masterDataArr[i].getUid());
        }
        masters.delete("-1");
    }

    @Test
    void create() {
        for (int i = 0, l = masterDataArr.length; i < l; i++) {
            int _i = i;
            assertThrows(Exception.class, () -> masterDataService.create(masterDataArr[_i]));
        }
        MasterData md = new MasterData("-1", -1, -1, -1, "-1", LocalDateTime.now());
        assertEquals(1, masterDataService.create(md));
    }

    @Test
    void read() {
        for (int i = 0, l = masterDataArr.length; i < l; i++) {
            assertEquals(masterDataArr[i], masterDataService.read(masterDataArr[i].getUid()));
        }
    }

    @Test
    void update() {
        masterDataArr[0].setColumnA(-1);
        assertEquals(1, masterDataService.update(masterDataArr[0]));
        assertEquals(masterDataArr[0], masterDataService.read(masterDataArr[0].getUid()));
    }

    @Test
    void list() {
        List<MasterData> retrieved = null;

        retrieved = masterDataService.list("0",3, 0);
        assertEquals(1, retrieved.size());
        assertEquals(masterDataArr[0], retrieved.get(0));

        List<MasterData> masterDataList = Arrays.stream(masterDataArr).collect(Collectors.toList());
        List<MasterData> _masterDataList = masterDataList;

        retrieved = masterDataService.list(null,3, 0);
        assertEquals(3, retrieved.size());
        retrieved.forEach(e -> assertTrue(_masterDataList.contains(e)));

        retrieved = masterDataService.list(null,3, 1);
        assertEquals(3, retrieved.size());
        retrieved.forEach(e -> assertTrue(_masterDataList.contains(e)));

        retrieved = masterDataService.list(null,3, 2);
        assertEquals(3, retrieved.size());
        retrieved.forEach(e -> assertTrue(_masterDataList.contains(e)));

        retrieved = masterDataService.list(null,3, 3);
        assertEquals(1, retrieved.size());
        retrieved.forEach(e -> assertTrue(_masterDataList.contains(e)));
    }

    @Test
    void count() {
        assertEquals(10, masterDataService.count(null));
        assertEquals(1, masterDataService.count("0"));
    }
}