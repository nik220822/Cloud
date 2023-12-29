package com.Nickode.repository;

import com.Nickode.entity.NiCloudFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
//@Repository
//public interface NiCloudFileRepository extends JpaRepository<NiCloudFile, String> {
//    List<NiCloudFile> findByFilenameIsNotNull();
//
//    Optional<NiCloudFile> findByUserAndFilename(String user, String fileName);
//
//    void deleteByUserAndFilename(String user, String fileName);
//}
@Repository
public interface NiCloudFileRepository extends JpaRepository<NiCloudFile, UUID> {
    List<NiCloudFile> findByFilenameIsNotNull();
    Optional<NiCloudFile> findByUser_usernameAndFilename(String userName, String fileName);
    void deleteByUser_usernameAndFilename(String userName, String fileName);
}