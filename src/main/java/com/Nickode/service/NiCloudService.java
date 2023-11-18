package com.Nickode.service;

import com.Nickode.entity.File;
import com.Nickode.exception.NotFoundFile;
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
public class NiCloudService {
    @Autowired
    private final NiCloudFileRepository niCloudFileRepository;

    public NiCloudService(NiCloudFileRepository niCloudFileRepository) {
        this.niCloudFileRepository = niCloudFileRepository;
    }

    public File create(String authToken, String fileName, MultipartFile multipartFile) {
        try {
            File file = new File(authToken.getUser, fileName, multipartFile.getContentType(), multipartFile.getSize(), multipartFile.getBytes());
        } catch (IOException ioException) {
        }
        return niCloudFileRepository.save(file);
    }

    public File update(String fileName, File file) {
        String id = file.getId();
        file.setFileName(fileName);
        if (niCloudFileRepository.existsById(id)) {
            niCloudFileRepository.deleteById(id);
            return niCloudFileRepository.save(file);
        } else {
            throw new NotFoundFile(id);
        }
    }

    public void delete(String id) {
        if (niCloudFileRepository.existsById(id)) {
            niCloudFileRepository.deleteById(id);
        } else {
            throw new NotFoundFile(id);
        }
    }

    public void deleteAll() {
        niCloudFileRepository.deleteAll();
    }

    public Optional<File> findFile(String fileName) {
        return niCloudFileRepository.findByFileName(fileName);
    }

    public List<String> getFileNames() {
        List<File> listOfFiles = niCloudFileRepository.findByNameIsNotNull();
        List<String> listOfFileNames = new ArrayList<>();
        for (File file : listOfFiles
        ) {
            listOfFileNames.add(file.getFileName());
        }
        return listOfFileNames;
    }
}
