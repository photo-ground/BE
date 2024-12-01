package ceos.phototoground.post.repository;

import ceos.phototoground.photographer.domain.QPhotographer;
import ceos.phototoground.post.domain.QPost;
import ceos.phototoground.postImage.domain.QPostImage;
import ceos.phototoground.spot.domain.QSpot;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Tuple> findPostsAndImagesByUnivWithNoOffset(String univName, Long cursor, int size) {

        QPost post = QPost.post;
        QPhotographer photographer = QPhotographer.photographer;
        QPostImage postImage = QPostImage.postImage;
        QSpot spot = QSpot.spot;

        List<Tuple> tuples = jpaQueryFactory
                .select(post, postImage)
                .from(post)
                .leftJoin(post.photographer, photographer).fetchJoin()
                .leftJoin(postImage).on(post.firstImageUrl.eq(postImage.imageUrl)).fetchJoin()
                .leftJoin(postImage.spot, spot).fetchJoin()
                .where(
                        eqUniv(univName), ltCursorId(cursor) //post의 univ.name가 univName인 애들, id가 cursor보다 작은 애들
                )
                .orderBy(post.id.desc()) //id 내림차순으로 정렬(최신순)
                .limit(size)
                .fetch();

        return tuples;
    }

    private BooleanExpression eqUniv(String univName) {

        if (univName == null) {
            return null;
        }

        return QPost.post.univ.name.eq(univName);
    }

    private BooleanExpression ltCursorId(Long cursorId) {

        if (cursorId == null) { //맨 처음 요청 시, cursorId가 없을 것이므로(가장 마지막으로 조회한 게시글 id가 존재하지 않아서)
            return null;
        }

        return QPost.post.id.lt(cursorId);
    }


}
