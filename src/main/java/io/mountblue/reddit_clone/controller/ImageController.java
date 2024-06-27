package io.mountblue.reddit_clone.controller;

import io.mountblue.reddit_clone.service.FileLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {
    private FileLocationService fileLocationService;

    @Autowired
    public ImageController(FileLocationService fileLocationService) {
        this.fileLocationService = fileLocationService;
    }

    @GetMapping(value = "/image/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    FileSystemResource downloadImage(@PathVariable int imageId) throws Exception {
        return fileLocationService.find(imageId);
    }
}
