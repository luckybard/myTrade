package com.myTrade.controllers;

import com.myTrade.dto.AdDto;
import com.myTrade.dto.AdEditDto;
import com.myTrade.dto.AdOwnerDto;
import com.myTrade.services.AdService;
import com.myTrade.utility.pojo.AdCategory;
import com.myTrade.utility.pojo.City;
import com.myTrade.utility.pojo.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/ad")
public final class AdController {
    private final AdService adService;

    @Autowired
    public AdController(AdService adService) {
        this.adService = adService;
    }

    @PostMapping("/save")
    public ResponseEntity saveAdByAdEditDtoWithInitialValuesAndAssignToUserAdList(@RequestBody AdEditDto adEditDto) {
        adService.saveAdByAdEditDtoWithInitialValuesAndAssignToUserAdList(adEditDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/fetch")
    public ResponseEntity<Page<AdDto>> fetchActiveAdDtoPageBySearchRequest(@RequestParam String searchText,
                                                                           @RequestParam Boolean isSearchedInDescription,
                                                                           @RequestParam City city,
                                                                           @RequestParam AdCategory adCategory,
                                                                           @RequestParam Integer priceFrom,
                                                                           @RequestParam Integer priceTo,
                                                                           @RequestParam Integer pageNumber,
                                                                           @RequestParam Integer pageSize) {
        Page<AdDto> adDtoPage = adService.fetchActiveAdDtoPageBySearchRequest(new SearchRequest(
                searchText, adCategory, isSearchedInDescription, city, priceFrom, priceTo, pageNumber, pageSize));
        return ResponseEntity.ok(adDtoPage);
    }

    @GetMapping("/fetch/random")
    public ResponseEntity<Page<AdDto>> fetchRandomAdDtoPage(@RequestParam Integer pageSize) {
        Page<AdDto> adDtoPage = adService.fetchRandomAdDtoPage(pageSize);
        return ResponseEntity.ok(adDtoPage);
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<AdDto> fetchAdDtoByIdAndSetIsUserFavourite(@PathVariable(value = "id") Long adId) {
        AdDto adDto = adService.fetchAdDtoByIdAndSetIsUserFavourite(adId);
        return ResponseEntity.ok(adDto);
    }

    @GetMapping("/fetch/edit/{id}")
    public ResponseEntity<AdEditDto> fetchAdEditDto(@PathVariable(value = "id") Long adId) {
        AdEditDto adEditDto = adService.fetchAdEditDto(adId);
        return ResponseEntity.ok(adEditDto);
    }

    @GetMapping("/fetch/adList")
    public ResponseEntity<Page<AdOwnerDto>> fetchUserAdOwnerDtoPageAndSetIsUserAbleToHighlightAndRefresh(@RequestParam Integer pageNumber,
                                                                                                         @RequestParam Integer pageSize) {
        Page<AdOwnerDto> adOwnerDtoPage = adService.fetchAdOwnerDtoPageAndSetIsUserAbleToHighlightAndRefresh(pageNumber, pageSize);
        return ResponseEntity.ok(adOwnerDtoPage);
    }

    @GetMapping("/fetch/adList/{username}")
    public ResponseEntity<Page<AdDto>> fetchAdDtoPageByOwnerUsernameAndSetUpIsUserFavourite(@PathVariable(value = "username") String username,
                                                                                            @RequestParam Integer pageNumber,
                                                                                            @RequestParam Integer pageSize) {
        Page<AdDto> adDtoPage = adService.fetchAdDtoPageByOwnerUsernameAndSetUpIsUserFavourite(username, pageNumber, pageSize);
        return ResponseEntity.ok(adDtoPage);
    }

    @GetMapping("/fetch/favourite/adList")
    public ResponseEntity<Page<AdDto>> fetchUserFavouriteAdDtoPage(@RequestParam Integer pageNumber,
                                                                   @RequestParam Integer pageSize) {
        Page<AdDto> adDtoPage = adService.fetchUserFavouriteAdDtoPage(pageNumber, pageSize);
        return ResponseEntity.ok(adDtoPage);
    }

    @PatchMapping("/patch")
    public ResponseEntity patchAdEntityByAdEditDto(@RequestBody AdEditDto adEditDto) {
        adService.patchAdEntityByAdEditDto(adEditDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/patch/refresh/{id}")
    public ResponseEntity refreshAdById(@PathVariable(value = "id") Long adId) {
        adService.refreshAdById(adId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/patch/highlight/{id}")
    public ResponseEntity highlightAdByIdAndDeductHighlightPointFromUser(@PathVariable(value = "id") Long adId) {
        adService.highlightAdByIdAndDeductHighlightPointFromUser(adId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/patch/active/{id}")
    public ResponseEntity changeAdStatusById(@PathVariable(value = "id") Long adId) {
        adService.changeAdStatusById(adId);
        return ResponseEntity.ok().build();
    }
}
