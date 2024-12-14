package ceos.phototoground.photoProfile.repository;

import ceos.phototoground.photoProfile.domain.PhotoProfile;
import ceos.phototoground.photoProfile.domain.QPhotoProfile;
import ceos.phototoground.photographer.domain.Photographer;
import ceos.phototoground.photographer.domain.QPhotographer;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PhotoProfileRepositoryImpl implements PhotoProfileRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    QPhotographer photographer = QPhotographer.photographer;
    QPhotoProfile photoProfile = QPhotoProfile.photoProfile;

    @Override //커서를 일치도와 ID의 조합으로 사용하여 페이징을 처리(일치도가 동일할 경우 ID를 보조적으로 사용하여 페이징 처리)
    public List<Tuple> findByNicknameContains(String name, String cursor, int size) {

        // SQL 쿼리 정의
        NumberExpression<Integer> priorityScore = matchNicknamePriority(name);

        List<Tuple> photoProfiles = jpaQueryFactory
                .select(photoProfile, photographer, priorityScore)
                .from(photoProfile)
                .join(photoProfile.photographer, photographer).fetchJoin()
                .where(searchName(name, photoProfile), ltCursor(cursor, name, photographer))   // 이름 검색 조건, 커서페이징 조건
                .orderBy(matchNicknamePriority(name).desc(),        // 일치도 순 정렬
                        photographer.id.desc())                  // 동일 일치도면 id 내림차순 정렬
                .limit(size)
                .fetch();

        return photoProfiles;
    }

    
    // 이번에 조회된 마지막 커서 값 반환 (우선순위+id)
    @Override
    public String generateNextCursor(List<Tuple> searchProfileList, String name) {

        // 마지막으로 조회된 photographer를 matchNicknamePriority 거쳐 priority 반환 + photographerId 반환
        Tuple tuple = searchProfileList.get(searchProfileList.size() - 1);
        PhotoProfile profile = tuple.get(QPhotoProfile.photoProfile);
        Photographer photographer = tuple.get(QPhotographer.photographer);
        Integer priorityScore = tuple.get(matchNicknamePriority(name));

        if (photographer == null || priorityScore == null) {
            throw new IllegalArgumentException("커서 정보를 생성할 수 없습니다. 데이터가 누락되었습니다.");
        }

        return priorityScore + "-" + photographer.getId();
    }


    // 검색 조건 (이름)
    private BooleanExpression searchName(String name, QPhotoProfile photoProfile) {

        if (name == null) {
            throw new IllegalArgumentException("검색하고 싶은 작가 이름을 입력해주세요");
        }
        return photoProfile.nickname.contains(name);
    }

    // 일치도 & id를 조합해서 페이징 처리 (일치도 순으로 정렬하고, 같은 일치도 내에서 ID의 크기를 기준으로 정렬 및 페이징)
    private BooleanExpression ltCursor(String cursor, String name, QPhotographer photographer) {

        if (name == null) {
            throw new IllegalArgumentException("검색하고 싶은 작가 이름을 입력해주세요");
        }

        if (cursor == null) {
            return null;
        }

        String[] cursorParts = cursor.split("-");
        int cursorMatchScore = Integer.parseInt(cursorParts[0]); // string->int
        Long cursorId = Long.parseLong(cursorParts[1]); // string->Long

        //조건 정의
        // cursor에 있는 priority보다 우선순위가 낮은 것들
        BooleanExpression matchScoreCondition = matchNicknamePriority(name).lt(cursorMatchScore);
        // cursor에 있는 priority와 우선순위 같은 애들
        BooleanExpression sameScoreCondition = matchNicknamePriority(name)
                .eq(cursorMatchScore)
                .and(photographer.id.lt(cursorId));

        // 조건 결합
        return matchScoreCondition.or(sameScoreCondition);
    }

    // 일치도 계산
    private NumberExpression<Integer> matchNicknamePriority(String name) {

        if (name == null) {
            throw new IllegalArgumentException("검색하고 싶은 작가 이름을 입력해주세요");
        }

        return new CaseBuilder()
                .when(QPhotoProfile.photoProfile.nickname.eq(name)).then(4)
                .when(QPhotoProfile.photoProfile.nickname.startsWith(name)).then(3)
                .when(QPhotoProfile.photoProfile.nickname.contains(name)).then(2)
                .otherwise(1);
    }


}
