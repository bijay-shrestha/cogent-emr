package com.cogent.admin.dto.files;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

/**
 * @author smriti on 2019-08-28
 */
public class MultipartFileUtils {

    public static MockMultipartFile getUpdatedMultipartFile() {
        return new MockMultipartFile("files",
                "image.png", MediaType.IMAGE_PNG_VALUE, "Hello!".getBytes());
    }

    public static MockMultipartFile getMockMultipartFile() {
        return new MockMultipartFile("data", "dummy.png",
                MediaType.IMAGE_PNG_VALUE, "Some dataset...".getBytes());
    }
}
