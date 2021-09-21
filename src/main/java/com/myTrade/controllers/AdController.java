package com.myTrade.controllers;

import com.myTrade.dto.AdDto;
import com.myTrade.entities.AdEntity;
import com.myTrade.services.AdService;
import com.myTrade.services.UserService;
import com.myTrade.utility.AdCategory;
import com.myTrade.utility.City;
import com.myTrade.utility.PriceRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/ad")
public class AdController {

    private final AdService adService;
    private final UserService userService;

    @Autowired
    public AdController(AdService adService, UserService userService) {
        this.adService = adService;
        this.userService = userService;
    }

    @GetMapping("/search")
    public Page<AdEntity> search(@RequestParam String searchText, @RequestParam Optional<City> city,
                                 @RequestParam Optional<AdCategory> category, @RequestParam Optional<PriceRange> priceRange,
                                 @RequestParam Optional<PageRequest> pageRequest) {
        return adService.findAllActiveByAdSearchRequest(searchText, city.orElse(City.EVERYWHERE), category.orElse(AdCategory.ALL),
                priceRange.orElse(new PriceRange(0, 2_147_483_647)), pageRequest.orElse(PageRequest.of(0, 25, Sort.by("refresh_time").descending())));
    }

    @GetMapping("/searchUpgraded")
    public Page<AdEntity> searchUpgraded(@RequestParam String searchText, @RequestParam Optional<City> city,
                                         @RequestParam Optional<AdCategory> category, @RequestParam Optional<PriceRange> priceRange,
                                         @RequestParam Optional<PageRequest> pageRequest) {
        return adService.findAllActiveByAdSearchRequestUpgraded(searchText, city.orElse(City.EVERYWHERE), category.orElse(AdCategory.ALL),
                priceRange.orElse(new PriceRange(0, 2_147_483_647)), pageRequest.orElse(PageRequest.of(0, 25, Sort.by("refresh_time").descending())));
    }

    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ad:write')")
    public void create(@RequestBody AdDto adDto) {
        adService.saveAdDtoWithProperValuesOfCreatedModifiedRefreshHighlightDateTime(adDto);
    }

    @GetMapping("/search/{id}")
    @PreAuthorize("hasAnyAuthority('ad:read')")
    public AdDto fetchAd(@PathVariable(value = "id") Long adId) {
        return adService.fetchAdDtoById(adId);
    }

    @PatchMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ad:write')")
    public void patch(@RequestBody AdDto adDto) {
        adService.patchAdDto(adDto);
    }

    @PatchMapping("/{id}/refresh")
    @PreAuthorize("hasAnyAuthority('ad:read','ad:write')")
    public void refreshAd(@PathVariable(value = "id") Long adId) {
        adService.refreshAd(adId);
    }

    @PatchMapping("/{id}/highlight")
    @PreAuthorize("hasAnyAuthority('ad:read','ad:write')")
    public void highlightAd(@RequestBody String userName, @PathVariable(value = "id") Long adId) {
        adService.highlightAd(adId);
        userService.deductHighlightPoint(userName);    //TODO:Is it possible to make transaction like in hibernate, to have confidence that whole code has been completed, try-catch?
    }

}