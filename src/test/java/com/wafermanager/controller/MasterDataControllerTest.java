package com.wafermanager.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wafermanager.AbstractControllerTest;
import com.wafermanager.entity.MasterData;
import com.wafermanager.mapper.MasterDataMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    void read() throws Exception {
        for (int i = 0, l = masterDataArr.length; i < l; i++) {
            mockMvc.perform(get("/master/{i}", i)).andExpect(status().isOk());
        }
    }

    @Test
    void list() throws Exception {
        List<MasterData> retrieved = null;

        MvcResult result = mockMvc.perform(get("/master")
                        .param("uid", "0")
                        .param("size","3")
                        .param("page","0"))
                .andExpect(status().isOk()).andReturn();

        retrieved = convert(result, new TypeReference<List<MasterData>>() {});
        assertEquals(1, retrieved.size());
        assertEquals(masterDataArr[0], retrieved.get(0));

        List<MasterData> masterDataList = Arrays.stream(masterDataArr).collect(Collectors.toList());
        List<MasterData> _masterDataList = masterDataList;

        result = mockMvc.perform(get("/master")
                    .param("size","3")
                    .param("page","0"))
                .andExpect(status().isOk()).andReturn();

        retrieved = convert(result, new TypeReference<List<MasterData>>() {});
        assertEquals(3, retrieved.size());
        retrieved.forEach(e -> assertTrue(_masterDataList.contains(e)));

        result = mockMvc.perform(get("/master")
                        .param("size","3")
                        .param("page","1"))
                .andExpect(status().isOk()).andReturn();

        retrieved = convert(result, new TypeReference<List<MasterData>>() {});
        assertEquals(3, retrieved.size());
        retrieved.forEach(e -> assertTrue(_masterDataList.contains(e)));

        result = mockMvc.perform(get("/master")
                        .param("size","3")
                        .param("page","2"))
                .andExpect(status().isOk()).andReturn();

        retrieved = convert(result, new TypeReference<List<MasterData>>() {});
        assertEquals(3, retrieved.size());
        retrieved.forEach(e -> assertTrue(_masterDataList.contains(e)));

        result = mockMvc.perform(get("/master")
                        .param("size","3")
                        .param("page","3"))
                .andExpect(status().isOk()).andReturn();

        retrieved = convert(result, new TypeReference<List<MasterData>>() {});
        assertEquals(1, retrieved.size());
        retrieved.forEach(e -> assertTrue(_masterDataList.contains(e)));
    }

    static <T> T convert(MvcResult result, TypeReference typeReference) throws Exception {
        return (T) new ObjectMapper().readValue(result.getResponse().getContentAsString(), typeReference);
    }

    @Test
    void count() throws Exception {
        MvcResult result = mockMvc.perform(get("/master/count")).andExpect(status().isOk()).andReturn();
        Long res = convert(result, new TypeReference<Long>() {});
        assertEquals(10, res);

        result = mockMvc.perform(get("/master/count")
                .param("uid", "0")).andExpect(status().isOk()).andReturn();
        res = convert(result, new TypeReference<Long>() {});
        assertEquals(1, res);
    }
}