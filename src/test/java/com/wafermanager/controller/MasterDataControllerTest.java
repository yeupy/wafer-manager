package com.wafermanager.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wafermanager.AbstractControllerTest;
import com.wafermanager.entity.MasterData;
import com.wafermanager.mapper.MasterDataMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MasterDataControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MasterDataMapper masters;

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
    void read() throws Exception {
        for (int i = 0, l = masterDataArr.length; i < l; i++) {
            mockMvc.perform(get("/master/{i}", i)).andExpect(status().isOk());
        }
    }

    @Test
    void list() throws Exception {
        List<MasterData> masterDataList = null;

        MasterData[] reversed = (MasterData[]) Arrays.stream(masterDataArr)
                .sorted(Comparator.comparing(MasterData::getModifiedDate).reversed()
                        .thenComparing(MasterData::getUid))
                .toArray(MasterData[]::new);

        MvcResult result = mockMvc.perform(get("/master")
                    .param("size","3")
                    .param("page","0"))
                .andExpect(status().isOk()).andReturn();

        masterDataList = convert(result, new TypeReference<List<MasterData>>() {});
        assertEquals(3, masterDataList.size());
        for (int i = 0, j = 0, l = i + masterDataList.size(); i < l; i++, j++) {
            assertEquals(reversed[i], masterDataList.get(j));
        }

        result = mockMvc.perform(get("/master")
                        .param("size","3")
                        .param("page","1"))
                .andExpect(status().isOk()).andReturn();

        masterDataList = convert(result, new TypeReference<List<MasterData>>() {});
        assertEquals(3, masterDataList.size());
        for (int i = 3, j = 0, l = i + masterDataList.size(); i < l; i++, j++) {
            assertEquals(reversed[i], masterDataList.get(j));
        }

        result = mockMvc.perform(get("/master")
                        .param("size","3")
                        .param("page","2"))
                .andExpect(status().isOk()).andReturn();

        masterDataList = convert(result, new TypeReference<List<MasterData>>() {});
        assertEquals(3, masterDataList.size());
        for (int i = 6, j = 0, l = i + masterDataList.size(); i < l; i++, j++) {
            assertEquals(reversed[i], masterDataList.get(j));
        }

        result = mockMvc.perform(get("/master")
                        .param("size","3")
                        .param("page","3"))
                .andExpect(status().isOk()).andReturn();

        masterDataList = convert(result, new TypeReference<List<MasterData>>() {});
        assertEquals(1, masterDataList.size());
        for (int i = 9, j = 0, l = i + masterDataList.size(); i < l; i++, j++) {
            assertEquals(reversed[i], masterDataList.get(j));
        }
    }

    static <T> T convert(MvcResult result, TypeReference typeReference) throws Exception {
        return (T) new ObjectMapper().readValue(result.getResponse().getContentAsString(), typeReference);
    }
}