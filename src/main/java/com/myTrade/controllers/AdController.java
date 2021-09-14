package com.myTrade.controllers;

import com.myTrade.dto.AdDto;
import com.myTrade.services.AdService;
import com.myTrade.services.UserService;
import com.myTrade.utility.searchEngine.AdSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/ad")
public class AdController {

    //TODO: Maybe I should rethink again endpoints: - Only one endPoint for editing ad (passing whole new AdDto object)
    //                                              - No information about adId in url?(all information should be passed in RequestBody)
    //                                              - Should be created verification if user is ad owner? If i'd like to connect it with the fronted,
    //                                              where verification should be? here in backend or on fronted side?
    //                                              - Is it possible to pass exception to frontend?

    //TODO: Searching ads by name (main search engine) best implementation is just passing name to stream filter?

    private final AdService adService;
    private final UserService userService;

    @Autowired
    public AdController(AdService adService, UserService userService) {
        this.adService = adService;
        this.userService = userService;
    }

    @GetMapping("/search")
    public List<AdDto> search(@RequestBody AdSearchRequest adSearchRequest)  {
        return adService.fetchByAllAdSearchRequest(adSearchRequest);
    }

    @GetMapping("/search/{id}")
    @PreAuthorize("hasAnyAuthority('ad:read','ad:write')")
    public AdDto fetchAd(@PathVariable(value = "id")Long adId) {
        return adService.fetchAdDtoById(adId);
    }

    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ad:read','ad:write')")
    public void create(@RequestBody AdDto adDto){
        adService.saveAdDtoWithCreatedAndModifiedDateTime(adDto);
    }

    @PatchMapping("/{id}/edit")
    @PreAuthorize("hasAnyAuthority('ad:read','ad:write')")
    public void patch(@RequestBody AdDto adDto)  {
        adService.patchAdDto(adDto);
    }

    @PatchMapping("/{id}/refresh")
    @PreAuthorize("hasAnyAuthority('ad:read','ad:write')")
    public void refreshAd(@PathVariable(value = "id")Long adId) {
        adService.refreshAd(adId);
    }

    @PatchMapping("/{id}/highlight")
    @PreAuthorize("hasAnyAuthority('ad:read','ad:write')")
    public void highlightAd(@RequestBody String userName,@PathVariable(value = "id")Long adId) {
        adService.highlightAd(adId);
        userService.deductHighlightPoint(userName);    //TODO:Is it possible to make transaction like in hibernate, to have confidence that whole code has been completed, try-catch?
    }



/*                                     !-Just for learning purpose -!
    @PatchMapping("/{id}/editTitle")
    @PreAuthorize("hasAnyAuthority('ad:read','ad:write')")
    public void changeTitle(@RequestBody String newTitle,@PathVariable(value = "id")Long adId) {
        adService.changeTitle(newTitle,adId);
    }

    @PatchMapping("/{id}/editCategory")
    @PreAuthorize("hasAnyAuthority('ad:read','ad:write')")
    public void changeCategory(@RequestBody AdCategory adCategory, @PathVariable(value = "id")Long adId)  {
        adService.changeAdCategory(adCategory,adId);
    }
    @PatchMapping("/{id}/editImagePath")
    @PreAuthorize("hasAnyAuthority('ad:read','ad:write')")
    public void changeImagePath(@RequestBody String newImagePath, @PathVariable(value = "id")Long adId) {
        adService.changeImagePath(newImagePath,adId);
    }
    @PatchMapping("/{id}/editDescription")
    @PreAuthorize("hasAnyAuthority('ad:read','ad:write')")
    public void changeDescription(@RequestBody String newDescription,@PathVariable(value = "id") Long adId) {
        adService.changeDescription(newDescription,adId);
    }
    @PatchMapping("/{id}/editPrice")
    @PreAuthorize("hasAnyAuthority('ad:read','ad:write')")
    public void changePrice(@RequestBody Double newPrice, @PathVariable(value = "id")Long adId){
        adService.changePrice(newPrice,adId);
    }
    @PatchMapping("/{id}/editCity")
    @PreAuthorize("hasAnyAuthority('ad:read','ad:write')")
    public void changeCity(@RequestBody String newCity,@PathVariable(value = "id")Long adId){
        adService.changeCity(newCity,adId);
    }

    @PatchMapping("/{id}/editStatus")
    @PreAuthorize("hasAnyAuthority('ad:read','ad:write')")
    public void changeActiveStatus(@RequestBody Boolean isActive,@PathVariable(value = "id")Long adId)  {
        adService.changeActiveStatus(isActive,adId);
    }                                                                                                                 */
}
