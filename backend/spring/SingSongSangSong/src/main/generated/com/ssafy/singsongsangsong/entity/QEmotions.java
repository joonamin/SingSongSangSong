package com.ssafy.singsongsangsong.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmotions is a Querydsl query type for Emotions
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmotions extends EntityPathBase<Emotions> {

    private static final long serialVersionUID = 1127800508L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEmotions emotions = new QEmotions("emotions");

    public final QArtist artist;

    public final EnumPath<com.ssafy.singsongsangsong.constants.EmotionsConstants> emotionType = createEnum("emotionType", com.ssafy.singsongsangsong.constants.EmotionsConstants.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSong song;

    public QEmotions(String variable) {
        this(Emotions.class, forVariable(variable), INITS);
    }

    public QEmotions(Path<? extends Emotions> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEmotions(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEmotions(PathMetadata metadata, PathInits inits) {
        this(Emotions.class, metadata, inits);
    }

    public QEmotions(Class<? extends Emotions> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.artist = inits.isInitialized("artist") ? new QArtist(forProperty("artist"), inits.get("artist")) : null;
        this.song = inits.isInitialized("song") ? new QSong(forProperty("song"), inits.get("song")) : null;
    }

}

