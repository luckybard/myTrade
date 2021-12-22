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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/ad")
public class AdController {
    private final AdService adService;

    @Autowired
    public AdController(AdService adService) {
        this.adService = adService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ad:write')")
    public ResponseEntity saveAdByAdEditDtoWithInitialValuesAndAssignToUserAdList(@RequestBody AdEditDto adEditDto) {
        adService.saveAdByAdEditDtoWithInitialValuesAndAssignToUserAdList(adEditDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
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

    @GetMapping("/random")
    public ResponseEntity<Page<AdDto>> fetchRandomAdDtoPage(@RequestParam Integer pageSize) {
        Page<AdDto> adDtoPage = adService.fetchRandomAdDtoPage(pageSize);
        return ResponseEntity.ok(adDtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdDto> fetchAdDtoByIdAndSetIsUserFavourite(@PathVariable(value = "id") Long adId) {
        AdDto adDto = adService.fetchAdDtoByIdAndSetIsUserFavourite(adId);
        return ResponseEntity.ok(adDto);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('ad:read')")
    public ResponseEntity<AdEditDto> fetchAdEditDto(@PathVariable(value = "id") Long adId) {
        AdEditDto adEditDto = adService.fetchAdEditDto(adId);
        return ResponseEntity.ok(adEditDto);
    }

    @GetMapping("/adList")
    @PreAuthorize("hasAuthority('ad:read')")
    public ResponseEntity<Page<AdOwnerDto>> fetchUserAdOwnerDtoPageAndSetIsUserAbleToHighlightAndRefresh(@RequestParam Integer pageNumber,
                                                                                                         @RequestParam Integer pageSize) {
        Page<AdOwnerDto> adOwnerDtoPage = adService.fetchAdOwnerDtoPageAndSetIsUserAbleToHighlightAndRefresh(pageNumber, pageSize);
        return ResponseEntity.ok(adOwnerDtoPage);
    }

    @GetMapping("/adList/{username}")
    public ResponseEntity<Page<AdDto>> fetchAdDtoPageByOwnerUsernameAndSetUpIsUserFavourite(@PathVariable(value = "username") String username,
                                                                                            @RequestParam Integer pageNumber,
                                                                                            @RequestParam Integer pageSize) {
        Page<AdDto> adDtoPage = adService.fetchAdDtoPageByOwnerUsernameAndSetUpIsUserFavourite(username, pageNumber, pageSize);
        return ResponseEntity.ok(adDtoPage);
    }

    @GetMapping("/favourite/adList")
    @PreAuthorize("hasAuthority('ad:read')")
    public ResponseEntity<Page<AdDto>> fetchUserFavouriteAdDtoPage(@RequestParam Integer pageNumber,
                                                                   @RequestParam Integer pageSize) {
        Page<AdDto> adDtoPage = adService.fetchUserFavouriteAdDtoPage(pageNumber, pageSize);
        return ResponseEntity.ok(adDtoPage);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ad:write')")
    public ResponseEntity patchAdEntityByAdEditDto(@RequestBody AdEditDto adEditDto) {
        adService.patchAdEntityByAdEditDto(adEditDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/refresh/{id}")
    @PreAuthorize("hasAuthority('ad:write')")
    public ResponseEntity refreshAdById(@PathVariable(value = "id") Long adId) {
        adService.refreshAdById(adId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/highlight/{id}")
    @PreAuthorize("hasAuthority('ad:write')")
    public ResponseEntity highlightAdByIdAndDeductHighlightPointFromUser(@PathVariable(value = "id") Long adId) {
        adService.highlightAdByIdAndDeductHighlightPointFromUser(adId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/active/{id}")
    @PreAuthorize("hasAuthority('ad:write')")
    public ResponseEntity changeAdStatusById(@PathVariable(value = "id") Long adId) {
        adService.changeAdStatusById(adId);
        return ResponseEntity.ok().build();
    }
}
