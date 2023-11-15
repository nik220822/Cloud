package com.Nickode.controller;

import com.Nickode.entity.File;
import com.Nickode.service.NiCloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class NiCloudController {
    @Autowired
    private NiCloudService service;

    public NiCloudController(NiCloudService service) {
        this.service = service;
    }

    /**
     * authentication
     */
    @PutMapping("/login")
    public ResponseEntity<?> login(@RequestParam("user") String name, @RequestParam("password") String password) throws SQLException {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * getList
     */
    @GetMapping("/list")
    public List<String> getName() {
        return service.getFileNames();
    }

    /**
     * fileManagement
     */
    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(@RequestParam("file") String fileName) {
        try {
            service.delete(fileName);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Deleted successfully: %s", fileName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Failed to delete: %s", fileName));
        }
    }

    @DeleteMapping("/allfiles")
    public ResponseEntity<?> deleteAllFiles(@RequestHeader("auth-token") String authToken) {
        try {
            service.deleteAll();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Failed to delete."));
        }
    }

    @GetMapping("/file")
    public ResponseEntity<?> downloadFile(@RequestParam("file") String fileName) {
        Optional<File> optionalFile = service.findFile(fileName);
        if (optionalFile.isEmpty()) {
            return ResponseEntity.notFound()
                    .build();
        }
        File file = optionalFile.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + file.getFileName() + "\"")
                .contentType(MediaType.valueOf(file.getType()))
                .body(file.getData());
    }

    @PutMapping("/file")
    public ResponseEntity<?> editFileName(@RequestHeader("auth-token") String authToken, @RequestParam String fileName, @RequestBody File file) {
        try {
            service.update(fileName, file);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Updated successfully: %s", file.getFileName()));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Failed to update: %s", fileName));
        }
    }

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestHeader("auth-token") String authToken,
                                        @RequestParam("fileName") String fileName, MultipartFile multipartFile) {
        try {
            service.create(authToken,fileName, multipartFile);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
