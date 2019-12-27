package com.cogent.admin.service.impl;

import com.cogent.admin.configuration.StorageProperties;
import com.cogent.admin.dto.response.files.FileUploadResponseDTO;
import com.cogent.admin.exception.FileStorageException;
import com.cogent.admin.exception.FileNotFoundException;
import com.cogent.admin.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.cogent.admin.constants.ErrorMessageConstants.FileServiceMessages.*;
import static com.cogent.admin.constants.FileConstants.FILE_BASE_PATH;
import static com.cogent.admin.constants.StringConstant.FORWARD_SLASH;
import static com.cogent.admin.constants.StringConstant.HYPHEN;
import static com.cogent.admin.log.constants.StorageLog.*;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.StringUtil.urlConverter;

/**
 * @author smriti on 2019-08-27
 */
@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    private Path rootLocation;

    private final StorageProperties storageProperties;

    public StorageServiceImpl(StorageProperties storageProperties) {
        this.rootLocation = Paths.get(storageProperties.getLocation());
        this.storageProperties = storageProperties;
    }

    @Override
    public Resource loadAsResource(String subDirectoryLocation, String fileName) {
        Long startTime = getTimeInMillisecondsFromLocalDate();
        log.info(LOADING_FILE_PROCESS_STARTED);

        try {
            String path = urlConverter(subDirectoryLocation, HYPHEN, FORWARD_SLASH) + FORWARD_SLASH;
            Path file = load(path + fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                log.info(LOADING_FILE_PROCESS_COMPLETED, getDifferenceBetweenTwoTime(startTime));
                return resource;
            } else {
                throw new FileNotFoundException(
                        INVALID_FILE_TYPE_MESSAGE + fileName, INVALID_FILE_TYPE_MESSAGE + fileName);
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException(
                    INVALID_FILE_TYPE_MESSAGE + fileName, INVALID_FILE_TYPE_MESSAGE + fileName + e);
        }
    }

    private FileUploadResponseDTO uploadFile(MultipartFile file, String subDirectoryLocation) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPLOADING_FILE_PROCESS_STARTED);

        String fileName = store(file, subDirectoryLocation);

        String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(FILE_BASE_PATH)
                .path(urlConverter(subDirectoryLocation, FORWARD_SLASH, HYPHEN))
                .path(FORWARD_SLASH)
                .path(fileName)
                .toUriString();

        FileUploadResponseDTO responseDTO = FileUploadResponseDTO.builder()
                .fileUri(fileUri)
                .fileType(file.getContentType())
                .fileSize(file.getSize()).build();

        log.info(UPLOADING_FILE_PROCESS_COMPLETED, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;
    }

    @Override
    public List<FileUploadResponseDTO> uploadFiles(MultipartFile[] files,
                                                   String subDirectoryLocation) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPLOADING_FILE_PROCESS_STARTED);

        List<FileUploadResponseDTO> fileUploadResponseDTOS = Arrays.stream(files)
                .map(file -> uploadFile(file, subDirectoryLocation))
                .collect(Collectors.toList());

        log.info(UPLOADING_FILE_PROCESS_COMPLETED, getDifferenceBetweenTwoTime(startTime));

        return fileUploadResponseDTOS;
    }

    @Override
    public String store(MultipartFile file, String subDirectory) {

        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (file.isEmpty())
                throw new FileStorageException(FILES_EMPTY_MESSAGE + filename, FILES_EMPTY_MESSAGE + filename);

            if (filename.contains(".."))
                throw new FileStorageException(INVALID_FILE_SEQUENCE, INVALID_FILE_SEQUENCE);

            resolvePath(subDirectory);

            Files.copy(file.getInputStream(),
                    this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);

            return filename;
        } catch (IOException exception) {
            throw new FileStorageException(FILE_EXCEPTION, FILE_EXCEPTION + exception);
        }
    }

    private void resolvePath(String subDirectoryLocation) throws IOException {
        Path path = Paths.get(storageProperties.getLocation() +
                FORWARD_SLASH + subDirectoryLocation + FORWARD_SLASH);

        /*test
        Path path = Paths.get(subDirectoryLocation);
        **/
        Files.createDirectories(path);
        this.rootLocation = path.toAbsolutePath().normalize();
    }

    private Path load(String filename) {
        return rootLocation.resolve(filename);
    }

}
