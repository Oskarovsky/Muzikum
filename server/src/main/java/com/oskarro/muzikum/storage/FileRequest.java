package com.oskarro.muzikum.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class FileRequest {

    private MultipartFile file;
    private String username;
    private String destination;

}
