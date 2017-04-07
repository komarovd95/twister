package ru.ssau.twister.dto;

import ru.ssau.twister.domain.Comment;

public class CommentDto extends Comment {
    private Long likesCount;
    private boolean likedByMe;

    public Long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Long likesCount) {
        this.likesCount = likesCount;
    }

    public boolean isLikedByMe() {
        return likedByMe;
    }

    public void setLikedByMe(boolean likedByMe) {
        this.likedByMe = likedByMe;
    }
}
