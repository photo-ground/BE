package ceos.phototoground.photoProfile.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPhotoProfile is a Querydsl query type for PhotoProfile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPhotoProfile extends EntityPathBase<PhotoProfile> {

    private static final long serialVersionUID = 770055852L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPhotoProfile photoProfile = new QPhotoProfile("photoProfile");

    public final ceos.phototoground.global.entity.QBaseTimeEntity _super = new ceos.phototoground.global.entity.QBaseTimeEntity(this);

    public final StringPath account = createString("account");

    public final NumberPath<Long> addPrice = createNumber("addPrice", Long.class);

    public final StringPath bank = createString("bank");

    public final StringPath camera = createString("camera");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> followerNum = createNumber("followerNum", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduction = createString("introduction");

    public final StringPath nickname = createString("nickname");

    public final ceos.phototoground.photographer.domain.QPhotographer photographer;

    public final ListPath<PhotoStyle, QPhotoStyle> photoStyles = this.<PhotoStyle, QPhotoStyle>createList("photoStyles", PhotoStyle.class, QPhotoStyle.class, PathInits.DIRECT2);

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final StringPath profileUrl = createString("profileUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPhotoProfile(String variable) {
        this(PhotoProfile.class, forVariable(variable), INITS);
    }

    public QPhotoProfile(Path<? extends PhotoProfile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPhotoProfile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPhotoProfile(PathMetadata metadata, PathInits inits) {
        this(PhotoProfile.class, metadata, inits);
    }

    public QPhotoProfile(Class<? extends PhotoProfile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.photographer = inits.isInitialized("photographer") ? new ceos.phototoground.photographer.domain.QPhotographer(forProperty("photographer"), inits.get("photographer")) : null;
    }

}

