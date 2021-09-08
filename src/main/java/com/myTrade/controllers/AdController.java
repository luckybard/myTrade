package com.myTrade.controllers;

import com.myTrade.dto.AdDto;
import com.myTrade.repositories.AdRepository;
import com.myTrade.services.AdService;
import com.myTrade.utility.AdCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/ad")
public class AdController {

    private final AdService adService;
    private final AdRepository adRepository;

    @Autowired
    public AdController(AdService adService, AdRepository adRepository) {
        this.adService = adService;
        this.adRepository = adRepository;
    }

    @GetMapping("/search/{id}")
    public AdDto fetchAd(@PathVariable(value = "id")Long adId) {
        return adService.fetchAdDtoById(adId);
    }

    @PostMapping("/save")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER, ADMIN')")
    public void saveAd(@RequestBody AdDto adDto){
        adService.saveAdDtoWithCreatedAndModifiedDateTime(adDto);
    }

    @PatchMapping("/{id}/editTitle")
    @PreAuthorize("hasAnyRole('USER, ADMIN')")
    public void changeTitle(@RequestBody String newTitle,@PathVariable(value = "id")Long adId) {
        adService.changeTitle(newTitle,adId);
    }
    @PatchMapping("/{id}/editCategory")
    @PreAuthorize("hasAnyRole('USER, ADMIN')")
    public void changeCategory(@RequestBody AdCategory adCategory, @PathVariable(value = "id")Long adId)  {
        adService.changeAdCategory(adCategory,adId);
    }
    @PatchMapping("/{id}/editImagePath")
    @PreAuthorize("hasAnyRole('USER, ADMIN')")
    public void changeImagePath(@RequestBody String newImagePath, @PathVariable(value = "id")Long adId) {
        adService.changeImagePath(newImagePath,adId);
    }
    @PatchMapping("/{id}/editDescription")
    @PreAuthorize("hasAnyRole('USER, ADMIN')")
    public void changeDescription(@RequestBody String newDescription,@PathVariable(value = "id") Long adId) {
        adService.changeDescription(newDescription,adId);
    }
    @PatchMapping("/{id}/editPrice")
    @PreAuthorize("hasAnyRole('USER, ADMIN')")
    public void changePrice(@RequestBody Double newPrice, @PathVariable(value = "id")Long adId){
        adService.changePrice(newPrice,adId);
    }
    @PatchMapping("/{id}/editCity")
    @PreAuthorize("hasAnyRole('USER, ADMIN')")
    public void changeCity(@RequestBody String newCity,@PathVariable(value = "id")Long adId){
        adService.changeCity(newCity,adId);
    }
    @PatchMapping("/{id}/editStatus")
    @PreAuthorize("hasAnyRole('USER, ADMIN')")
    public void changeActiveStatus(@RequestBody Boolean isActive,@PathVariable(value = "id")Long adId)  {
        adService.changeActiveStatus(isActive,adId);
    }
}
