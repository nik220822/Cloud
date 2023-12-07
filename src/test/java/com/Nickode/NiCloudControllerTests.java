package com.Nickode;

import com.Nickode.controller.NiCloudController;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public class NiCloudControllerTests {
    private static final String login = "Nikolai";
    private static final String password = "NikolaiPassword";
    private static final String secret = "nikolaiSecret";
    private final NiCloudJSONwebTokenManager niCloudJSONwebTokenManager = new NiCloudJSONwebTokenManager(secret);
    private final NiCloudController niCloudController = new NiCloudController(Mockito.mock(AuthenticationManager.class), Mockito.mock(NiCloudUserService.class), Mockito.mock(NiCloudJSONwebTokenManager.class), Mockito.mock(NiCloudFileService.class));
    private static final MultipartFile multipartFile = new MockMultipartFile("MultipartFileTestName", "MultipartFileTestOriginalFilename", "byte",
            new String("Test Multipart NiCloudFile").getBytes());

    @Test
    public void loginTest() {
        NiCloudAuthRequest niCloudAuthRequest = new NiCloudAuthRequest(login, password);
        Assertions.assertDoesNotThrow(() -> niCloudController.login(niCloudAuthRequest));
    }

    @Test
    public void logoutTest() {
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(httpServletRequest.getHeader("auth-token")).thenReturn("Bearer test token");
        niCloudController.logout(httpServletRequest);
        Assertions.assertTrue(niCloudJSONwebTokenManager.getBlackTokens().contains("test token"));
    }

    @Test
    public void getFileNamesTest() {
        Authentication authenticationMock = Mockito.mock(Authentication.class);
        Mockito.when(authenticationMock.getName()).thenReturn(login);
        Assertions.assertDoesNotThrow(() -> niCloudController.getFileNames(authenticationMock));
    }

    @Test
    public void deleteFileTest() {
        Authentication authenticationMock = Mockito.mock(Authentication.class);
        Mockito.when(authenticationMock.getName()).thenReturn(login);
        Assertions.assertDoesNotThrow(() -> niCloudController.deleteFile("file", authenticationMock));
    }

    @Test
    public void downloadFileTest() {
        Authentication authenticationMock = Mockito.mock(Authentication.class);
        Mockito.when(authenticationMock.getName()).thenReturn(login);
        Assertions.assertDoesNotThrow(() -> niCloudController.downloadFile("file", authenticationMock));
    }

    @Test
    public void editFileNameTest() {
        Authentication authenticationMock = Mockito.mock(Authentication.class);
        Mockito.when(authenticationMock.getName()).thenReturn(login);
        Assertions.assertDoesNotThrow(() -> niCloudController.editFileName("filename", "newFileName", authenticationMock));
    }

    @Test
    public void uploadFileTest() {
        Authentication authenticationMock = Mockito.mock(Authentication.class);
        Mockito.when(authenticationMock.getName()).thenReturn(login);
        Assertions.assertDoesNotThrow(() -> niCloudController.uploadFile("file", multipartFile, authenticationMock));
    }
}
