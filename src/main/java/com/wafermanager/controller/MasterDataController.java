package com.wafermanager.controller;

import com.wafermanager.entity.MasterData;
import com.wafermanager.service.MasterDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master")
public class MasterDataController {

    private final MasterDataService masterDataService;

    private URI location(MasterData masterData) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + masterData.getUid())
                .buildAndExpand()
                .toUri();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MasterData masterData) {
        masterDataService.create(masterData);
        MasterData created = masterDataService.read(masterData.getUid());
        return ResponseEntity.created(location(created)).build();
    }

    @GetMapping("/{uid}")
    public ResponseEntity<MasterData> read(@PathVariable String uid) {
        return ResponseEntity.ok(masterDataService.read(uid));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody MasterData masterData) {
        masterDataService.update(masterData);
        return ResponseEntity.status(HttpStatus.ACCEPTED).location(location(masterData)).build();
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<?> delete(@PathVariable String uid) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<MasterData>> list(@RequestParam(value = "uid", required = false) String uid, @RequestParam("size") int size, @RequestParam("page") int page) {
        return ResponseEntity.ok(masterDataService.list(uid, size, page));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(@RequestParam(value = "uid", required = false) String uid) {
        return ResponseEntity.ok(masterDataService.count(uid));
    }

    @GetMapping("/sample")
    public ResponseEntity<?> sample() throws IOException {
        masterDataService.sample();
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
