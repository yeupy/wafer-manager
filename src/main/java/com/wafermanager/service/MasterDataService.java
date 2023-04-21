package com.wafermanager.service;

import com.wafermanager.entity.MasterData;
import com.wafermanager.mapper.MasterDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
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
        masterData.setModifiedDate(LocalDateTime.now(ZoneId.of("UTC")));
        return masters.update(masterData);
    }

    @Transactional
    public int delete(String uid) {
        return masters.delete(uid);
    }

    @Transactional
    public int deleteList(List<String> uids) {
        return masters.deleteList(uids);
    }

    public List<MasterData> list(String uid, int size, int offset) {
        return masters.list(uid, size, offset);
    }

    public long count(String uid) {
        return masters.count(uid);
    }

    private String randomUid(int size) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int length = chars.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0, l = size; i < l; i++) {
            sb.append(chars.charAt(Double.valueOf(Math.floor(Math.random() * length)).intValue()));
        }
        return sb.toString();
    }

    public void sample(int size) throws IOException {
        masters.deleteAll();
        HashSet<String> uids = new HashSet<>();

        /*int result = 0;
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
        }*/
        LocalDateTime modifiedDate = LocalDateTime.now(ZoneId.of("UTC"));

        for (int i = 0, l = size; i < l; i++) {
            String uid = randomUid(6);
            uids.add(uid);
            while (!uids.contains(uid)) {
                uid = randomUid(6);
            }

            int a = Double.valueOf(Math.floor(Math.random() * ((i * 0.8) - i)) + i).intValue();
            int b = Double.valueOf(Math.floor(Math.random() * ((i * 0.5) - i)) + i).intValue();
            int c = Double.valueOf(Math.floor(Math.random() * 500)).intValue();
            String d = randomUid(2);
//            modifiedDate = LocalDateTime.now(ZoneId.of("UTC"));

            MasterData _masterData = new MasterData(uid, a, b, c, d, modifiedDate.minusDays(i % 60));
            masters.create(_masterData);
            log.info(_masterData.toString());
        }
    }
}
