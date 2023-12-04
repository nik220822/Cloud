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
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class NiCloudServicesTests {
    NiCloudFileRepository niCloudFileRepository = new NiCloudFileRepository() {
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
    };
    NiCloudUserRepository niCloudUserRepository = new NiCloudUserRepository() {
        @Override
        public Optional<NiCloudUser> findByUsername(String username) {
            return Optional.empty();
        }

        @Override
        public List<NiCloudUser> findByUsernameIsNotNull() {
            return null;
        }

        @Override
        public void flush() {

        }

        @Override
        public <S extends NiCloudUser> S saveAndFlush(S entity) {
            return null;
        }

        @Override
        public <S extends NiCloudUser> List<S> saveAllAndFlush(Iterable<S> entities) {
            return null;
        }

        @Override
        public void deleteAllInBatch(Iterable<NiCloudUser> entities) {

        }

        @Override
        public void deleteAllByIdInBatch(Iterable<String> strings) {

        }

        @Override
        public void deleteAllInBatch() {

        }

        @Override
        public NiCloudUser getOne(String s) {
            return null;
        }

        @Override
        public NiCloudUser getById(String s) {
            return null;
        }

        @Override
        public NiCloudUser getReferenceById(String s) {
            return null;
        }

        @Override
        public <S extends NiCloudUser> List<S> findAll(Example<S> example) {
            return null;
        }

        @Override
        public <S extends NiCloudUser> List<S> findAll(Example<S> example, Sort sort) {
            return null;
        }

        @Override
        public <S extends NiCloudUser> List<S> saveAll(Iterable<S> entities) {
            return null;
        }

        @Override
        public List<NiCloudUser> findAll() {
            return null;
        }

        @Override
        public List<NiCloudUser> findAllById(Iterable<String> strings) {
            return null;
        }

        @Override
        public <S extends NiCloudUser> S save(S entity) {
            return null;
        }

        @Override
        public Optional<NiCloudUser> findById(String s) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(String s) {
            return false;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(String s) {

        }

        @Override
        public void delete(NiCloudUser entity) {

        }

        @Override
        public void deleteAllById(Iterable<? extends String> strings) {

        }

        @Override
        public void deleteAll(Iterable<? extends NiCloudUser> entities) {

        }

        @Override
        public void deleteAll() {

        }

        @Override
        public List<NiCloudUser> findAll(Sort sort) {
            return null;
        }

        @Override
        public Page<NiCloudUser> findAll(Pageable pageable) {
            return null;
        }

        @Override
        public <S extends NiCloudUser> Optional<S> findOne(Example<S> example) {
            return Optional.empty();
        }

        @Override
        public <S extends NiCloudUser> Page<S> findAll(Example<S> example, Pageable pageable) {
            return null;
        }

        @Override
        public <S extends NiCloudUser> long count(Example<S> example) {
            return 0;
        }

        @Override
        public <S extends NiCloudUser> boolean exists(Example<S> example) {
            return false;
        }

        @Override
        public <S extends NiCloudUser, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
            return null;
        }
    };
    private static final String authenticationGetName = "Nikolai";
    private static final String authenticationGetPassword = "NikolaiPassword";
    NiCloudFileService niCloudFileService = new NiCloudFileService(niCloudFileRepository);
    NiCloudUserService niCloudUserService = new NiCloudUserService(Mockito.mock(NiCloudJSONwebTokenManager.class), Mockito.mock(AuthenticationManager.class), Mockito.mock(HttpServletRequest.class), niCloudUserRepository);
    NiCloudAuthRequest niCloudAuthRequest = new NiCloudAuthRequest(authenticationGetName, authenticationGetPassword);
    private static final String testToken = "test token";
    private static final MultipartFile multipartFile = new MockMultipartFile("MultipartFileName", "MultipartFileTestOriginalFilename", "byte",
            new String("Test Multipart NiCloudFile").getBytes());

    @Test
    public void create() {
        Assertions.assertDoesNotThrow(() -> niCloudFileService.create(multipartFile.getName(), multipartFile, authenticationGetName));
    }
    //TODO
    @Test
    public void update() {
        Assertions.assertDoesNotThrow(() -> niCloudFileService.update(multipartFile.getName(), "Nikolas", authenticationGetName));

    }

    @Test
    public void delete() {
        Assertions.assertDoesNotThrow(() -> niCloudFileService.delete(multipartFile.getName(), authenticationGetName));
    }

    @Test
    public void deleteAll() {
        Assertions.assertDoesNotThrow(() -> niCloudFileService.deleteAll(authenticationGetName));
    }

    @Test
    public void findFile() {
        Assertions.assertDoesNotThrow(() -> niCloudFileService.findFile(multipartFile.getName(), authenticationGetName));
    }
    //TODO
    @Test
    public void getFileNames() {
        Assertions.assertDoesNotThrow(() -> niCloudFileService.getAllFilesNames(authenticationGetName));
    }

    //TODO
    @Test
    public void loadUserByUsername() {
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
