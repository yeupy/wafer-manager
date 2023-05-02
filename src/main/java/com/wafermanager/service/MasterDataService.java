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
        for (int i = 0; i < size; i++) {
            sb.append(chars.charAt(Double.valueOf(Math.floor(Math.random() * length)).intValue()));
        }
        return sb.toString();
    }

    public void sample(int size) {
        masters.deleteAll();
        HashSet<String> uids = new HashSet<>();
        LocalDateTime modifiedDate = LocalDateTime.now(ZoneId.of("UTC"));

        for (int i = 0; i < size; i++) {
            String uid = randomUid(6);
            while (uids.contains(uid)) {
                uid = randomUid(6);
            }
            uids.add(uid);

            int a = Double.valueOf(Math.floor(Math.random() * ((i * 0.8) - i)) + i).intValue();
            int b = Double.valueOf(Math.floor(Math.random() * ((i * 0.5) - i)) + i).intValue();
            int c = Double.valueOf(Math.floor(Math.random() * 500)).intValue();
            String d = randomUid(2);

            MasterData _masterData = new MasterData(uid, a, b, c, d, modifiedDate.minusDays(i % 60));
            masters.create(_masterData);
            log.info(_masterData.toString());
        }
    }
}
