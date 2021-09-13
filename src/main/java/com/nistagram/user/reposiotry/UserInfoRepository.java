package com.nistagram.user.reposiotry;

import com.nistagram.user.model.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findByUsername(String username);
    List<UserInfo> findAllByFirstNameContaining(String firstName);
    List<UserInfo> findAllByLastNameContaining(String lastName);
    List<UserInfo> findByAgentAndApprovedAgent(Boolean agent, Boolean approved);
}
