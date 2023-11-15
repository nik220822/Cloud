package com.Nickode.service;

import com.Nickode.entity.File;
import com.Nickode.exception.NotFoundFile;
import com.Nickode.repository.NiCloudRepository;
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
    private final NiCloudRepository niCloudRepository;

    public NiCloudService(NiCloudRepository niCloudRepository) {
        this.niCloudRepository = niCloudRepository;
    }

    public File create(String authToken, String fileName, MultipartFile multipartFile) {
        try {
            File file = new File(authToken.getUser, fileName, multipartFile.getContentType(), multipartFile.getSize(), multipartFile.getBytes());
        } catch (IOException ioException) {
        }
        return niCloudRepository.save(file);
    }

    public File update(String fileName, File file) {
        String id = file.getId();
        file.setFileName(fileName);
        if (niCloudRepository.existsById(id)) {
            niCloudRepository.deleteById(id);
            return niCloudRepository.save(file);
        } else {
            throw new NotFoundFile(id);
        }
    }

    public void delete(String id) {
        if (niCloudRepository.existsById(id)) {
            niCloudRepository.deleteById(id);
        } else {
            throw new NotFoundFile(id);
        }
    }

    public void deleteAll() {
        niCloudRepository.deleteAll();
    }

    public Optional<File> findFile(String fileName) {
        return niCloudRepository.findByFileName(fileName);
    }

    public List<String> getFileNames() {
        List<File> listOfFiles = niCloudRepository.findByNameIsNotNull();
        List<String> listOfFileNames = new ArrayList<>();
        for (File file : listOfFiles
        ) {
            listOfFileNames.add(file.getFileName());
        }
        return listOfFileNames;
    }
}
