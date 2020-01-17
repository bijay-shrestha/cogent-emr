package com.cogent.admin.controller.v1;

import com.cogent.admin.service.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cogent.admin.constants.StringConstant.BACKWARD_SLASH;
import static com.cogent.admin.constants.SwaggerConstants.FileConstant.BASE_API_VALUE;
import static com.cogent.admin.constants.SwaggerConstants.FileConstant.DOWNLOAD_OPERATION;
import static com.cogent.admin.constants.WebResourceKeyConstants.API_V1;
import static com.cogent.admin.constants.WebResourceKeyConstants.FILE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = API_V1)
@Api(BASE_API_VALUE)
public class FileController {

    private final StorageService storageService;

    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping(FILE)
    @ApiOperation(DOWNLOAD_OPERATION)
    public ResponseEntity<Resource> downloadFile(@PathVariable String subDirectoryLocation,
                                                 @PathVariable String filename) {

        Resource file = storageService.loadAsResource(subDirectoryLocation, filename);

        return ok().header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + BACKWARD_SLASH + file.getFilename() + BACKWARD_SLASH)
                .body(file);
    }

}
