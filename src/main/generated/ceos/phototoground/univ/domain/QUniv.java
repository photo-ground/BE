package ceos.phototoground.univ.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUniv is a Querydsl query type for Univ
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUniv extends EntityPathBase<Univ> {

    private static final long serialVersionUID = 1002650634L;

    public static final QUniv univ = new QUniv("univ");

    public final ceos.phototoground.global.QBaseTimeEntity _super = new ceos.phototoground.global.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QUniv(String variable) {
        super(Univ.class, forVariable(variable));
    }

    public QUniv(Path<? extends Univ> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUniv(PathMetadata metadata) {
        super(Univ.class, metadata);
    }

}

