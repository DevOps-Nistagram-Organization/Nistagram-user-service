package com.nistagram.user.model.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class UserInfo {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private String phone;
    @Column
    private String gender;
    @Column
    private Date dateOfBirth;
    @Column
    private String website;
    @Column
    private String biography;

    @Column
    private Boolean agent;
    @Column
    private Boolean approvedAgent;
    @Column
    private String imagePath;

    @Column()
    private Boolean publicProfile;

    @ManyToMany
    private Set<UserInfo> followers;
    @ManyToMany
    private Set<UserInfo> following;
    @ManyToMany
    private Set<UserInfo> sentFollowRequests;
    @ManyToMany
    private Set<UserInfo> receivedFollowRequests;


    public UserInfo() {
    }

    public UserInfo(String username, String firstName, String lastName, String email, String gender, Date dateOfBirth, Boolean agent) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.agent = agent;
    }

    public UserInfo(Long id, String username, String firstName, String lastName, String email, String phone, String gender, Date dateOfBirth, String website, String biography, Boolean agent, Set<UserInfo> followers, Set<UserInfo> following, Set<UserInfo> sentFollowRequests, Set<UserInfo> receivedFollowRequests, String imagePath, Boolean publicProfile) {
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

    public Set<UserInfo> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<UserInfo> followers) {
        this.followers = followers;
    }

    public Set<UserInfo> getFollowing() {
        return following;
    }

    public void setFollowing(Set<UserInfo> following) {
        this.following = following;
    }

    public Set<UserInfo> getSentFollowRequests() {
        return sentFollowRequests;
    }

    public void setSentFollowRequests(Set<UserInfo> sentFollowRequests) {
        this.sentFollowRequests = sentFollowRequests;
    }

    public Set<UserInfo> getReceivedFollowRequests() {
        return receivedFollowRequests;
    }

    public void setReceivedFollowRequests(Set<UserInfo> receivedFollowRequests) {
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

    public Boolean getApprovedAgent() {
        return approvedAgent;
    }

    public void setApprovedAgent(Boolean approvedAgent) {
        this.approvedAgent = approvedAgent;
    }
}
