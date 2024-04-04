import { Url } from "url";

export interface DateType {
  year: number;
  month: string;
  day: number;
}

type TempCommentType = {
  artist: string;
  comment: string;
};

export interface commentType {
  comments: TempCommentType[];
}

export type AnalyzedStateType = {
  title: string;
  process: string;
  songId: number;
};

type GenreType = {
  type: string;
  correlation: number;
};

type AtmosphereType = {
  type: string;
  correlation: number;
};

type SimilaritiesType = {
  songId: number;
  startTime: number;
  endTime: number;
  similarityPercent: number;
  songFile: Url;
};

export type AnalyzedResultType = {
  songTitle: string;
  songFile: Url;
  songMfcc: Url;
  songSpectrum: Url;
  genre: GenreType[];
  atmosphere: AtmosphereType[];
  similarities: SimilaritiesType[];
};

type MusicType = {
  musicId: number;
  title: string;
  genre: string;
  atmosphere: number;
  playCount: number;
  artistId: number;
  artistName: string;
  duration: number;
};

type ArtistType = {
  id: number;
  name: string;
  profileImagePath: string;
};

export type SearchType = {
  musics: MusicType[];
  artist: ArtistType[];
};

export type SearchParmasType = {
  keyword: string | null;
  genre: string | null;
  atmosphere: string | null;
  bpm: string | null;
  sort: string | null;
};

export type EmotionType = {
  moved: number;
  like: number;
  excited: number;
  energized: number;
  funny: number;
  sad: number;
};
