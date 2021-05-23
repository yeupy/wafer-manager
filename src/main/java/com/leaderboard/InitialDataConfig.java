package com.leaderboard;

import com.leaderboard.entity.Player;
import com.leaderboard.service.PlayerService;
import com.leaderboard.service.StatisticService;
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

@Configuration
@Slf4j
@Profile("!test")
@RequiredArgsConstructor
public class InitialDataConfig implements CommandLineRunner {

    private final PlayerService playerService;
    private final StatisticService statisticService;

    @Override
    public void run(String... args) throws Exception {
        Path path = Paths.get(new ClassPathResource("initialData.txt").getURI());
        BufferedReader reader = Files.newBufferedReader(path);
        String line = reader.readLine();
        while(line != null) {
            log.info(line);
            String[] str = line.split(",");
            Player _player = new Player(Long.valueOf(str[0]), Integer.valueOf(str[1]));
            Player player = playerService.read(_player.getId());
            if(player == null) {
                playerService.create(_player);
            } else {
                player.setMmr(player.getMmr() + _player.getMmr());
                playerService.update(player);
            }
            line = reader.readLine();
        }
        log.info("Total players: " + statisticService.getTotal());
    }
}
