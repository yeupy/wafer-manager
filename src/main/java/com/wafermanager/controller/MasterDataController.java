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
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master")
public class MasterDataController {

    private final MasterDataService masterDataService;

    private URI location(MasterData masterData) {
        String uid = masterData != null ? masterData.getUid() : "";
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/master/" + uid)
                .buildAndExpand()
                .toUri();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MasterData masterData) {
        masterDataService.create(masterData);
        return ResponseEntity.created(location(masterData)).build();
//        return ResponseEntity.ok("CREATED");
    }

    @GetMapping("/{uid}")
    public ResponseEntity<MasterData> read(@PathVariable String uid) {
        MasterData masterData = masterDataService.read(uid);
        if (masterData != null) {
            return ResponseEntity.ok(masterData);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody MasterData masterData) {
        if (masterDataService.update(masterData) == 1) {
            return ResponseEntity.accepted().location(location(masterData)).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestHeader(value = "Uid") String uid) {
        if(uid.contains(",")) {
            String[] uids = uid.split(",");
            masterDataService.deleteList(Arrays.asList(uids));
            return ResponseEntity.noContent().build();
        } else {
            if (masterDataService.delete(uid) == 1) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<MasterData>> list(@RequestParam(value = "uid", required = false) String uid, @RequestParam(value = "size", required = false, defaultValue = "5") int size, @RequestParam(value = "offset", required = false, defaultValue = "0") int offset) {
        return ResponseEntity.ok(masterDataService.list(uid, size, offset));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(@RequestParam(value = "uid", required = false) String uid) {
        return ResponseEntity.ok(masterDataService.count(uid));
    }

    @GetMapping("/sample")
    public ResponseEntity<?> sample(@RequestParam("size") int size) {
        masterDataService.sample(size);
        return ResponseEntity.created(location(null)).build();
    }

    @GetMapping("/badRequest")
    public ResponseEntity<?> badRequest() {
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/serverError")
    public ResponseEntity<?> serverError() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
