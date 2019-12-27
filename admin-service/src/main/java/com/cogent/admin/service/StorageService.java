package com.cogent.admin.service;

import com.cogent.admin.dto.response.files.FileUploadResponseDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author smriti on 2019-08-27
 */
public interface StorageService {

    Resource loadAsResource(String path, String fileName);

    List<FileUploadResponseDTO> uploadFiles(MultipartFile[] files, String subDirectoryLocation);

    String store(MultipartFile file, String subDirectory );
}
