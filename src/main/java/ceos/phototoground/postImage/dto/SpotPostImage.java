package ceos.phototoground.postImage.dto;

import ceos.phototoground.photoProfile.domain.PhotoProfile;
import ceos.phototoground.post.domain.Post;
import ceos.phototoground.postImage.domain.PostImage;
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
