package com.myTrade.services;

import com.myTrade.entities.AdEntity;
import com.myTrade.entities.ConversationEntity;
import com.myTrade.entities.UserEntity;
import com.myTrade.mappersImpl.UserMapperImpl;
import com.myTrade.repositories.AdRepository;
import com.myTrade.repositories.UserRepository;
import com.myTrade.utility.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
//implements UserDetailsService
public class UserService  {

    private UserRepository userRepository;
    private AdRepository adRepository;
    private UserMapperImpl userMapper = new UserMapperImpl();

    @Autowired
    public UserService(UserRepository userRepository, AdRepository adRepository) {
        this.userRepository = userRepository;
        this.adRepository = adRepository;
    }

    public void saveUserEntity(UserEntity userEntity) {
            userRepository.save(userEntity);
    }

    public void singUpUserByRegistrationRequest(RegistrationRequest registrationRequest){
        saveUserEntity(userMapper.registrationRequestToUserEntity(registrationRequest));
    }

    public List<AdEntity> findUserAdEntityList(String userName) {
        return userRepository.findByUserName(userName).getAdEntityList();
    }

    public List<ConversationEntity> findUserConversationEntityList(String userName) {
        return userRepository.findByUserName(userName).getConversationEntityList();
    }

    public void deleteUser(String userName) {
        userRepository.delete(userRepository.findByUserName(userName));
    }

    public void deleteAdFromAdList(String userName, Long adId) {
        UserEntity user = userRepository.findByUserName(userName);
        List<AdEntity> adEntityList = user.getAdEntityList().stream().collect(Collectors.toList());
        user.setAdEntityList(adEntityList.stream().filter(adEntity -> !adEntity.getId().equals(adId)).collect(Collectors.toList())); // better use stream to avoid using adRepository (findById)? due to this I don't need mock adrepository
        userRepository.save(user);
        adRepository.deleteById(adId);

    }

    public void addAdToAdList(String userName, AdEntity adEntity) {
        UserEntity user = userRepository.findByUserName(userName);
        List<AdEntity> adEntityList = user.getAdEntityList().stream().collect(Collectors.toList());
        adRepository.save(adEntity); // first of all i have to save adEntity to db and then add adEntity to UserAdList?
        adEntityList.add(adEntity);
        user.setAdEntityList(adEntityList);
        userRepository.save(user);
    }

//    @Override //:TODO TO DELETE?
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        return userMapper.userEntityToUserDto(userRepository.findByUserName(userName));
//    }
}

