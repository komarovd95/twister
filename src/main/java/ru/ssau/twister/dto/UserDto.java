package ru.ssau.twister.dto;

import ru.ssau.twister.domain.User;

public class UserDto extends User {
    private Long followersCount;
    private Long followeesCount;

    private boolean followingByMe;
    private boolean followingMe;

    public Long getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Long followersCount) {
        this.followersCount = followersCount;
    }

    public Long getFolloweesCount() {
        return followeesCount;
    }

    public void setFolloweesCount(Long followeesCount) {
        this.followeesCount = followeesCount;
    }

    public boolean isFollowingByMe() {
        return followingByMe;
    }

    public void setFollowingByMe(boolean followingByMe) {
        this.followingByMe = followingByMe;
    }

    public boolean isFollowingMe() {
        return followingMe;
    }

    public void setFollowingMe(boolean followingMe) {
        this.followingMe = followingMe;
    }
}
