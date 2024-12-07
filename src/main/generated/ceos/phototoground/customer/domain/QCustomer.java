package ceos.phototoground.customer.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCustomer is a Querydsl query type for Customer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomer extends EntityPathBase<Customer> {

    private static final long serialVersionUID = -1774602822L;

    public static final QCustomer customer = new QCustomer("customer");

    public final ceos.phototoground.global.entity.QBaseTimeEntity _super = new ceos.phototoground.global.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final EnumPath<ceos.phototoground.photographer.domain.Gender> gender = createEnum("gender", ceos.phototoground.photographer.domain.Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<ceos.phototoground.photographer.domain.MyUniv> myUniv = createEnum("myUniv", ceos.phototoground.photographer.domain.MyUniv.class);

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final EnumPath<UserRole> role = createEnum("role", UserRole.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCustomer(String variable) {
        super(Customer.class, forVariable(variable));
    }

    public QCustomer(Path<? extends Customer> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomer(PathMetadata metadata) {
        super(Customer.class, metadata);
    }

}

