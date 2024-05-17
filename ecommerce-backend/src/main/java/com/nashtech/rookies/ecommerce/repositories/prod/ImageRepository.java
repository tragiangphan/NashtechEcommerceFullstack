package com.nashtech.rookies.ecommerce.repositories.prod;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nashtech.rookies.ecommerce.models.prod.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
