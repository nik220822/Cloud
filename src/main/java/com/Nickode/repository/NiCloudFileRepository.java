package com.Nickode.repository;

import com.Nickode.entity.NiCloudFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NiCloudFileRepository extends JpaRepository<NiCloudFile, String> {
    List<NiCloudFile> findByFilenameIsNotNull();

    Optional<NiCloudFile> findByUserAndFilename(String user, String fileName);

    void deleteByUserAndFilename(String user, String fileName);
}
