package ceos.phototoground.domain.univ.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPhotographerUniv is a Querydsl query type for PhotographerUniv
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPhotographerUniv extends EntityPathBase<PhotographerUniv> {

    private static final long serialVersionUID = 641457498L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPhotographerUniv photographerUniv = new QPhotographerUniv("photographerUniv");

    public final ceos.phototoground.global.entity.QBaseTimeEntity _super = new ceos.phototoground.global.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ceos.phototoground.domain.photographer.entity.QPhotographer photographer;

    public final QUniv univ;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPhotographerUniv(String variable) {
        this(PhotographerUniv.class, forVariable(variable), INITS);
    }

    public QPhotographerUniv(Path<? extends PhotographerUniv> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPhotographerUniv(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPhotographerUniv(PathMetadata metadata, PathInits inits) {
        this(PhotographerUniv.class, metadata, inits);
    }

    public QPhotographerUniv(Class<? extends PhotographerUniv> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.photographer = inits.isInitialized("photographer") ? new ceos.phototoground.domain.photographer.entity.QPhotographer(forProperty("photographer"), inits.get("photographer")) : null;
        this.univ = inits.isInitialized("univ") ? new QUniv(forProperty("univ")) : null;
    }

}

