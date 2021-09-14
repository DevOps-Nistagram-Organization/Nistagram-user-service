package com.nistagram.user.converter;

import com.nistagram.user.model.dto.UserInfoDTO;
import com.nistagram.user.model.dto.UserInfoRegistrationDTO;
import com.nistagram.user.model.entity.UserInfo;
import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserInfoConverter {

    public static UserInfo registerUserInfo(UserInfoRegistrationDTO dto) {
        UserInfo userInfo = new UserInfo(dto.getUsername(), dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getGender(), dto.getDateOfBirth(), dto.getAgent());
        userInfo.setWebsite(dto.getWebsite());
        return userInfo;
    }

    public static UserInfo basicConversionToUserInfo(UserInfoDTO dto) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(dto.getId());
        userInfo.setAgent(dto.getAgent());
        userInfo.setEmail(dto.getEmail());
        userInfo.setBiography(dto.getBiography());
        userInfo.setFirstName(dto.getFirstName());
        userInfo.setLastName(dto.getLastName());
        userInfo.setDateOfBirth(dto.getDateOfBirth());
        userInfo.setGender(dto.getGender());
        userInfo.setPhone(dto.getPhone());
        userInfo.setImagePath(dto.getImagePath());
        return userInfo;
    }

    //TODO: check if working. Possible corruption
    public static UserInfo toUserInfo(UserInfoDTO dto) {
        Set<UserInfo> followers = new HashSet<>();
        if (dto.getFollowers() != null) {
            followers.addAll(dto.getFollowers().stream().map(UserInfoConverter::basicConversionToUserInfo).collect(Collectors.toSet()));
        }
        Set<UserInfo> following = new HashSet<>();
        if (dto.getFollowing() != null) {
            following.addAll(dto.getFollowing().stream().map(UserInfoConverter::basicConversionToUserInfo).collect(Collectors.toSet()));
        }
        Set<UserInfo> receivedFollowRequests = new HashSet<>();
        if (dto.getReceivedFollowRequests() != null) {
            receivedFollowRequests.addAll(dto.getReceivedFollowRequests().stream().map(UserInfoConverter::basicConversionToUserInfo).collect(Collectors.toSet()));
        }
        Set<UserInfo> sentFollowRequests = new HashSet<>();
        if (dto.getSentFollowRequests() != null) {
            sentFollowRequests.addAll(dto.getSentFollowRequests().stream().map(UserInfoConverter::basicConversionToUserInfo).collect(Collectors.toSet()));
        }
        return new UserInfo(dto.getId(),
                dto.getUsername(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getGender(),
                dto.getDateOfBirth(),
                dto.getWebsite(),
                dto.getBiography(),
                dto.getAgent(),
                followers,
                following,
                sentFollowRequests,
                receivedFollowRequests,
                dto.getImagePath(),
                dto.getPublicProfile());
    }

    public static UserInfoDTO toBasicDTO(UserInfo info) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setAgent(info.getAgent());
        userInfoDTO.setBiography(info.getBiography());
        userInfoDTO.setEmail(info.getEmail());
        userInfoDTO.setFirstName(info.getFirstName());
        userInfoDTO.setDateOfBirth(info.getDateOfBirth());
        userInfoDTO.setGender(info.getGender());
        userInfoDTO.setImagePath(info.getImagePath());
        userInfoDTO.setId(info.getId());
        userInfoDTO.setPhone(info.getPhone());
        userInfoDTO.setLastName(info.getLastName());
        userInfoDTO.setUsername(info.getUsername());
        userInfoDTO.setPublicProfile(info.getPublicProfile());
        userInfoDTO.setWebsite(info.getWebsite());
        return userInfoDTO;
    }

    public static UserInfoDTO toDTO(UserInfo info) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setAgent(info.getAgent());
        userInfoDTO.setBiography(info.getBiography());
        userInfoDTO.setEmail(info.getEmail());
        userInfoDTO.setFirstName(info.getFirstName());
        userInfoDTO.setDateOfBirth(info.getDateOfBirth());
        userInfoDTO.setGender(info.getGender());
        userInfoDTO.setImagePath(info.getImagePath());
        userInfoDTO.setId(info.getId());
        userInfoDTO.setPhone(info.getPhone());
        userInfoDTO.setLastName(info.getLastName());
        userInfoDTO.setUsername(info.getUsername());
        userInfoDTO.setPublicProfile(info.getPublicProfile());
        userInfoDTO.setWebsite(info.getWebsite());
        if (info.getFollowers() != null) {
            userInfoDTO.setFollowers(info.getFollowers().stream().map(UserInfoConverter::toBasicDTO).collect(Collectors.toSet()));
        }
        if (info.getFollowing() != null) {
            userInfoDTO.setFollowing(info.getFollowing().stream().map(UserInfoConverter::toBasicDTO).collect(Collectors.toSet()));
        }
        if (info.getReceivedFollowRequests() != null) {
            userInfoDTO.setReceivedFollowRequests(info.getReceivedFollowRequests().stream().map(UserInfoConverter::toBasicDTO).collect(Collectors.toSet()));
        }
        if (info.getSentFollowRequests() != null) {
            userInfoDTO.setSentFollowRequests(info.getSentFollowRequests().stream().map(UserInfoConverter::toBasicDTO).collect(Collectors.toSet()));
        }
        return userInfoDTO;
    }

}
