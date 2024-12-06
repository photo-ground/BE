package ceos.phototoground.photoProfile.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStyle is a Querydsl query type for Style
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStyle extends EntityPathBase<Style> {

    private static final long serialVersionUID = -770305092L;

    public static final QStyle style = new QStyle("style");

    public final ceos.phototoground.global.QBaseTimeEntity _super = new ceos.phototoground.global.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QStyle(String variable) {
        super(Style.class, forVariable(variable));
    }

    public QStyle(Path<? extends Style> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStyle(PathMetadata metadata) {
        super(Style.class, metadata);
    }

}

