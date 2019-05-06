package api.iti0208.unit.service;

import api.iti0208.config.FileStorageProperties;
import api.iti0208.exception.BadRequestException;
import api.iti0208.exception.FileStorageException;
import api.iti0208.exception.PageNotFoundException;
import api.iti0208.service.FileStorageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileStorageServiceTest {

    @MockBean(answer = Answers.RETURNS_DEEP_STUBS)
    private FileStorageProperties fsp;

    @MockBean
    private FileStorageService fss;

    @Before
    public void setUp() {
        Mockito.when(fsp.getUploadDir())
               .thenReturn("./src/test/resources/cache");

        this.fss = new FileStorageService(fsp);
    }

    @Test
    public void testUploadNormalFile() throws Exception {
        String originalName = "testText";
        MultipartFile multipartFile = new MockMultipartFile("testText.txt",
                "testText.txt",
                null,
                new FileInputStream(new File("./src/test/resources/testText.txt")));

        assertTrue(fss.storeFile(multipartFile).contains(originalName));
    }

    @Test(expected = BadRequestException.class)
    public void testUploadDisapprovedContent() throws Exception {
        MultipartFile multipartFile = new MockMultipartFile("executable.exe",
                "executable.exe",
                null,
                new FileInputStream(new File("./src/test/resources/executable.exe")));

        fss.storeFile(multipartFile);
    }

    @Test(expected = FileStorageException.class)
    public void testUploadContentBrokenPath() throws Exception {
        MultipartFile multipartFile = new MockMultipartFile("testText.txt",
                "/../testText.txt",
                null,
                new FileInputStream(new File("./src/test/resources/testText.txt")));

        fss.storeFile(multipartFile);
    }

    @Test
    public void testDownloadContent() throws Exception {
        Resource resource = fss.loadFileAsResource("testText.txt");

        assertEquals("testText.txt", resource.getFile().getName());
    }

    @Test(expected = PageNotFoundException.class)
    public void testDownloadContentDoesNotExist() {
        fss.loadFileAsResource("testedText.txt");
    }
}
