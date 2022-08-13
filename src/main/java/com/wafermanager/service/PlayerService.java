package com.wafermanager.service;

import com.wafermanager.entity.Player;
import com.wafermanager.entity.Tier;
import com.wafermanager.mapper.PlayerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerMapper players;
    private final StatisticService statisticService;

    @Transactional
    public int create(Player player) {
        int create = players.create(player);
        statisticService.increaseTotal(create);
        return create;
    }

    public Player read(long id) {
        return players.read(id);
    }

    public Player readFull(long id) {
        Player player = players.read(id);
        player.setRank(getRank(id));
        player.setTier(getTier(id));
        return player;
    }

    @Transactional
    public int update(Player player) {
        return players.update(player);
    }

    @Transactional
    public int delete(long id) {
        int delete = players.delete(id);
        statisticService.decreaseTotal(delete);
        return delete;
    }

    public Integer getRank(long id) {
        return players.getRank(read(id), statisticService.getTotal());
    }

    public Tier getTier(long id) {
        int total = statisticService.getTotal();
        int rank = players.getRank(read(id), total);

        if (total > 100 && rank <= 100) {
            return Tier.CHALLENGER;
        } else if (rank <= total * 0.01) {
            return Tier.MASTER;
        } else if (rank <= total * 0.05) {
            return Tier.DIAMOND;
        } else if (rank <= total * 0.1) {
            return Tier.PLATINUM;
        } else if (rank <= total * 0.25) {
            return Tier.GOLD;
        } else if (rank <= total * 0.65) {
            return Tier.SILVER;
        }

        return Tier.BRONZE;
    }

    public List<Player> listTop10() {
        List<Player> top = this.players.listTop(10, statisticService.getTotal());
        top.stream().forEach(e -> {
            e.setRank(getRank(e.getId()));
            e.setTier(getTier(e.getId()));
        });
        return top;
    }

    public List<Player> listNear(long id) {
        final int total = statisticService.getTotal();
        Player player = read(id);
        List<Player> above = players.listAbove(player, 5, total);
        List<Player> below = players.listBelow(player, 5, total);
        above.add(player);
        above.addAll(below);
        above.stream().forEach(e -> {
            e.setRank(getRank(e.getId()));
            e.setTier(getTier(e.getId()));
        });
        return above;
    }
}
