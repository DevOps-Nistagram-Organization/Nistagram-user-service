package com.nistagram.user.model.dto;

import java.util.Date;
import java.util.Set;

public class UserInfoDTO {

    private Long id;

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
    private Date dateOfBirth;
    private String website;
    private String biography;
    private Boolean agent;
    private String imagePath;
    private Boolean publicProfile;

    private Set<UserInfoDTO> followers;
    private Set<UserInfoDTO> following;
    private Set<UserInfoDTO> sentFollowRequests;
    private Set<UserInfoDTO> receivedFollowRequests;

    public UserInfoDTO(Long id, String username, String firstName, String lastName, String email, String phone, String gender, Date dateOfBirth, String website, String biography, Boolean agent, Set<UserInfoDTO> followers, Set<UserInfoDTO> following, Set<UserInfoDTO> sentFollowRequests, Set<UserInfoDTO> receivedFollowRequests, String imagePath, Boolean publicProfile) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.website = website;
        this.biography = biography;
        this.agent = agent;
        this.followers = followers;
        this.following = following;
        this.sentFollowRequests = sentFollowRequests;
        this.receivedFollowRequests = receivedFollowRequests;
        this.imagePath = imagePath;
        this.publicProfile = publicProfile;
    }

    public UserInfoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Boolean getAgent() {
        return agent;
    }

    public void setAgent(Boolean agent) {
        this.agent = agent;
    }

    public Set<UserInfoDTO> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<UserInfoDTO> followers) {
        this.followers = followers;
    }

    public Set<UserInfoDTO> getFollowing() {
        return following;
    }

    public void setFollowing(Set<UserInfoDTO> following) {
        this.following = following;
    }

    public Set<UserInfoDTO> getSentFollowRequests() {
        return sentFollowRequests;
    }

    public void setSentFollowRequests(Set<UserInfoDTO> sentFollowRequests) {
        this.sentFollowRequests = sentFollowRequests;
    }

    public Set<UserInfoDTO> getReceivedFollowRequests() {
        return receivedFollowRequests;
    }

    public void setReceivedFollowRequests(Set<UserInfoDTO> receivedFollowRequests) {
        this.receivedFollowRequests = receivedFollowRequests;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Boolean getPublicProfile() {
        return publicProfile;
    }

    public void setPublicProfile(Boolean publicProfile) {
        this.publicProfile = publicProfile;
    }
}
