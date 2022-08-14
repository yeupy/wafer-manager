package com.wafermanager.mapper;

import com.wafermanager.entity.MasterData;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MasterDataMapper {

    int create(MasterData masterData);

    MasterData read(String uid);

    int update(MasterData masterData);

    @Delete("DELETE FROM master")
    int deleteAll();

    List<MasterData> list(int size, int page);

}
