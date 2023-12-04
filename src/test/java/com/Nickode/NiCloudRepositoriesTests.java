package com.Nickode;

import com.Nickode.entity.NiCloudFile;
import com.Nickode.entity.NiCloudUser;
import com.Nickode.repository.NiCloudFileRepository;
import com.Nickode.repository.NiCloudUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class NiCloudRepositoriesTests {
    NiCloudFileRepository niCloudFileRepository = new NiCloudFileRepository() {
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
        public <S extends NiCloudFile> List<S> findAll(Example<S> example) {
            return null;
        }

        @Override
        public <S extends NiCloudFile> List<S> findAll(Example<S> example, Sort sort) {
            return null;
        }

        @Override
        public <S extends NiCloudFile> List<S> saveAll(Iterable<S> entities) {
            return null;
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
        public <S extends NiCloudFile> S save(S entity) {
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
        public List<NiCloudFile> findAll(Sort sort) {
            return null;
        }

        @Override
        public Page<NiCloudFile> findAll(Pageable pageable) {
            return null;
        }

        @Override
        public <S extends NiCloudFile> Optional<S> findOne(Example<S> example) {
            return Optional.empty();
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

    @Test
    void findByFilename() {
        NiCloudFile testNiCloudFile = new NiCloudFile();
        testNiCloudFile.setFilename("testFileFilename");
        niCloudFileRepository.save(testNiCloudFile);
        Assertions.assertDoesNotThrow(() -> niCloudFileRepository.findByFilename("testFileFileName"));
    }

    @Test
    void findByFilenameIsNotNull() {
        NiCloudFile testNiCloudFile1 = new NiCloudFile();
        testNiCloudFile1.setFilename("testFile1Filename");
        NiCloudFile testNiCloudFile2 = new NiCloudFile();
        testNiCloudFile2.setFilename("testFile2Filename");
        niCloudFileRepository.save(testNiCloudFile1);
        niCloudFileRepository.save(testNiCloudFile2);
        Assertions.assertDoesNotThrow(() -> niCloudFileRepository.findByFilenameIsNotNull());
    }

    @Test
    void deleteByFilename() {
        NiCloudFile testNiCloudFile = new NiCloudFile();
        testNiCloudFile.setFilename("testFileFilename");
        niCloudFileRepository.save(testNiCloudFile);
        Assertions.assertDoesNotThrow(() -> niCloudFileRepository.deleteByFilename("testFileFilename"));
    }

    @Test
    void findByUsername() {
        NiCloudUser niCloudUser = new NiCloudUser();
        niCloudUser.setUsername("Nikolai");
        Assertions.assertDoesNotThrow(() -> niCloudUserRepository.findByUsername("Nikolai"));
    }

    @Test
    void findByUsernameIsNotNull() {
        NiCloudUser niCloudUser = new NiCloudUser();
        niCloudUser.setUsername("Nikolai");
        Assertions.assertDoesNotThrow(() -> niCloudUserRepository.findByUsernameIsNotNull());
    }
}
