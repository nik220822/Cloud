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
    //TODO
    public void update(String fileName, String newFileName, String authenticationGetName) {
        Optional<NiCloudFile> optionalFile = niCloudFileRepository.findByFilename(fileName);
        optionalFile.get().setFilename(newFileName);
        if (optionalFile.isEmpty()) {
            throw new NotFoundFileExcptn(fileName);
        }
    }

    //TODO
    public void delete(String fileName, String authenticationGetName) {
        niCloudFileRepository.deleteByFilename(fileName);
    }

    //TODO
    public void deleteAll(String authenticationGetName) {
        niCloudFileRepository.deleteAll();
    }
    //TODO
    public Optional<NiCloudFile> findFile(String fileName, String authenticationGetName) {
        return niCloudFileRepository.findByFilename(fileName);
    }
    //TODO
    public List<String> getAllFilesNames(String authenticationGetName) {
        List<NiCloudFile> listOfNiCloudFiles = niCloudFileRepository.findByFilenameIsNotNull();
        List<String> listOfFileNames = new ArrayList<>();
        for (NiCloudFile niCloudFile : listOfNiCloudFiles
        ) {
            listOfFileNames.add(niCloudFile.getFilename());
        }
        return listOfFileNames;
    }
}
