package com.wafermanager.controller;

import com.wafermanager.entity.MasterData;
import com.wafermanager.service.MasterDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master")
public class MasterDataController {

    private final MasterDataService masterDataService;

    @GetMapping("/{uid}")
    public ResponseEntity<MasterData> read(@PathVariable String uid) {
        return ResponseEntity.ok(masterDataService.read(uid));
    }

    @GetMapping
    public ResponseEntity<List<MasterData>> list(@RequestParam("size") int size, @RequestParam("page") int page) {
        return ResponseEntity.ok(masterDataService.list(size, page));
    }
}
