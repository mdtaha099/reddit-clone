package io.mountblue.reddit_clone.service;

import io.mountblue.reddit_clone.entity.Image;
import org.springframework.core.io.FileSystemResource;

public interface FileLocationService {
    Image save(byte[] bytes, String imageName) throws Exception;

    FileSystemResource find(int imageId);
}
