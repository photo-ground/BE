package ceos.phototoground.photoProfile.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPhotoStyle is a Querydsl query type for PhotoStyle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPhotoStyle extends EntityPathBase<PhotoStyle> {

    private static final long serialVersionUID = -1766189196L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPhotoStyle photoStyle = new QPhotoStyle("photoStyle");

    public final ceos.phototoground.global.QBaseTimeEntity _super = new ceos.phototoground.global.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPhotoProfile photoProfile;

    public final QStyle style;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPhotoStyle(String variable) {
        this(PhotoStyle.class, forVariable(variable), INITS);
    }

    public QPhotoStyle(Path<? extends PhotoStyle> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPhotoStyle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPhotoStyle(PathMetadata metadata, PathInits inits) {
        this(PhotoStyle.class, metadata, inits);
    }

    public QPhotoStyle(Class<? extends PhotoStyle> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.photoProfile = inits.isInitialized("photoProfile") ? new QPhotoProfile(forProperty("photoProfile"), inits.get("photoProfile")) : null;
        this.style = inits.isInitialized("style") ? new QStyle(forProperty("style")) : null;
    }

}

