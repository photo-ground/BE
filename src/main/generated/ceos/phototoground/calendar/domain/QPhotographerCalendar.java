package ceos.phototoground.calendar.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPhotographerCalendar is a Querydsl query type for PhotographerCalendar
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPhotographerCalendar extends EntityPathBase<PhotographerCalendar> {

    private static final long serialVersionUID = -1660701277L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPhotographerCalendar photographerCalendar = new QPhotographerCalendar("photographerCalendar");

    public final ceos.phototoground.global.entity.QBaseTimeEntity _super = new ceos.phototoground.global.entity.QBaseTimeEntity(this);

    public final QCalendar calendar;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ceos.phototoground.photographer.domain.QPhotographer photographer;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPhotographerCalendar(String variable) {
        this(PhotographerCalendar.class, forVariable(variable), INITS);
    }

    public QPhotographerCalendar(Path<? extends PhotographerCalendar> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPhotographerCalendar(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPhotographerCalendar(PathMetadata metadata, PathInits inits) {
        this(PhotographerCalendar.class, metadata, inits);
    }

    public QPhotographerCalendar(Class<? extends PhotographerCalendar> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.calendar = inits.isInitialized("calendar") ? new QCalendar(forProperty("calendar")) : null;
        this.photographer = inits.isInitialized("photographer") ? new ceos.phototoground.photographer.domain.QPhotographer(forProperty("photographer"), inits.get("photographer")) : null;
    }

}

