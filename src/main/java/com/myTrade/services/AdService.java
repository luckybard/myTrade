package com.myTrade.services;

import com.myTrade.dto.AdDto;
import com.myTrade.entities.AdEntity;
import com.myTrade.entities.UserEntity;
import com.myTrade.jwt.JwtConfiguration;
import com.myTrade.mappersImpl.AdMapperImpl;
import com.myTrade.repositories.AdRepository;
import com.myTrade.repositories.UserRepository;
import com.myTrade.utility.AdCategory;
import com.myTrade.utility.City;
import com.myTrade.utility.PriceRange;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdService {

    private AdRepository adRepository;
    private UserRepository userRepository;
    private JwtConfiguration jwtConfiguration;
    private final AdMapperImpl adMapper = new AdMapperImpl();

    @Autowired
    public AdService(AdRepository adRepository, UserRepository userRepository, JwtConfiguration jwtConfiguration) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.jwtConfiguration = jwtConfiguration;
    }

    public AdDto fetchAdDtoById(Long adId) {
        return adMapper.adEntityToAdDto(adRepository.getById(adId));
    }

    public void patchAdDto(AdDto adDto, String authToken) {
        //TODO: Method to be extracted, verification each sensitive request!
        System.out.println(authToken);
        String token = authToken.replace(jwtConfiguration.getTokenPrefix(), "");
        System.out.println(token);
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(jwtConfiguration.getSecretKey())
                    .build()
                    .parseClaimsJws(token);

            Claims claimsJwsBody = claimsJws.getBody();
            String username = claimsJwsBody.getSubject();

            System.out.println(username);
            System.out.println(adDto.getOwnerUsername().contentEquals(username));
            if(adDto.getOwnerUsername().contentEquals(username)) {
                AdEntity adEntity = adMapper.adDtoAdEntity(adDto);
                setModifiedDate(adEntity);
                adRepository.save(adEntity);
            }
        } catch (JwtException e) {
            throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
        }
    }

    public void saveAdDtoAndAddAdToUserAdList(AdDto adDto) {
        Long adEntityId = saveAdDtoWithProperValuesOfCreatedModifiedRefreshHighlightDateTime(adDto);
        UserEntity adOwner = userRepository.findByUsername(adDto.getOwnerUsername());//TODO: differance between .get() and adding Optional<UserEntity>
        List<AdEntity> adEntityList = adOwner.getAdEntityList().stream().collect(Collectors.toList());
        adEntityList.add(adRepository.getById(adEntityId));
        adOwner.setAdEntityList(adEntityList);
        userRepository.save(adOwner);
    }

    public Long saveAdDtoWithProperValuesOfCreatedModifiedRefreshHighlightDateTime(AdDto adDto) {
        AdEntity adEntity = adMapper.adDtoAdEntity(adDto);
        setCreatedDate(adEntity);
        setModifiedDate(adEntity);
        setRefreshDate(adEntity);
        setHighlightExpirationTime(adEntity);
        return adRepository.save(adEntity).getId();
    }

    private void setModifiedDate(AdEntity adEntity) {
        adEntity.setModifiedDateTime(LocalDateTime.now());
    }

    private void setCreatedDate(AdEntity adEntity) {
        adEntity.setCreatedDateTime(LocalDateTime.now());
    }

    private void setRefreshDate(AdEntity adEntity) {
        adEntity.setRefreshTime(LocalDateTime.now());
    }

    private void setHighlightExpirationTime(AdEntity adEntity) {
        adEntity.setExpirationHighlightTime(LocalDateTime.of(LocalDate.of(1990, 1, 1), LocalTime.of(1, 0)));
    }

    public void highlightAd(Long adId) {
        AdEntity adEntity = adRepository.findById(adId).get();
        adEntity.setExpirationHighlightTime(LocalDateTime.now().plusMinutes(5));
        adRepository.save(adEntity);
    }

    public void refreshAd(Long adId) {
        AdEntity adEntity = adRepository.findById(adId).get();
        adEntity.setRefreshTime(LocalDateTime.now());
        adRepository.save(adEntity);
    }

    public void addAdView(Long adId){
        AdEntity adEntity = adRepository.findById(adId).get();
        adEntity.setCountView(adEntity.getCountView() + 1);
        adRepository.save(adEntity);
    }

    //TODO: Fix queue! mapper, dto, entity related things
//    public void addAdEntityToLastViewedQueue(Long adId,String username){
//        UserEntity userEntity = userRepository.findByUsername(username);
//        Queue<AdEntity> adEntityQueue = userRepository.findByUsername(username).getLastViewedAdEntityQueueList();
//        if(adEntityQueue.size() > 10){
//            adEntityQueue.remove();
//        }
//        adEntityQueue.add(adRepository.findById(adId).get());
//        userEntity.setLastViewedAdEntityQueueList(adEntityQueue);
//        userRepository.save(userEntity);
//    }


    public Page<AdEntity> findAllActiveByAdSearchRequest(String searchText, Boolean searchInDescription, City city,
                                                         AdCategory category, PriceRange priceRange, Integer pageNumber, Integer pageSize) {
        if (searchInDescription) {
            return adRepository.findBySearchRequest(category.getCategory(), city.getCityName(), priceRange.getFrom(),
                    priceRange.getTo(), searchText.toLowerCase(), PageRequest.of(pageNumber, pageSize, Sort.by("refresh_time").descending()));
        } else
            return adRepository.findBySearchRequestWithoutDescription(category.getCategory(), city.getCityName(), priceRange.getFrom(),
                    priceRange.getTo(), searchText.toLowerCase(), PageRequest.of(pageNumber, pageSize, Sort.by("refresh_time").descending()));
    }

    //TODO: Fronted, maximum search is by 10 words.
    public Page<AdEntity> findAllActiveByAdSearchRequestUpgraded(String searchText, City city, AdCategory
            category, PriceRange priceRange, PageRequest pageRequest) {
        String[] textsToBeSearched = searchText.toLowerCase().split(" ");
        String[] texts = new String[10];
        if (textsToBeSearched.length < 10) {
            for (int i = 0; i < textsToBeSearched.length - 1; i++) {
                texts[i] = "%" + textsToBeSearched[i] + "%";
            }
            for (int i = textsToBeSearched.length - 1; i < 10; i++) {
                texts[i] = "%" + textsToBeSearched[textsToBeSearched.length - 1] + "%";
            }
        }
        return adRepository.findBySearchRequestUpgraded(city.getCityName(), category.getCategory(),
                priceRange.getFrom(), priceRange.getTo(), texts[0], texts[1], texts[2], texts[3], texts[4], texts[5],
                texts[6], texts[7], texts[8], texts[9], pageRequest);
    }

    public Page<AdEntity> fetchRandom(){
        return adRepository.findAll(PageRequest.of(0,10));
    }
}


