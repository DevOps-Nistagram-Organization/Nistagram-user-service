package com.nistagram.user.service;

import com.nistagram.user.config.JwtTokenUtil;
import com.nistagram.user.converter.UserInfoConverter;
import com.nistagram.user.exceptions.ActionNotAllowed;
import com.nistagram.user.model.dto.SearchDTO;
import com.nistagram.user.model.dto.UserInfoDTO;
import com.nistagram.user.model.dto.UserInfoRegistrationDTO;
import com.nistagram.user.model.dto.UsernameWrapper;
import com.nistagram.user.model.entity.UserInfo;
import com.nistagram.user.reposiotry.UserInfoRepository;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public UserInfo getUserInfo(String username) {
        return userInfoRepository.findByUsername(username);
    }


    public UserInfo register(UserInfoRegistrationDTO dto) throws ActionNotAllowed {

        UserInfo existing = userInfoRepository.findByUsername(dto.getUsername());
        if (existing != null) {
            throw new ActionNotAllowed();
        }
        UserInfo userInfo = UserInfoConverter.registerUserInfo(dto);
        userInfo.setPublicProfile(true);
        userInfo.setApprovedAgent(false);
        return userInfoRepository.save(userInfo);
    }

    private String getUsername() {
        String token = SecurityContextHolder.getContext().getAuthentication().getDetails().toString();
        String jwtToken = token.substring(7);
        return jwtTokenUtil.getUsernameFromToken(jwtToken);
    }

    public UserInfo getMyUserInfo() {
        String myUsername = getUsername();
        return getUserInfo(myUsername);
    }

    public UserInfo follow(String username) throws ActionNotAllowed {
        UserInfo myInfo = getMyUserInfo();
        UserInfo followingUserInfo = getUserInfo(username);
        blockActionIfNeeded(myInfo, followingUserInfo);
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

    public UserInfo unfollow(String username) throws ActionNotAllowed {
        UserInfo myInfo = getMyUserInfo();
        UserInfo followingUserInfo = getUserInfo(username);
        blockActionIfNeeded(myInfo, followingUserInfo);
        if (followingUserInfo.getFollowers().contains(myInfo) || myInfo.getFollowing().contains(followingUserInfo)) {
            myInfo.getFollowing().remove(followingUserInfo);
            followingUserInfo.getFollowers().remove(myInfo);
            userInfoRepository.save(myInfo);
            userInfoRepository.save(followingUserInfo);
        }
        return myInfo;
    }

    public UserInfo sendFollowRequest(String username) throws ActionNotAllowed {
        UserInfo myInfo = getMyUserInfo();
        UserInfo followingUserInfo = getUserInfo(username);
        blockActionIfNeeded(myInfo, followingUserInfo);
        if (!followingUserInfo.getFollowers().contains(myInfo) && !myInfo.getSentFollowRequests().contains(followingUserInfo) && !followingUserInfo.getPublicProfile()) {
            myInfo.getSentFollowRequests().add(followingUserInfo);
            followingUserInfo.getReceivedFollowRequests().add(myInfo);
            userInfoRepository.save(myInfo);
            userInfoRepository.save(followingUserInfo);
        }
        return myInfo;
    }

    public UserInfo removeFollowRequest(String username) throws ActionNotAllowed {
        UserInfo myInfo = getMyUserInfo();
        UserInfo followingUserInfo = getUserInfo(username);
        blockActionIfNeeded(myInfo, followingUserInfo);
        if (myInfo.getSentFollowRequests().contains(followingUserInfo)) {
            myInfo.getSentFollowRequests().remove(followingUserInfo);
            followingUserInfo.getReceivedFollowRequests().remove(myInfo);
            userInfoRepository.save(myInfo);
            userInfoRepository.save(followingUserInfo);
        }
        return myInfo;
    }

    public UserInfo acceptFollowRequest(String username) throws ActionNotAllowed {
        UserInfo myInfo = getMyUserInfo();
        UserInfo followingUserInfo = getUserInfo(username);
        blockActionIfNeeded(myInfo, followingUserInfo);
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

    public UserInfo rejectFollowRequest(String username) throws ActionNotAllowed {
        UserInfo myInfo = getMyUserInfo();
        UserInfo followingUserInfo = getUserInfo(username);
        blockActionIfNeeded(myInfo, followingUserInfo);
        if (myInfo.getReceivedFollowRequests().contains(followingUserInfo)) {
            myInfo.getReceivedFollowRequests().remove(followingUserInfo);
            followingUserInfo.getSentFollowRequests().remove(myInfo);
            userInfoRepository.save(myInfo);
            userInfoRepository.save(followingUserInfo);
        }
        return myInfo;
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
            userInfo.setAgent(existingUserInfo.getAgent());
            userInfo.setFollowing(existingUserInfo.getFollowing());
            userInfo.setReceivedFollowRequests(existingUserInfo.getReceivedFollowRequests());
            userInfo.setSentFollowRequests(existingUserInfo.getSentFollowRequests());
            return userInfoRepository.save(UserInfoConverter.toUserInfo(dto));
        }
        throw new ActionNotAllowed();
    }

    public List<UserInfo> getNotApprovedAgents() {
        return userInfoRepository.findByAgentAndApprovedAgent(true, false);
    }

    public UserInfo approveAgent(UsernameWrapper userInfoDTO) {
        UserInfo userInfo = userInfoRepository.findByUsername(userInfoDTO.getUsername());
        userInfo.setApprovedAgent(true);
        return userInfoRepository.save(userInfo);
    }

    public UserInfo rejectAgent(UsernameWrapper userInfoDTO) {
        UserInfo userInfo = userInfoRepository.findByUsername(userInfoDTO.getUsername());
        userInfo.setAgent(false);
        return userInfoRepository.save(userInfo);
    }

    public UserInfo muteUser(UsernameWrapper usernameWrapper) {
        UserInfo myInfo = getMyUserInfo();
        UserInfo otherInfo = getUserInfo(usernameWrapper.getUsername());
        myInfo.getMutedUsers().add(otherInfo);
        return userInfoRepository.save(myInfo);
    }

    public UserInfo unmuteUser(UsernameWrapper usernameWrapper) {
        UserInfo myInfo = getMyUserInfo();
        UserInfo otherInfo = getUserInfo(usernameWrapper.getUsername());
        myInfo.getMutedUsers().remove(otherInfo);
        return userInfoRepository.save(myInfo);
    }

    public UserInfo blockUser(UsernameWrapper usernameWrapper) {
        UserInfo myInfo = getMyUserInfo();
        UserInfo otherInfo = getUserInfo(usernameWrapper.getUsername());
        myInfo.getBlockedUsers().add(otherInfo);
        myInfo.getMutedUsers().remove(otherInfo);
        myInfo.getFollowers().remove(otherInfo);
        myInfo.getFollowing().remove(otherInfo);
        myInfo.getSentFollowRequests().remove(otherInfo);
        myInfo.getReceivedFollowRequests().remove(otherInfo);

        otherInfo.getMutedUsers().remove(myInfo);
        otherInfo.getFollowers().remove(myInfo);
        otherInfo.getFollowing().remove(myInfo);
        otherInfo.getSentFollowRequests().remove(myInfo);
        otherInfo.getReceivedFollowRequests().remove(myInfo);
        userInfoRepository.save(otherInfo);
        return userInfoRepository.save(myInfo);
    }

    private void blockActionIfNeeded(UserInfo myInfo, UserInfo otherInfo) throws ActionNotAllowed {
        if(myInfo.getBlockedUsers().contains(otherInfo) || otherInfo.getBlockedUsers().contains(myInfo)) {
            throw new ActionNotAllowed();
        }
    }
}
