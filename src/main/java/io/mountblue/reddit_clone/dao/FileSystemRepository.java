package io.mountblue.reddit_clone.dao;

import org.springframework.core.io.FileSystemResource;

public interface FileSystemRepository {
    String save(byte[] content, String imageName) throws Exception;
    FileSystemResource findInFileSystem(String location);
}
