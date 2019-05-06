package api.iti0208.service;

import api.iti0208.config.FileStorageProperties;
import api.iti0208.exception.BadRequestException;
import api.iti0208.exception.FileStorageException;
import api.iti0208.exception.PageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    private final Set<String> whitelistedFileExtensions;

    public FileStorageService(FileStorageProperties fileStorageProperties) {
        // Full path to uploads directory
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        Set<String> whitelist = new HashSet<>(Arrays.asList("jpg", "png", "pdf", "txt",
                "doc", "docx", "xls", "xlsx", "rtf", "jpeg", "tiff", "ppt"));
        this.whitelistedFileExtensions = Collections.unmodifiableSet(whitelist);

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String newFileName = null;

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            // Check if file extension is whitelisted
            String[] fileNameExtension = fileName.split("\\.");
            if (!whitelistedFileExtensions.contains(
                    fileNameExtension[fileNameExtension.length - 1].toLowerCase())) {
                throw new BadRequestException("Unsupported file type!");
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);

            // if file with the name exists:
            // add an unique number to the end of the filename, otherwise file would be overwritten
            int randomNum = 0;
            while (Files.exists(targetLocation)) {
                String[] fileNameParts = fileName.split("\\.");
                newFileName = fileNameParts[0] + randomNum + "." + fileNameParts[fileNameParts.length - 1];
                randomNum += 1;
                targetLocation = this.fileStorageLocation.resolve(newFileName);
            }
            if (newFileName != null) {
                fileName = newFileName;
            }

            // only if something went wrong, will the REPLACE_EXISTING tactic be used
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new PageNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new PageNotFoundException("File not found " + fileName, ex);
        }
    }
}