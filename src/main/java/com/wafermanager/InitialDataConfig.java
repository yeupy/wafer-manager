package com.wafermanager;

import com.wafermanager.entity.MasterData;
import com.wafermanager.service.MasterDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
@Slf4j
@Profile("init")
@RequiredArgsConstructor
public class InitialDataConfig implements CommandLineRunner {

    private final MasterDataService masterDataService;

    @Override
    public void run(String... args) throws Exception {
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

            MasterData masterData = masterDataService.read(str[0]);
            if(masterData == null) {
                masterDataService.create(_masterData);
            } else {
                masterDataService.update(_masterData);
            }
            line = reader.readLine();
        }
    }
}
