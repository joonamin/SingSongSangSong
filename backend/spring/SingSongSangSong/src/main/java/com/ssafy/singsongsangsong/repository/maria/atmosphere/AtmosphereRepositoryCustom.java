package com.ssafy.singsongsangsong.repository.maria.atmosphere;

import java.util.List;
import java.util.Optional;

import com.ssafy.singsongsangsong.entity.Atmosphere;
import com.ssafy.singsongsangsong.entity.Song;

public interface AtmosphereRepositoryCustom {
	List<Song> atmosphereFilterList(List<Long> songIdList,String requestAtmosphere);
	List<Atmosphere> getAtmosphereBySongId(Long songId);

	List<Atmosphere> findBySongId(Long songId, int limit);

	Optional<Atmosphere> getFirstAtmosphereBySongId(Long songId);
}
