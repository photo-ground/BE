package ceos.phototoground.domain.postImage.dto;

import ceos.phototoground.domain.photoProfile.entity.PhotoProfile;
import ceos.phototoground.domain.post.entity.Post;
import ceos.phototoground.domain.postImage.entity.PostImage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SpotPostImage {

    private Long imageId;
    private String imageUrl;
    private Long postId;
    private String photographerName;


    public static SpotPostImage of(PostImage postImage, Post post, PhotoProfile profile) {
        return SpotPostImage.builder()
                .imageId(postImage.getId())
                .imageUrl(postImage.getImageUrl())
                .postId(post.getId())
                .photographerName(profile.getNickname())
                .build();
    }
}
