package com.ssafy.singsongsangsong.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAtmosphere is a Querydsl query type for Atmosphere
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAtmosphere extends EntityPathBase<Atmosphere> {

    private static final long serialVersionUID = -837841050L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAtmosphere atmosphere1 = new QAtmosphere("atmosphere1");

    public final StringPath atmosphere = createString("atmosphere");

    public final NumberPath<Double> correlation = createNumber("correlation", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSong song;

    public QAtmosphere(String variable) {
        this(Atmosphere.class, forVariable(variable), INITS);
    }

    public QAtmosphere(Path<? extends Atmosphere> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAtmosphere(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAtmosphere(PathMetadata metadata, PathInits inits) {
        this(Atmosphere.class, metadata, inits);
    }

    public QAtmosphere(Class<? extends Atmosphere> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.song = inits.isInitialized("song") ? new QSong(forProperty("song"), inits.get("song")) : null;
    }

}

