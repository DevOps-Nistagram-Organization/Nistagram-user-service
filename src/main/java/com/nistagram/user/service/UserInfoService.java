package com.nistagram.user.service;

import com.nistagram.user.config.JwtTokenUtil;
import com.nistagram.user.converter.UserInfoConverter;
import com.nistagram.user.exceptions.ActionNotAllowed;
import com.nistagram.user.model.dto.SearchDTO;
import com.nistagram.user.model.dto.UserInfoDTO;
import com.nistagram.user.model.dto.UserInfoRegistrationDTO;
import com.nistagram.user.model.entity.UserInfo;
import com.nistagram.user.reposiotry.UserInfoRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final JwtTokenUtil jwtTokenUtil;

    public UserInfoService(UserInfoRepository userInfoRepository, JwtTokenUtil jwtTokenUtil) {
        this.userInfoRepository = userInfoRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public UserInfo follow(String username) throws ActionNotAllowed {
        UserInfo myInfo = getMyUserInfo();
        UserInfo followingUserInfo = getUserInfo(username);
        if (followingUserInfo.getPublicProfile()) {
            myInfo.getFollowing().add(followingUserInfo);
            followingUserInfo.getFollowers().add(myInfo);
            userInfoRepository.save(myInfo);
            userInfoRepository.save(followingUserInfo);
        } else {
            throw new ActionNotAllowed();
        }
        return myInfo;
    }

    public UserInfo unfollow(String username) {
        UserInfo myInfo = getMyUserInfo();
        UserInfo followingUserInfo = getUserInfo(username);
        if (followingUserInfo.getFollowers().contains(myInfo) || myInfo.getFollowing().contains(followingUserInfo)) {
            myInfo.getFollowing().remove(followingUserInfo);
            followingUserInfo.getFollowers().remove(myInfo);
            userInfoRepository.save(myInfo);
            userInfoRepository.save(followingUserInfo);
        }
        return myInfo;
    }

    public UserInfo sendFollowRequest(String username) {
        UserInfo myInfo = getMyUserInfo();
        UserInfo followingUserInfo = getUserInfo(username);
        if (!followingUserInfo.getFollowers().contains(myInfo) && !myInfo.getSentFollowRequests().contains(followingUserInfo) && !followingUserInfo.getPublicProfile()) {
            myInfo.getSentFollowRequests().add(followingUserInfo);
            followingUserInfo.getReceivedFollowRequests().add(myInfo);
            userInfoRepository.save(myInfo);
            userInfoRepository.save(followingUserInfo);
        }
        return myInfo;
    }

    public UserInfo removeFollowRequest(String username) {
        UserInfo myInfo = getMyUserInfo();
        UserInfo followingUserInfo = getUserInfo(username);
        if (myInfo.getSentFollowRequests().contains(followingUserInfo)) {
            myInfo.getSentFollowRequests().remove(followingUserInfo);
            followingUserInfo.getReceivedFollowRequests().remove(myInfo);
            userInfoRepository.save(myInfo);
            userInfoRepository.save(followingUserInfo);
        }
        return myInfo;
    }

    public UserInfo acceptFollowRequest(String username) {
        UserInfo myInfo = getMyUserInfo();
        UserInfo followingUserInfo = getUserInfo(username);
        if (myInfo.getReceivedFollowRequests().contains(followingUserInfo)) {
            myInfo.getReceivedFollowRequests().remove(followingUserInfo);
            followingUserInfo.getSentFollowRequests().remove(myInfo);
            myInfo.getFollowers().add(followingUserInfo);
            followingUserInfo.getFollowing().add(myInfo);
            userInfoRepository.save(myInfo);
            userInfoRepository.save(followingUserInfo);
        }
        return myInfo;
    }

    public UserInfo rejectFollowRequest(String username) {
        UserInfo myInfo = getMyUserInfo();
        UserInfo followingUserInfo = getUserInfo(username);
        if (myInfo.getReceivedFollowRequests().contains(followingUserInfo)) {
            myInfo.getReceivedFollowRequests().remove(followingUserInfo);
            followingUserInfo.getSentFollowRequests().remove(myInfo);
            userInfoRepository.save(myInfo);
            userInfoRepository.save(followingUserInfo);
        }
        return myInfo;
    }

    public UserInfo getUserInfo(String username) {
        return getUserInfo(username);
    }

    public Set<UserInfo> search(SearchDTO searchDTO) {
        Set<UserInfo> result = new HashSet<>();
        Arrays.stream(searchDTO.getName().split(" ")).forEach(s -> {
                    result.addAll(userInfoRepository.findAllByFirstNameContaining(s));
                    result.addAll(userInfoRepository.findAllByLastNameContaining(s));
                }
        );
        return result;
    }

    public UserInfo editInfo(UserInfoDTO dto) throws ActionNotAllowed {
        UserInfo existingUserInfo = getMyUserInfo();
        if (getUsername().equals(dto.getUsername())) {
            UserInfo userInfo = UserInfoConverter.basicConversionToUserInfo(dto);
            userInfo.setFollowers(existingUserInfo.getFollowers());
            userInfo.setFollowing(existingUserInfo.getFollowing());
            userInfo.setReceivedFollowRequests(existingUserInfo.getReceivedFollowRequests());
            userInfo.setSentFollowRequests(existingUserInfo.getSentFollowRequests());
            return userInfoRepository.save(UserInfoConverter.toUserInfo(dto));
        }
        throw new ActionNotAllowed();
    }

    public UserInfo register(UserInfoRegistrationDTO dto) throws ActionNotAllowed {

        UserInfo existing = userInfoRepository.findByUsername(dto.getUsername());
        if (existing != null) {
            throw new ActionNotAllowed();
        }
        UserInfo userInfo = UserInfoConverter.registerUserInfo(dto);
        userInfo.setPublicProfile(true);
        return userInfoRepository.save(userInfo);
    }

    private String getUsername() {
        String token = SecurityContextHolder.getContext().getAuthentication().getDetails().toString();
        return jwtTokenUtil.getUsernameFromToken(token);
    }

    private UserInfo getMyUserInfo() {
        String myUsername = getUsername();
        return getUserInfo(myUsername);
    }

}
