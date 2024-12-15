package ceos.phototoground.photographer.repository;


import ceos.phototoground.photoProfile.domain.QPhotoProfile;
import ceos.phototoground.photographer.domain.Photographer;
import ceos.phototoground.photographer.domain.QPhotographer;
import ceos.phototoground.univ.domain.QPhotographerUniv;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PhotographerRepositoryImpl implements PhotographerRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Photographer> findPhotographerWithNoOffset(Long cursor, int size, String univ, String gender) {

        QPhotographer photographer = QPhotographer.photographer;
        QPhotoProfile photoProfile = QPhotoProfile.photoProfile;
        QPhotographerUniv photographerUniv = QPhotographerUniv.photographerUniv;

        List<Photographer> photographers = jpaQueryFactory
                .selectFrom(photographer)
                .leftJoin(photographer.photoProfile, photoProfile).fetchJoin()
                .join(photographerUniv)
                .on(photographer.id.eq(photographerUniv.photographer.id)) //한 번의 쿼리로 photographerUniv 까지 가져오려고
                .join(photographerUniv.univ)
                .where(eqUniv(univ, photographerUniv), eqGender(gender, photographer), ltCursorId(cursor, photographer))
                .orderBy(photographer.id.desc())
                .limit(size)
                .fetch();

        return photographers;

    }

    //학교필터링
    private BooleanExpression eqUniv(String univ, QPhotographerUniv photographerUniv) {
        if (univ == null) {
            return null;
        }
        return photographerUniv.univ.name.eq(univ);
    }

    //성별필터링
    private BooleanExpression eqGender(String gender, QPhotographer photographer) {
        if (gender == null) {
            return null;
        }
        return photographer.gender.stringValue().eq(gender);
    }

    //페이징
    private BooleanExpression ltCursorId(Long cursorId, QPhotographer photographer) {
        if (cursorId == null) {
            return null;
        }
        return photographer.id.lt(cursorId);
    }
}
