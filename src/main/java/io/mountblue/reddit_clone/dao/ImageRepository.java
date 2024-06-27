package io.mountblue.reddit_clone.dao;

import io.mountblue.reddit_clone.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
