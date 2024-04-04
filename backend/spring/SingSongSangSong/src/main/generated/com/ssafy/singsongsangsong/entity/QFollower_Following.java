package com.ssafy.singsongsangsong.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFollower_Following is a Querydsl query type for Follower_Following
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFollower_Following extends EntityPathBase<Follower_Following> {

    private static final long serialVersionUID = 1812254164L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFollower_Following follower_Following = new QFollower_Following("follower_Following");

    public final QArtist from;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QArtist to;

    public QFollower_Following(String variable) {
        this(Follower_Following.class, forVariable(variable), INITS);
    }

    public QFollower_Following(Path<? extends Follower_Following> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFollower_Following(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFollower_Following(PathMetadata metadata, PathInits inits) {
        this(Follower_Following.class, metadata, inits);
    }

    public QFollower_Following(Class<? extends Follower_Following> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.from = inits.isInitialized("from") ? new QArtist(forProperty("from"), inits.get("from")) : null;
        this.to = inits.isInitialized("to") ? new QArtist(forProperty("to"), inits.get("to")) : null;
    }

}

