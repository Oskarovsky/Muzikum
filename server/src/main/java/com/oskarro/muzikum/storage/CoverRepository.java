package com.oskarro.muzikum.storage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoverRepository extends JpaRepository<Cover, Integer> {

    Boolean existsByName(String fileName);

    Cover findByName(String fileName);

    Cover findByUrl(String url);

    Optional<Cover> findById(Integer coverId);

}
