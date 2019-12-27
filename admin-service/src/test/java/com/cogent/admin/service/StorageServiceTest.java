package com.cogent.admin.service;

import com.cogent.admin.configuration.StorageProperties;
import com.cogent.admin.exception.FileStorageException;
import com.cogent.admin.exception.FileNotFoundException;
import com.cogent.admin.service.impl.StorageServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * @author smriti on 2019-08-28
 */
@RunWith(MockitoJUnitRunner.class)
public class StorageServiceTest {

    @InjectMocks
    private StorageServiceImpl storageService;

    @Mock
    private StorageProperties storageProperties;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private MockMultipartFile file = new MockMultipartFile("files"
            , "hello.png", MediaType.IMAGE_PNG_VALUE, "Hello, World!".getBytes());

    @Before
    public void setup() {

    }

    public void Save_Image_Test() throws Exception {

        Should_Upload_Image_To_Upload_path();

        Should_Throw_Exception_When_Resource_Is_Not_Found();

        Should_Match_File_Content_Type_And_File_Size();

        Should_Match_File_Content_Type_And_File_Size();

        Should_Throw_Exception_While_FileName_Contains_Unusual_Sequence();
    }

    @Test
    public void Should_Upload_Image_To_Upload_path() {
        String uploaded = fileManager();
        assertThat(folder.getRoot().toPath().resolve(uploaded)).exists();
    }

    public String fileManager() {
        String temporalPath = temporalPath();
        return storageService.store(file, temporalPath);
    }

    public Path pathRessolver(String uploaded) {
        return folder.getRoot().toPath().resolve(uploaded);
    }

    public String temporalPath(){
        return folder.getRoot().toPath().toString();
    }

    @Test(expected = FileNotFoundException.class)
    public void Should_Throw_Exception_When_Resource_Is_Not_Found() {
        String temporalPath = temporalPath();

        String filename = storageService.store(file, temporalPath);

        storageService.loadAsResource(temporalPath + "/test/", filename);
    }

    @Test
    public void Should_Match_File_Content_Type_And_File_Size() throws IOException {

        String uploaded = fileManager();
        Path uploadedPath = pathRessolver(uploaded);

        File f = new File(uploadedPath.toString());
        FileInputStream input = new FileInputStream(f);

        MultipartFile multipartFile = new MockMultipartFile("file",
                f.getName(), new MimetypesFileTypeMap().getContentType(f), input);

        assertEquals(file.getContentType(), multipartFile.getContentType());

        assertEquals(file.getSize(), multipartFile.getSize());
    }

    @Test(expected = FileStorageException.class)
    public void Should_Throw_Exception_While_FileName_Contains_Unusual_Sequence() {

        file = new MockMultipartFile("files"
                , "hello..test.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        String temporalPath = temporalPath();

        storageService.store(file, temporalPath);
    }
}
