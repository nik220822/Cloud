package com.Nickode.service;

import com.Nickode.entity.File;
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

    public File create(String fileName, MultipartFile multipartFile, String authenticationGetName) {
        File file = new File();
        try {
            file = new File(authenticationGetName, fileName, multipartFile.getContentType(), multipartFile.getSize(), multipartFile.getBytes());
        } catch (IOException ioException) {
        }
        return niCloudFileRepository.save(file);
    }

    public void update(String fileName, String newFileName, String authenticationGetName) {
        Optional<File> optionalFile = niCloudFileRepository.findByFilename(fileName);
        optionalFile.get().setFilename(newFileName);
        if (optionalFile.isEmpty()) {
            throw new NotFoundFileExcptn(fileName);
        }
    }

    public void delete(String fileName, String authenticationGetName) {
//        String id = niCloudFileRepository.findByFileName(fileName).get().getId();
//        niCloudFileRepository.deleteById(id);
        niCloudFileRepository.deleteByFilename(fileName);
    }

    public void deleteAll(String authenticationGetName) {
        niCloudFileRepository.deleteAll();
    }

    public Optional<File> findFile(String fileName, String authenticationGetName) {
        return niCloudFileRepository.findByFilename(fileName);
    }

    public List<String> getFileNames(String authenticationGetName) {
        List<File> listOfFiles = niCloudFileRepository.findByFilenameIsNotNull();
        List<String> listOfFileNames = new ArrayList<>();
        for (File file : listOfFiles
        ) {
            listOfFileNames.add(file.getFilename());
        }
        return listOfFileNames;
    }
}
