package ceos.phototoground.domain.photographer.repository;


import ceos.phototoground.domain.photoProfile.entity.QPhotoProfile;
import ceos.phototoground.domain.photographer.entity.Photographer;
import ceos.phototoground.domain.photographer.entity.QPhotographer;
import ceos.phototoground.domain.univ.entity.QPhotographerUniv;
import com.querydsl.core.Tuple;
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
    public List<Tuple> findPhotographerWithNoOffset(Long cursor, int size, String univ, String gender) {

        QPhotographer photographer = QPhotographer.photographer;
        QPhotoProfile photoProfile = QPhotoProfile.photoProfile;
        QPhotographerUniv photographerUniv = QPhotographerUniv.photographerUniv;

        System.out.println("쿼리DSL size : " + size);
/*
        List<Photographer> photographers = jpaQueryFactory
                .selectDistinct(photographer)
                .from(photographer)
                .leftJoin(photographer.photoProfile, photoProfile).fetchJoin()
                .leftJoin(photographerUniv).fetchJoin()
                .on(photographer.id.eq(photographerUniv.photographer.id)) //한 번의 쿼리로 photographerUniv 까지 가져오려고
                .leftJoin(photographerUniv.univ)
                .where(eqUniv(univ, photographerUniv), eqGender(gender, photographer), ltCursorId(cursor, photographer))
                .orderBy(photographer.id.desc())
                .limit(size)
                .fetch();
*/

        List<Photographer> photographers = jpaQueryFactory
                .selectDistinct(photographer)
                .from(photographer)
                .leftJoin(photographerUniv)
                .on(photographer.id.eq(photographerUniv.photographer.id)) //한 번의 쿼리로 photographerUniv 까지 가져오려고
                .where(eqUniv(univ, photographerUniv), eqGender(gender, photographer), ltCursorId(cursor, photographer))
                .orderBy(photographer.id.desc())
                .limit(size)
                .fetch();

        return jpaQueryFactory
                .select(photographer, photographerUniv)
                .from(photographer)
                .leftJoin(photographer.photoProfile, photoProfile).fetchJoin()
                .leftJoin(photographerUniv).on(photographer.id.eq(photographerUniv.photographer.id)).fetchJoin()
                .leftJoin(photographerUniv.univ).fetchJoin()
                .where(photographer.in(photographers))
                .fetch();

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
