package com.cogent.admin.dto.files;

import com.cogent.admin.feign.dto.response.fileserver.FileServerResponseDTO;

import java.util.Arrays;
import java.util.List;

/**
 * @author smriti on 2019-10-18
 */
public class FileServerResponseUtils {

    public static List<FileServerResponseDTO> getFileServerResponse() {
        return Arrays.asList(FileServerResponseDTO.builder()
                .fileUri("http://localhost:8081/files/test-cogent/test.png")
                .fileType("image/png")
                .fileSize(100L)
                .build());
    }

    public static List<FileServerResponseDTO> getUpdatedFileServerResponse() {
        return Arrays.asList(FileServerResponseDTO.builder()
                .fileUri("http://localhost:8081/files/test-cogent/testUpdate.png")
                .fileType("image/png")
                .fileSize(100L)
                .build());
    }
}
