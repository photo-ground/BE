package ceos.phototoground.domain.spot.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSpot is a Querydsl query type for Spot
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSpot extends EntityPathBase<Spot> {

    private static final long serialVersionUID = 1815063337L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSpot spot = new QSpot("spot");

    public final ceos.phototoground.global.entity.QBaseTimeEntity _super = new ceos.phototoground.global.entity.QBaseTimeEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Float> latitude = createNumber("latitude", Float.class);

    public final NumberPath<Float> longitude = createNumber("longitude", Float.class);

    public final StringPath name = createString("name");

    public final StringPath spotImageUrl = createString("spotImageUrl");

    public final ceos.phototoground.domain.univ.entity.QUniv univ;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSpot(String variable) {
        this(Spot.class, forVariable(variable), INITS);
    }

    public QSpot(Path<? extends Spot> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSpot(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSpot(PathMetadata metadata, PathInits inits) {
        this(Spot.class, metadata, inits);
    }

    public QSpot(Class<? extends Spot> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.univ = inits.isInitialized("univ") ? new ceos.phototoground.domain.univ.entity.QUniv(forProperty("univ")) : null;
    }

}

