package ceos.phototoground.domain.reservation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReservation is a Querydsl query type for Reservation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservation extends EntityPathBase<Reservation> {

    private static final long serialVersionUID = 22086475L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReservation reservation = new QReservation("reservation");

    public final ceos.phototoground.global.entity.QBaseTimeEntity _super = new ceos.phototoground.global.entity.QBaseTimeEntity(this);

    public final StringPath canceledReason = createString("canceledReason");

    public final StringPath chatUrl = createString("chatUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final ceos.phototoground.domain.customer.entity.QCustomer customer;

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath message = createString("message");

    public final ceos.phototoground.domain.photographer.entity.QPhotographer photographer;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath requirement = createString("requirement");

    public final NumberPath<Integer> reserveNum = createNumber("reserveNum", Integer.class);

    public final BooleanPath reviewComplete = createBoolean("reviewComplete");

    public final TimePath<java.time.LocalTime> startTime = createTime("startTime", java.time.LocalTime.class);

    public final EnumPath<Status> status = createEnum("status", Status.class);

    public final ceos.phototoground.domain.univ.entity.QUniv univ;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QReservation(String variable) {
        this(Reservation.class, forVariable(variable), INITS);
    }

    public QReservation(Path<? extends Reservation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReservation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReservation(PathMetadata metadata, PathInits inits) {
        this(Reservation.class, metadata, inits);
    }

    public QReservation(Class<? extends Reservation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new ceos.phototoground.domain.customer.entity.QCustomer(forProperty("customer")) : null;
        this.photographer = inits.isInitialized("photographer") ? new ceos.phototoground.domain.photographer.entity.QPhotographer(forProperty("photographer"), inits.get("photographer")) : null;
        this.univ = inits.isInitialized("univ") ? new ceos.phototoground.domain.univ.entity.QUniv(forProperty("univ")) : null;
    }

}

