package com.vvs.hypeshop.Repository;

import com.vvs.hypeshop.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
