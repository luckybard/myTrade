package com.myTrade.controllers;

import com.myTrade.dto.AdDto;
import com.myTrade.entities.AdEntity;
import com.myTrade.services.AdService;
import com.myTrade.utility.AdCategory;
import com.myTrade.utility.City;
import com.myTrade.utility.PriceRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/ad")
public class AdController {

    private final AdService adService;


    @Autowired
    public AdController(AdService adService) {
        this.adService = adService;
    }

    @GetMapping("/search")
    public Page<AdDto> search(@RequestParam String searchText, @RequestParam Optional<Boolean> searchInDescription, @RequestParam Optional<City> city,
                                 @RequestParam Optional<AdCategory> category, @RequestParam Optional<PriceRange> priceRange,
                                 @RequestParam Optional<Integer> pageNumber,@RequestParam Optional<Integer> pageSize ) {
        return adService.findAllActiveByAdSearchRequest(searchText,searchInDescription.orElse(false), city.orElse(City.EVERYWHERE), category.orElse(AdCategory.ALL),
                priceRange.orElse(new PriceRange(0, 2_147_483_647)), pageNumber.orElse(0), pageSize.orElse(10));
    }

//    @GetMapping("/searchUpgraded")
//    public Page<AdDto> searchUpgraded(@RequestParam String searchText, @RequestParam Optional<City> city,
//                                         @RequestParam Optional<AdCategory> category, @RequestParam Optional<PriceRange> priceRange,
//                                         @RequestParam Optional<PageRequest> pageRequest) {
//        return adService.findAllActiveByAdSearchRequestUpgraded(searchText, city.orElse(City.EVERYWHERE), category.orElse(AdCategory.ALL),
//                priceRange.orElse(new PriceRange(0, 2_147_483_647)), pageRequest.orElse(PageRequest.of(0, 25, Sort.by("refresh_time").descending())));
//    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ad:write')")
    public void create(@RequestBody AdDto adDto) {
        adService.saveAdDtoAndAddAdToUserAdList(adDto);
    }

    @GetMapping("/fetch-random")
    public Page<AdEntity> fetchRandom(@RequestParam Optional<Integer> pageSize ){ //TODO:[Q] Is it good practise to useOptional?
        return adService.fetchRandom(pageSize.orElse(10));
    }

    @GetMapping("/fetch/{id}")
    public AdDto fetchAd(@PathVariable(value = "id") Long adId) {
        return adService.fetchAdDtoById(adId);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ad:write')")
    public AdDto fetchAdForEdit(@PathVariable(value = "id") Long adId) {
        return adService.fetchAdForEdit(adId);
    }

    @PatchMapping("/patch")
    @PreAuthorize("hasAnyAuthority('ad:write')")
    public void patch(@RequestBody AdDto adDto) {
        adService.patchAdDto(adDto);
    }

    @PatchMapping("/refresh/{id}")
    @PreAuthorize("hasAnyAuthority('ad:read','ad:write')")
    public void refreshAd(@PathVariable(value = "id") Long adId) {
        adService.refreshAd(adId);
    }

    @PatchMapping("/highlight/{id}")
    @PreAuthorize("hasAnyAuthority('ad:read','ad:write')")
    public void highlightAd(@PathVariable(value = "id") Long adId) {
        adService.highlightAd(adId);
    }

    @PatchMapping("/active/{id}")
    @PreAuthorize("hasAnyAuthority('ad:read','ad:write')")
    public void changeActiveStatus(@PathVariable(value = "id") Long adId){
        adService.changeActiveStatus(adId);
    }

}
