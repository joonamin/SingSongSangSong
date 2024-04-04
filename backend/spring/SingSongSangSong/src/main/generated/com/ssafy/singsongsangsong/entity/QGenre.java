package com.ssafy.singsongsangsong.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGenre is a Querydsl query type for Genre
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGenre extends EntityPathBase<Genre> {

    private static final long serialVersionUID = 1455455423L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGenre genre = new QGenre("genre");

    public final NumberPath<Double> correlation = createNumber("correlation", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath mainCategory = createString("mainCategory");

    public final QSong song;

    public final StringPath subCategory = createString("subCategory");

    public QGenre(String variable) {
        this(Genre.class, forVariable(variable), INITS);
    }

    public QGenre(Path<? extends Genre> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGenre(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGenre(PathMetadata metadata, PathInits inits) {
        this(Genre.class, metadata, inits);
    }

    public QGenre(Class<? extends Genre> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.song = inits.isInitialized("song") ? new QSong(forProperty("song"), inits.get("song")) : null;
    }

}

