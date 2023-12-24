package com.Nickode.service;

import com.Nickode.entity.NiCloudFile;
import com.Nickode.entity.NiCloudUser;
import com.Nickode.exception.NotFoundFileException;
import com.Nickode.repository.NiCloudFileRepository;
import com.Nickode.repository.NiCloudUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class NiCloudFileService {
    @Autowired
    private final NiCloudFileRepository niCloudFileRepository;
    @Autowired
    private final NiCloudUserRepository niCloudUserRepository;

    public NiCloudFileService(NiCloudFileRepository niCloudFileRepository, NiCloudUserRepository niCloudUserRepository) {
        this.niCloudFileRepository = niCloudFileRepository;
        this.niCloudUserRepository = niCloudUserRepository;
    }

    public NiCloudFile create(String fileName, MultipartFile multipartFile, String authenticationGetName) {
        NiCloudFile niCloudFile;
        NiCloudUser niCloudUser = niCloudUserRepository.findByUsername(authenticationGetName).get();
        try {
            niCloudFile = new NiCloudFile(niCloudUser, fileName, multipartFile.getContentType(), multipartFile.getSize(), multipartFile.getBytes());
        } catch (IOException ioException) {
            throw new RuntimeException("file creation failure");
        }
        return niCloudFileRepository.save(niCloudFile);
    }

    public void update(String fileName, String newFileName, String authenticationGetName) {
        Optional<NiCloudFile> optionalFile = niCloudFileRepository.findByUserAndFilename(authenticationGetName, fileName);
        optionalFile.get().setFilename(newFileName);
        if (optionalFile.isEmpty()) {
            throw new NotFoundFileException(fileName);
        }
    }

    public void delete(String fileName, String authenticationGetName) {
        niCloudFileRepository.deleteByUserAndFilename(authenticationGetName, fileName);
    }

    public void deleteAll() {
        niCloudFileRepository.deleteAll();
    }

    public Optional<NiCloudFile> findFile(String fileName, String authenticationGetName) {
        return niCloudFileRepository.findByUserAndFilename(authenticationGetName, fileName);
    }

    public List<String> getAllFilesNames() {
        List<NiCloudFile> listOfNiCloudFiles = niCloudFileRepository.findByFilenameIsNotNull();
        List<String> listOfFileNames = new ArrayList<>();
        for (NiCloudFile niCloudFile : listOfNiCloudFiles
        ) {
            listOfFileNames.add(niCloudFile.getFilename());
        }
        return listOfFileNames;
    }
}
