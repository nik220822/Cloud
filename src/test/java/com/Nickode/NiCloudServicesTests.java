package com.Nickode;

import com.Nickode.entity.NiCloudFile;
import com.Nickode.entity.NiCloudUser;
import com.Nickode.repository.NiCloudFileRepository;
import com.Nickode.repository.NiCloudUserRepository;
import com.Nickode.security.NiCloudAuthRequest;
import com.Nickode.security.NiCloudJSONwebTokenManager;
import com.Nickode.service.NiCloudFileService;
import com.Nickode.service.NiCloudUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;

public class NiCloudServicesTests {
    private static final String authenticationGetName = "Nikolai";
    private static final String authenticationGetPassword = "NikolaiPassword";
    private static final NiCloudAuthRequest niCloudAuthRequest = new NiCloudAuthRequest(authenticationGetName, authenticationGetPassword);
    private static final NiCloudFileRepository niCloudFileRepositoryMock = mock(NiCloudFileRepository.class);
    private static final NiCloudUserRepository niCloudUserRepositoryMock = mock(NiCloudUserRepository.class);
    private static final NiCloudFileService niCloudFileService = new NiCloudFileService(niCloudFileRepositoryMock);
    private static final NiCloudUserService niCloudUserService = new NiCloudUserService(mock(NiCloudJSONwebTokenManager.class), mock(AuthenticationManager.class), mock(HttpServletRequest.class), mock(NiCloudUserRepository.class));
    private static final String testToken = "test token";
    private static final MultipartFile multipartFile = new MockMultipartFile("MultipartFileName", "MultipartFileTestOriginalFilename", "byte",
            new String("Test Multipart NiCloudFile").getBytes());
    private static final NiCloudFile niCloudFile = new NiCloudFile();
    private static final NiCloudUser niCloudUser = new NiCloudUser();
    Optional<NiCloudFile> optionalFile = Optional.of(niCloudFile);
    List<NiCloudFile> listOfNiCloudFiles = List.of(niCloudFile);

    @Test
    public void create() {
        Mockito.when(niCloudFileRepositoryMock.save(niCloudFile)).thenReturn(niCloudFile);
        Assertions.assertDoesNotThrow(() -> niCloudFileService.create(multipartFile.getName(), multipartFile, authenticationGetName));
    }

    @Test
    public void update() {
        Mockito.when(niCloudFileRepositoryMock.findByUserAndFilename(authenticationGetName, multipartFile.getName())).thenReturn(optionalFile);
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
        Mockito.when(niCloudFileRepositoryMock.findByUserAndFilename(authenticationGetName, multipartFile.getName())).thenReturn(optionalFile);
        Assertions.assertDoesNotThrow(() -> niCloudFileService.findFile(multipartFile.getName(), authenticationGetName));
    }

    @Test
    public void getFileNames() {
        Mockito.when(niCloudFileRepositoryMock.findByFilenameIsNotNull()).thenReturn(listOfNiCloudFiles);
        Assertions.assertDoesNotThrow(() -> niCloudFileService.getAllFilesNames());
    }

    @Test
    public void loadUserByUsername() {
        Mockito.when(niCloudUserRepositoryMock.findByUsername(authenticationGetName)).thenReturn(Optional.of(niCloudUser));
        Assertions.assertDoesNotThrow(() -> niCloudUserService.loadUserByUsername(authenticationGetName));
    }

    @Test
    public void login() {
        Assertions.assertDoesNotThrow(() -> niCloudUserService.login(niCloudAuthRequest));
    }

    @Test
    public void logout() {
        Assertions.assertDoesNotThrow(() -> niCloudUserService.logout(testToken));
    }
}
