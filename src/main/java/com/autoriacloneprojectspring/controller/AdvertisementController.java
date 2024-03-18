package com.autoriacloneprojectspring.controller;

import com.autoriacloneprojectspring.dto.AdvertisementRequestDto;
import com.autoriacloneprojectspring.entity.Advertisement;
import com.autoriacloneprojectspring.services.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/advertisement")
@RequiredArgsConstructor
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<String> createAdvertisement(@RequestBody AdvertisementRequestDto advertisementRequestDto) {
        advertisementService.createAdvertisement(advertisementRequestDto);
        return ResponseEntity.ok("Advertisement was successfully created");
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Advertisement> getAllAdvertisements() {
        return advertisementService.findAllAdvertisements();

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANEGER','ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAdvertisement(@PathVariable long id) {
        advertisementService.deleteByAdvertisementId(id);
    }
}
