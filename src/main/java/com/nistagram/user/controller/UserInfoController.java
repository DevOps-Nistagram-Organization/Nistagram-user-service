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

    @GetMapping(value = "getUser/{username}")
    public ResponseEntity<UserInfoDTO> getUserinfo(@PathVariable(value = "username") String username) {
        UserInfo userInfo = userInfoService.getUserInfo(username);
        UserInfoDTO userInfoDTO = UserInfoConverter.toDTO(userInfo);
        return new ResponseEntity<>(userInfoDTO, HttpStatus.OK);
    }

    @GetMapping(value = "getMyInfo")
    public ResponseEntity<UserInfoDTO> getMyUserinfo() {
        UserInfo userInfo = userInfoService.getMyUserInfo();
        UserInfoDTO userInfoDTO = UserInfoConverter.toDTO(userInfo);
        return new ResponseEntity<>(userInfoDTO, HttpStatus.OK);
    }

    @PostMapping(value = "saveUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoDTO> saveUser(@RequestBody() UserInfoRegistrationDTO dto, @RequestHeader("Authorization") String bearerToken) throws ActionNotAllowed {
        UserInfo userInfo = userInfoService.register(dto);
        return new ResponseEntity<>(UserInfoConverter.toDTO(userInfo), HttpStatus.OK);
    }

    @PostMapping(value = "follow")
    public ResponseEntity<UserInfoDTO> follow(@RequestBody() UsernameWrapper username) throws ActionNotAllowed {
        UserInfo userInfo = userInfoService.follow(username.getUsername());
        return new ResponseEntity<>(UserInfoConverter.toDTO(userInfo), HttpStatus.OK);
    }

    @PostMapping(value = "unfollow")
    public ResponseEntity<UserInfoDTO> unfollow(@RequestBody() UsernameWrapper username) throws ActionNotAllowed {
        UserInfo userInfo = userInfoService.unfollow(username.getUsername());
        return new ResponseEntity<>(UserInfoConverter.toDTO(userInfo), HttpStatus.OK);
    }

    @PostMapping(value = "send-follow-request")
    public ResponseEntity<UserInfoDTO> sendFollowRequest(@RequestBody() UsernameWrapper username) throws ActionNotAllowed {
        UserInfo userInfo = userInfoService.sendFollowRequest(username.getUsername());
        return new ResponseEntity<>(UserInfoConverter.toDTO(userInfo), HttpStatus.OK);
    }

    @PostMapping(value = "remove-follow-request")
    public ResponseEntity<UserInfoDTO> removeFollowRequest(@RequestBody() UsernameWrapper username) throws ActionNotAllowed {
        UserInfo userInfo = userInfoService.removeFollowRequest(username.getUsername());
        return new ResponseEntity<>(UserInfoConverter.toDTO(userInfo), HttpStatus.OK);
    }

    @PostMapping(value = "accept-follow-request")
    public ResponseEntity<UserInfoDTO> acceptFollowRequest(@RequestBody() UsernameWrapper username) throws ActionNotAllowed {
        UserInfo userInfo = userInfoService.acceptFollowRequest(username.getUsername());
        return new ResponseEntity<>(UserInfoConverter.toDTO(userInfo), HttpStatus.OK);
    }

    @PostMapping(value = "reject-follow-request")
    public ResponseEntity<UserInfoDTO> rejectFollowRequest(@RequestBody() UsernameWrapper username) throws ActionNotAllowed {
        UserInfo userInfo = userInfoService.rejectFollowRequest(username.getUsername());
        return new ResponseEntity<>(UserInfoConverter.toDTO(userInfo), HttpStatus.OK);
    }

    @PostMapping(value = "search")
    public ResponseEntity<List<UserInfoDTO>> search(@RequestBody() SearchDTO searchDTO) throws ActionNotAllowed {
        Set<UserInfo> result = userInfoService.search(searchDTO);
        List<UserInfoDTO> resultDTO = result.stream().map(UserInfoConverter::toBasicDTO).collect(Collectors.toList());
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @PutMapping(value = "edit")
    public ResponseEntity<UserInfoDTO> editInfo(@RequestBody() UserInfoDTO dto) throws ActionNotAllowed {
        UserInfo userInfo = userInfoService.editInfo(dto);
        return new ResponseEntity<>(UserInfoConverter.toDTO(userInfo), HttpStatus.OK);
    }

    @GetMapping(value = "getNotApprovedAgents")
    public ResponseEntity<List<UserInfoDTO>> getNotApprovedAgents() {
        List<UserInfo> agents = userInfoService.getNotApprovedAgents();
        List<UserInfoDTO> userInfoDTOS = agents.stream().map(UserInfoConverter::toDTO).collect(Collectors.toList());
        return new ResponseEntity<>(userInfoDTOS, HttpStatus.OK);
    }

    @PostMapping(value = "approveAgent")
    public ResponseEntity<UserInfoDTO> approveAgent(@RequestBody() UsernameWrapper dto) throws ActionNotAllowed {
        UserInfo userInfo = userInfoService.approveAgent(dto);
        return new ResponseEntity<>(UserInfoConverter.toDTO(userInfo), HttpStatus.OK);
    }

    @PostMapping(value = "rejectAgent")
    public ResponseEntity<UserInfoDTO> rejectAgent(@RequestBody() UsernameWrapper dto) throws ActionNotAllowed {
        UserInfo userInfo = userInfoService.rejectAgent(dto);
        return new ResponseEntity<>(UserInfoConverter.toDTO(userInfo), HttpStatus.OK);
    }
    @PostMapping(value = "mute")
    public ResponseEntity<UserInfoDTO> muteUser(@RequestBody() UsernameWrapper dto) throws ActionNotAllowed {
        UserInfo userInfo = userInfoService.muteUser(dto);
        return new ResponseEntity<>(UserInfoConverter.toDTO(userInfo), HttpStatus.OK);
    }
    @PostMapping(value = "unmute")
    public ResponseEntity<UserInfoDTO> unmuteUser(@RequestBody() UsernameWrapper dto) throws ActionNotAllowed {
        UserInfo userInfo = userInfoService.unmuteUser(dto);
        return new ResponseEntity<>(UserInfoConverter.toDTO(userInfo), HttpStatus.OK);
    }
    @PostMapping(value = "block")
    public ResponseEntity<UserInfoDTO> blockUser(@RequestBody() UsernameWrapper dto) throws ActionNotAllowed {
        UserInfo userInfo = userInfoService.blockUser(dto);
        return new ResponseEntity<>(UserInfoConverter.toDTO(userInfo), HttpStatus.OK);
    }
}
