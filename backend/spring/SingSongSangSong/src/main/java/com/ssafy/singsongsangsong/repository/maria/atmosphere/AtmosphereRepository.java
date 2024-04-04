package com.ssafy.singsongsangsong.repository.maria.atmosphere;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.singsongsangsong.entity.Atmosphere;

@Repository
public interface AtmosphereRepository extends JpaRepository<Atmosphere, Long>, AtmosphereRepositoryCustom {
}
