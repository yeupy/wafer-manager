package com.leaderboard.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import com.leaderboard.entity.Player;
import com.leaderboard.service.PlayerService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/{id}")
    public ResponseEntity<Player> read(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.readFull(id));
    }

    @GetMapping("/top")
    public ResponseEntity<?> top() {
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("topPlayers", playerService.listTop10());
        return ResponseEntity.ok(res);
    }

    @GetMapping("/nearby/{id}")
    public ResponseEntity<?> near(@PathVariable Long id) {
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("playersNearby", playerService.listNear(id));
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        int res = playerService.delete(id);
        return ResponseEntity.ok(res == 1 ? "DELETED" : "NOT FOUND");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Player player) {
        player.setId(id);
        int res = playerService.update(player);
        return ResponseEntity.ok(res == 1 ? "UPDATED" : "NOT FOUND");
    }

    @PostMapping("/*")
    public ResponseEntity<?> create(@RequestBody Player player) {
        playerService.create(player);
        return ResponseEntity.ok("CREATE");
    }
}
