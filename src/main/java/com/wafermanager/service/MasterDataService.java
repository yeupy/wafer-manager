package com.wafermanager.service;

import com.wafermanager.entity.MasterData;
import com.wafermanager.mapper.MasterDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<MasterData> list(int size, int page) {
        return masters.list(size, page);
    }
}
