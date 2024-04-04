package com.ssafy.singsongsangsong.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSong is a Querydsl query type for Song
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSong extends EntityPathBase<Song> {

    private static final long serialVersionUID = -1753798055L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSong song = new QSong("song");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final QFile albumImage;

    public final QArtist artist;

    public final NumberPath<Integer> bpm = createNumber("bpm", Integer.class);

    public final StringPath chord = createString("chord");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath customGenre = createString("customGenre");

    public final NumberPath<Integer> downloadCount = createNumber("downloadCount", Integer.class);

    public final NumberPath<Integer> duration = createNumber("duration", Integer.class);

    public final NumberPath<Integer> energizedEmotionCount = createNumber("energizedEmotionCount", Integer.class);

    public final NumberPath<Integer> excitedEmotionCount = createNumber("excitedEmotionCount", Integer.class);

    public final NumberPath<Integer> funnyEmotionCount = createNumber("funnyEmotionCount", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isAnalyzed = createBoolean("isAnalyzed");

    public final BooleanPath isPublished = createBoolean("isPublished");

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final NumberPath<Integer> likeEmotionCount = createNumber("likeEmotionCount", Integer.class);

    public final StringPath lyrics = createString("lyrics");

    public final QFile mfccImage;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final NumberPath<Integer> movedEmotionCount = createNumber("movedEmotionCount", Integer.class);

    public final StringPath musicFileName = createString("musicFileName");

    public final NumberPath<Integer> playCount = createNumber("playCount", Integer.class);

    public final NumberPath<Integer> sadEmotionCount = createNumber("sadEmotionCount", Integer.class);

    public final StringPath songDescription = createString("songDescription");

    public final QFile spectrumImage;

    public final StringPath themes = createString("themes");

    public final StringPath title = createString("title");

    public final NumberPath<Integer> weeklyDownloadCount = createNumber("weeklyDownloadCount", Integer.class);

    public final NumberPath<Integer> weeklyLikeCount = createNumber("weeklyLikeCount", Integer.class);

    public final NumberPath<Integer> weeklyPlayCount = createNumber("weeklyPlayCount", Integer.class);

    public QSong(String variable) {
        this(Song.class, forVariable(variable), INITS);
    }

    public QSong(Path<? extends Song> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSong(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSong(PathMetadata metadata, PathInits inits) {
        this(Song.class, metadata, inits);
    }

    public QSong(Class<? extends Song> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.albumImage = inits.isInitialized("albumImage") ? new QFile(forProperty("albumImage")) : null;
        this.artist = inits.isInitialized("artist") ? new QArtist(forProperty("artist"), inits.get("artist")) : null;
        this.mfccImage = inits.isInitialized("mfccImage") ? new QFile(forProperty("mfccImage")) : null;
        this.spectrumImage = inits.isInitialized("spectrumImage") ? new QFile(forProperty("spectrumImage")) : null;
    }

}

