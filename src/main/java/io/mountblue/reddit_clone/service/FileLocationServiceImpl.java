package io.mountblue.reddit_clone.service;

import io.mountblue.reddit_clone.dao.FileSystemRepository;
import io.mountblue.reddit_clone.dao.ImageRepository;
import io.mountblue.reddit_clone.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FileLocationServiceImpl implements FileLocationService {
    private FileSystemRepository fileSystemRepository;
    private ImageRepository imageRepository;

    @Autowired
    public FileLocationServiceImpl(FileSystemRepository fileSystemRepository, ImageRepository imageRepository) {
        this.fileSystemRepository = fileSystemRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public Image save(byte[] bytes, String imageName) throws Exception {
        String location = fileSystemRepository.save(bytes, imageName);
        return imageRepository.save(new Image(imageName, location));
    }

    @Override
    public FileSystemResource find(int imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return fileSystemRepository.findInFileSystem(image.getLocation());
    }
}
