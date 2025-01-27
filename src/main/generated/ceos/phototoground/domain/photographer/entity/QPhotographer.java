package ceos.phototoground.domain.photographer.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPhotographer is a Querydsl query type for Photographer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPhotographer extends EntityPathBase<Photographer> {

    private static final long serialVersionUID = 1060667575L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPhotographer photographer = new QPhotographer("photographer");

    public final ceos.phototoground.global.entity.QBaseTimeEntity _super = new ceos.phototoground.global.entity.QBaseTimeEntity(this);

    public final NumberPath<Integer> bornYear = createNumber("bornYear", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final EnumPath<Gender> gender = createEnum("gender", Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isFirst = createBoolean("isFirst");

    public final EnumPath<MyUniv> myUniv = createEnum("myUniv", MyUniv.class);

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final ceos.phototoground.domain.photoProfile.entity.QPhotoProfile photoProfile;

    public final EnumPath<ceos.phototoground.domain.customer.entity.UserRole> role = createEnum("role", ceos.phototoground.domain.customer.entity.UserRole.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPhotographer(String variable) {
        this(Photographer.class, forVariable(variable), INITS);
    }

    public QPhotographer(Path<? extends Photographer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPhotographer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPhotographer(PathMetadata metadata, PathInits inits) {
        this(Photographer.class, metadata, inits);
    }

    public QPhotographer(Class<? extends Photographer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.photoProfile = inits.isInitialized("photoProfile") ? new ceos.phototoground.domain.photoProfile.entity.QPhotoProfile(forProperty("photoProfile"), inits.get("photoProfile")) : null;
    }

}

