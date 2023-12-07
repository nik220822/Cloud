package com.Nickode.service;

import com.Nickode.entity.NiCloudFile;
import com.Nickode.exception.NotFoundFileExcptn;
import com.Nickode.repository.NiCloudFileRepository;
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

    public NiCloudFileService(NiCloudFileRepository niCloudFileRepository) {
        this.niCloudFileRepository = niCloudFileRepository;
    }

    public NiCloudFile create(String fileName, MultipartFile multipartFile, String authenticationGetName) {
        NiCloudFile niCloudFile = new NiCloudFile();
        try {
            niCloudFile = new NiCloudFile(authenticationGetName, fileName, multipartFile.getContentType(), multipartFile.getSize(), multipartFile.getBytes());
        } catch (IOException ioException) {
        }
        return niCloudFileRepository.save(niCloudFile);
    }
    public void update(String fileName, String newFileName, String authenticationGetName) {
        Optional<NiCloudFile> optionalFile = niCloudFileRepository.findByUserAndFilename(authenticationGetName, fileName);
        optionalFile.get().setFilename(newFileName);
        if (optionalFile.isEmpty()) {
            throw new NotFoundFileExcptn(fileName);
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
