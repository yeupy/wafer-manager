package com.wafermanager.service;

import com.wafermanager.entity.MasterData;
import com.wafermanager.mapper.MasterDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MasterDataService {

    private final MasterDataMapper masters;

    @Transactional
    public int create(MasterData masterData) {
        return masters.create(masterData);
    }

    public MasterData read(String uid) {
        return masters.read(uid);
    }

    @Transactional
    public int update(MasterData masterData) {
        return masters.update(masterData);
    }

    public List<MasterData> list(String uid, int size, int page) {
        return masters.list(uid, size, page);
    }

    public long count(String uid) {
        return masters.count(uid);
    }

    public void sample() throws IOException {
        masters.deleteAll();
        int result = 0;
        Path path = Paths.get("./initMasterData.csv");
        if(!Files.exists(path)) {
            path = Paths.get(new ClassPathResource("initMasterData.csv").getURI());
        }
        BufferedReader reader = Files.newBufferedReader(path);
        reader.readLine();
        String line = reader.readLine();
        while (line != null) {
            log.info(line);
            String[] str = Arrays.stream(line.split(";")).map(e -> e.replaceAll("\"","")).toArray(String[]::new);

            MasterData _masterData = new MasterData(str[0], Integer.parseInt(str[1]), Integer.parseInt(str[2])
                    , Integer.parseInt(str[3]), str[4], LocalDateTime.parse(str[5]+"T00:00:00.000"));

            result += masters.create(_masterData);
            line = reader.readLine();
        }
    }
}
