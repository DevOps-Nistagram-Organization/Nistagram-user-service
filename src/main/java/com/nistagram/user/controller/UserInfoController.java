package com.nistagram.user.controller;

import com.nistagram.user.converter.UserInfoConverter;
import com.nistagram.user.exceptions.ActionNotAllowed;
import com.nistagram.user.model.dto.SearchDTO;
import com.nistagram.user.model.dto.UserInfoDTO;
import com.nistagram.user.model.dto.UserInfoRegistrationDTO;
import com.nistagram.user.model.dto.UsernameWrapper;
import com.nistagram.user.model.entity.UserInfo;
import com.nistagram.user.service.UserInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "user")
public class UserInfoController {

    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping(value = "saveUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoDTO> saveUser(@RequestBody() UserInfoRegistrationDTO dto, @RequestHeader("Authorization") String bearerToken) throws ActionNotAllowed {
        UserInfo userInfo = userInfoService.register(dto);
        return new ResponseEntity<>(UserInfoConverter.toDTO(userInfo), HttpStatus.OK);
    }
}
