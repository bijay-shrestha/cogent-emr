package com.cogent.admin.feign.service;

import com.cogent.admin.feign.configuration.FeignMultipartConfiguration;
import com.cogent.admin.feign.dto.response.fileserver.FileServerResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.cogent.admin.constants.MicroServiceConstants.API_V1;
import static com.cogent.admin.constants.MicroServiceConstants.FileServerMicroService.*;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

/**
 * @author smriti on 2019-10-01
 */
@FeignClient(name = BASE_NAME, configuration = FeignMultipartConfiguration.class)
@RequestMapping(API_V1 + BASE_FILE)
public interface FileServerService {

    @RequestMapping(value = UPLOAD, method = RequestMethod.POST, consumes = MULTIPART_FORM_DATA_VALUE)
    List<FileServerResponseDTO> uploadFiles(@RequestPart(value = "file") MultipartFile[] file,
                                            @RequestParam("subDirectoryLocation") String subDirectoryLocation);
}

