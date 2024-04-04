package com.ssafy.singsongsangsong.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStructure is a Querydsl query type for Structure
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStructure extends EntityPathBase<Structure> {

    private static final long serialVersionUID = -1226923345L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStructure structure = new QStructure("structure");

    public final NumberPath<Integer> endTime = createNumber("endTime", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath label = createString("label");

    public final QSong song;

    public final NumberPath<Integer> startTime = createNumber("startTime", Integer.class);

    public QStructure(String variable) {
        this(Structure.class, forVariable(variable), INITS);
    }

    public QStructure(Path<? extends Structure> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStructure(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStructure(PathMetadata metadata, PathInits inits) {
        this(Structure.class, metadata, inits);
    }

    public QStructure(Class<? extends Structure> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.song = inits.isInitialized("song") ? new QSong(forProperty("song"), inits.get("song")) : null;
    }

}

