package com.wafermanager.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Data
@Alias("master")
public class MasterData {

    private String uid;
    private int columnA;
    private int columnB;
    private int columnC;
    private String columnD;
    private LocalDateTime modifiedDate;

    public MasterData(String uid, int columnA, int columnB, int columnC, String columnD, LocalDateTime modifiedDate) {
        this.uid = uid;
        this.columnA = columnA;
        this.columnB = columnB;
        this.columnC = columnC;
        this.columnD = columnD;
        this.modifiedDate = modifiedDate;
    }

    @JsonCreator
    public MasterData(@JsonProperty("uid") String uid, @JsonProperty("columnA") int columnA, @JsonProperty("columnB") int columnB, @JsonProperty("columnC") int columnC, @JsonProperty("columnD") String columnD, @JsonProperty("modifiedDate") String modifiedDate) {
        this.uid = uid;
        this.columnA = columnA;
        this.columnB = columnB;
        this.columnC = columnC;
        this.columnD = columnD;
        this.modifiedDate = modifiedDate == null ? LocalDateTime.now(ZoneId.of("UTC")) : LocalDateTime.parse(modifiedDate.replaceFirst(" ", "T"), DateTimeFormatter.ISO_DATE_TIME);
    }
}
