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
        return userInfoRepository.save(userInfo);
    }

    private String getUsername() {
        String token = SecurityContextHolder.getContext().getAuthentication().getDetails().toString();
        return jwtTokenUtil.getUsernameFromToken(token);
    }

    public UserInfo getMyUserInfo() {
        String myUsername = getUsername();
        return getUserInfo(myUsername);
    }

}
