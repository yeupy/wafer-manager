package com.wafermanager.service;

import com.wafermanager.AbstractTest;
import com.wafermanager.entity.MasterData;
import com.wafermanager.mapper.MasterDataMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
        masters.deleteAll();
        for (int i = 0, l = masterDataArr.length; i < l; i++) {
            masterDataArr[i] = new MasterData(String.valueOf(i), i, i, i, String.valueOf(i), LocalDateTime.now());
            masters.create(masterDataArr[i]);
        }
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
        List<MasterData> masterDataList = null;

        MasterData[] reversed = (MasterData[]) Arrays.stream(masterDataArr)
                .sorted(Comparator.comparing(MasterData::getModifiedDate).reversed()
                        .thenComparing(MasterData::getUid))
                .toArray(MasterData[]::new);

        masterDataList = masterDataService.list(3, 0);
        assertEquals(3, masterDataList.size());
        for (int i = 0, j = 0, l = i + masterDataList.size(); i < l; i++, j++) {
            assertEquals(reversed[i], masterDataList.get(j));
        }

        masterDataList = masterDataService.list(3, 1);
        assertEquals(3, masterDataList.size());
        for (int i = 3, j = 0, l = i + masterDataList.size(); i < l; i++, j++) {
            assertEquals(reversed[i], masterDataList.get(j));
        }

        masterDataList = masterDataService.list(3, 2);
        assertEquals(3, masterDataList.size());
        for (int i = 6, j = 0, l = i + masterDataList.size(); i < l; i++, j++) {
            assertEquals(reversed[i], masterDataList.get(j));
        }

        masterDataList = masterDataService.list(3, 3);
        assertEquals(1, masterDataList.size());
        for (int i = 9, j = 0, l = i + masterDataList.size(); i < l; i++, j++) {
            assertEquals(reversed[i], masterDataList.get(j));
        }
    }
}