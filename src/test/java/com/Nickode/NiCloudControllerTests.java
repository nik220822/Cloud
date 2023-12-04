package com.Nickode;

import com.Nickode.controller.NiCloudController;
import com.Nickode.entity.NiCloudFile;
import com.Nickode.repository.NiCloudFileRepository;
import com.Nickode.security.NiCloudAuthRequest;
import com.Nickode.security.NiCloudJSONwebTokenManager;
import com.Nickode.service.NiCloudFileService;
import com.Nickode.service.NiCloudUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.function.Function;

public class NiCloudControllerTests {
    private static final String login = "Nikolai";
    private static final String secret = "nikolaiSecret";
    private final NiCloudJSONwebTokenManager niCloudJSONwebTokenManager=new NiCloudJSONwebTokenManager(secret);
    private final NiCloudController niCloudController = new NiCloudController(Mockito.mock(AuthenticationManager.class), Mockito.mock(NiCloudUserService.class), Mockito.mock(NiCloudJSONwebTokenManager.class), new NiCloudFileService(new NiCloudFileRepository() {
        @Override
        public List<NiCloudFile> findAll(Sort sort) {
            return null;
        }

        @Override
        public Page<NiCloudFile> findAll(Pageable pageable) {
            return null;
        }

        @Override
        public <S extends NiCloudFile> S save(S entity) {
            return null;
        }

        @Override
        public <S extends NiCloudFile> List<S> saveAll(Iterable<S> entities) {
            return null;
        }

        @Override
        public Optional<NiCloudFile> findById(String s) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(String s) {
            return false;
        }

        @Override
        public List<NiCloudFile> findAll() {
            return null;
        }

        @Override
        public List<NiCloudFile> findAllById(Iterable<String> strings) {
            return null;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(String s) {

        }

        @Override
        public void delete(NiCloudFile entity) {

        }

        @Override
        public void deleteAllById(Iterable<? extends String> strings) {

        }

        @Override
        public void deleteAll(Iterable<? extends NiCloudFile> entities) {

        }

        @Override
        public void deleteAll() {

        }

        @Override
        public void flush() {

        }

        @Override
        public <S extends NiCloudFile> S saveAndFlush(S entity) {
            return null;
        }

        @Override
        public <S extends NiCloudFile> List<S> saveAllAndFlush(Iterable<S> entities) {
            return null;
        }

        @Override
        public void deleteAllInBatch(Iterable<NiCloudFile> entities) {

        }

        @Override
        public void deleteAllByIdInBatch(Iterable<String> strings) {

        }

        @Override
        public void deleteAllInBatch() {

        }

        @Override
        public NiCloudFile getOne(String s) {
            return null;
        }

        @Override
        public NiCloudFile getById(String s) {
            return null;
        }

        @Override
        public NiCloudFile getReferenceById(String s) {
            return null;
        }

        @Override
        public <S extends NiCloudFile> Optional<S> findOne(Example<S> example) {
            return Optional.empty();
        }

        @Override
        public <S extends NiCloudFile> List<S> findAll(Example<S> example) {
            return null;
        }

        @Override
        public <S extends NiCloudFile> List<S> findAll(Example<S> example, Sort sort) {
            return null;
        }

        @Override
        public <S extends NiCloudFile> Page<S> findAll(Example<S> example, Pageable pageable) {
            return null;
        }

        @Override
        public <S extends NiCloudFile> long count(Example<S> example) {
            return 0;
        }

        @Override
        public <S extends NiCloudFile> boolean exists(Example<S> example) {
            return false;
        }

        @Override
        public <S extends NiCloudFile, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
            return null;
        }

        @Override
        public Optional<NiCloudFile> findByFilename(String FileName) {
            return Optional.empty();
        }

        @Override
        public List<NiCloudFile> findByFilenameIsNotNull() {
            return null;
        }

        @Override
        public void deleteByFilename(String Filename) {

        }
    }));
    private static final MultipartFile multipartFile = new MockMultipartFile("MultipartFileTestName", "MultipartFileTestOriginalFilename", "byte",
            new String("Test Multipart NiCloudFile").getBytes());

    @Test
    public void loginTest() {
        NiCloudAuthRequest niCloudAuthRequest = new NiCloudAuthRequest();
        niCloudAuthRequest.setUsername("Nikolai");
        niCloudAuthRequest.setPassword("nikolaipassword");
        Assertions.assertDoesNotThrow(() -> niCloudController.login(niCloudAuthRequest));
    }
    //TODO
    @Test
    public void logoutTest() {
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(httpServletRequest.getHeader("auth-token")).thenReturn("Bearer test token");
        niCloudController.logout(httpServletRequest);
        Assertions.assertTrue(niCloudJSONwebTokenManager.getBlackTokens().contains("test token"));
    }
    //TODO
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
