package com.Nickode;

import com.Nickode.entity.NiCloudFile;
import com.Nickode.entity.NiCloudUser;
import com.Nickode.repository.NiCloudFileRepository;
import com.Nickode.repository.NiCloudUserRepository;
import com.Nickode.service.NiCloudFileService;
import com.Nickode.service.NiCloudUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;

@Disabled
public class NiCloudServicesTests {
    private static final String authenticationGetId = "nikolaiId";
    private static final String authenticationGetName = "Nikolai";
    private static final String authenticationGetPassword = "nikolaiPassword";
    private static final NiCloudFile niCloudFile = new NiCloudFile();
    private static final NiCloudUser niCloudUser = new NiCloudUser(authenticationGetId, authenticationGetName, authenticationGetPassword, List.of(niCloudFile));

    private static final NiCloudFileRepository niCloudFileRepositoryMock = mock(NiCloudFileRepository.class);
    private static final NiCloudUserRepository niCloudUserRepositoryMock = mock(NiCloudUserRepository.class);
    private static final NiCloudFileService niCloudFileService = new NiCloudFileService(niCloudFileRepositoryMock, niCloudUserRepositoryMock);
    private static final NiCloudUserService niCloudUserService = new NiCloudUserService(niCloudUserRepositoryMock);

    private static final MultipartFile multipartFile = new MockMultipartFile("MultipartFileName", "MultipartFileTestOriginalFilename", "byte",
            "Test Multipart NiCloudFile".getBytes());

    @Test
    public void create() {
        NiCloudFileRepository niCloudFileRepositoryMock = mock(NiCloudFileRepository.class);
        NiCloudFileService niCloudFileService = new NiCloudFileService(niCloudFileRepositoryMock, mock(NiCloudUserRepository.class));
        Mockito.when(niCloudFileRepositoryMock.save(niCloudFile)).thenReturn(niCloudFile);
        Assertions.assertDoesNotThrow(() -> niCloudFileService.create(multipartFile.getName(), multipartFile, authenticationGetName));
    }

    @Test
    public void update() {
        Mockito.when(niCloudFileRepositoryMock.findByUser_usernameAndFilename(authenticationGetName, multipartFile.getName())).thenReturn(Optional.of(niCloudFile));
        Assertions.assertDoesNotThrow(() -> niCloudFileService.update(multipartFile.getName(), "Nikolas", authenticationGetName));
    }

    @Test
    public void delete() {
        Assertions.assertDoesNotThrow(() -> niCloudFileService.delete(multipartFile.getName(), authenticationGetName));
    }

    @Test
    public void deleteAll() {
        Assertions.assertDoesNotThrow(() -> niCloudFileService.deleteAll());
    }

    @Test
    public void findFile() {
        Mockito.when(niCloudFileRepositoryMock.findByUser_usernameAndFilename(authenticationGetName, multipartFile.getName())).thenReturn(Optional.of(niCloudFile));
        Assertions.assertDoesNotThrow(() -> niCloudFileService.findFile(multipartFile.getName(), authenticationGetName));
    }

    @Test
    public void getFileNames() {
        Mockito.when(niCloudFileRepositoryMock.findByFilenameIsNotNull()).thenReturn(List.of(niCloudFile));
        Assertions.assertDoesNotThrow(() -> niCloudFileService.getAllFilesNames());
    }

    @Test
    public void loadUserByUsername() {
        Mockito.when(niCloudUserRepositoryMock.findByUsername(authenticationGetName)).thenReturn(Optional.of(niCloudUser));
        Assertions.assertDoesNotThrow(() -> niCloudUserService.loadUserByUsername(authenticationGetName));
    }
}
