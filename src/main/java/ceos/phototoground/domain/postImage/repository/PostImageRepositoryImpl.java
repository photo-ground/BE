package ceos.phototoground.domain.postImage.repository;

import ceos.phototoground.domain.photoProfile.entity.QPhotoProfile;
import ceos.phototoground.domain.photographer.entity.QPhotographer;
import ceos.phototoground.domain.post.entity.QPost;
import ceos.phototoground.domain.postImage.entity.QPostImage;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostImageRepositoryImpl implements PostImageRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Tuple> findBySpot_Id(Long spotId, Long cursor, int size) {

        QPostImage postImage = QPostImage.postImage;
        QPost post = QPost.post;
        QPhotographer photographer = QPhotographer.photographer;
        QPhotoProfile photoProfile = QPhotoProfile.photoProfile;

        List<Tuple> tuples = jpaQueryFactory
                .select(postImage, post, photoProfile)
                .from(postImage)
                .leftJoin(postImage.post, post).fetchJoin()
                .leftJoin(post.photographer, photographer).fetchJoin()
                .leftJoin(photographer.photoProfile, photoProfile).fetchJoin()
                .where(eqSpotId(postImage, spotId), ltCursorId(cursor, postImage))
                .orderBy(postImage.id.desc())
                .limit(size)
                .fetch();

        return tuples;

    }

    private BooleanExpression eqSpotId(QPostImage postImage, Long spotId) {

        if (spotId == null) {
            return null;
        }

        return postImage.spot.id.eq(spotId);
    }

    private BooleanExpression ltCursorId(Long cursor, QPostImage postImage) {

        if (cursor == null) {
            return null;
        }

        return postImage.id.lt(cursor);
    }
}
