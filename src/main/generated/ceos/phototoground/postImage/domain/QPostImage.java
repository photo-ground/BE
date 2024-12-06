package ceos.phototoground.postImage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostImage is a Querydsl query type for PostImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostImage extends EntityPathBase<PostImage> {

    private static final long serialVersionUID = -1442236890L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostImage postImage = new QPostImage("postImage");

    public final ceos.phototoground.global.QBaseTimeEntity _super = new ceos.phototoground.global.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> imageOrder = createNumber("imageOrder", Integer.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final StringPath originalFileName = createString("originalFileName");

    public final ceos.phototoground.post.domain.QPost post;

    public final ceos.phototoground.spot.domain.QSpot spot;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPostImage(String variable) {
        this(PostImage.class, forVariable(variable), INITS);
    }

    public QPostImage(Path<? extends PostImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostImage(PathMetadata metadata, PathInits inits) {
        this(PostImage.class, metadata, inits);
    }

    public QPostImage(Class<? extends PostImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new ceos.phototoground.post.domain.QPost(forProperty("post"), inits.get("post")) : null;
        this.spot = inits.isInitialized("spot") ? new ceos.phototoground.spot.domain.QSpot(forProperty("spot"), inits.get("spot")) : null;
    }

}

