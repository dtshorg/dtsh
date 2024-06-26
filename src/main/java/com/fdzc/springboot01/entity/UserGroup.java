package com.fdzc.springboot01.entity;

import org.springframework.stereotype.Component;

@Component
public class UserGroup {

    private Integer userId;
    private Integer groupId;

    public UserGroup() {
    }

    public UserGroup(Integer userId, Integer groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "userId=" + userId +
                ", groupId=" + groupId +
                '}';
    }
}
