package com.Nickode.controller;

import com.Nickode.entity.NiCloudFile;
import com.Nickode.security.NiCloudJSONwebTokenManager;
import com.Nickode.security.NiCloudAuthRequest;
import com.Nickode.security.NiCloudAuthResponse;
import com.Nickode.service.NiCloudFileService;
import com.Nickode.service.NiCloudUserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@RestController
@RequestMapping("/")
public class NiCloudController {
    static Logger niCloudControllerLogger;

    static {
        try (FileInputStream ins = new FileInputStream("logger.config")) {
            LogManager.getLogManager().readConfiguration(ins);
            niCloudControllerLogger = Logger.getLogger(NiCloudController.class.getName());
        } catch (Exception exception) {
            niCloudControllerLogger.log(Level.WARNING, "The logger exception was caught: ", exception);
            exception.printStackTrace();
        }
    }

    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final NiCloudJSONwebTokenManager niCloudJSONwebTokenManager;
    @Autowired
    private final NiCloudUserService niCloudUserService;
    @Autowired
    private final NiCloudFileService niCloudFileService;

    public NiCloudController(AuthenticationManager authenticationManager, NiCloudUserService niCloudUserService, NiCloudJSONwebTokenManager niCloudJSONwebTokenManager, NiCloudFileService niCloudFileService) {
        this.authenticationManager = authenticationManager;
        this.niCloudJSONwebTokenManager = niCloudJSONwebTokenManager;
        this.niCloudUserService = niCloudUserService;
        this.niCloudFileService = niCloudFileService;
    }

    /**
     * authentication
     */
    @PutMapping("/login")
    public NiCloudAuthResponse login(@RequestBody @Valid final NiCloudAuthRequest niCloudAuthRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    niCloudAuthRequest.getLogin(), niCloudAuthRequest.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {
            niCloudControllerLogger.log(Level.WARNING, "An exception was caught: ", badCredentialsException);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        final UserDetails userDetails = niCloudUserService.loadUserByUsername(niCloudAuthRequest.getLogin());
        final NiCloudAuthResponse niCloudAuthResponse = new NiCloudAuthResponse();
        niCloudAuthResponse.setAuthToken(niCloudJSONwebTokenManager.generateToken(userDetails));
        niCloudControllerLogger.log(Level.INFO, "The token was successfully generated: you are logged in.");
        return niCloudAuthResponse;
    }

    @PutMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody HttpServletRequest httpServletRequest) {
        final String header = httpServletRequest.getHeader("auth-token");
        niCloudControllerLogger.log(Level.INFO, "The token from the request was successfully loaded into the variable");
        if (header.startsWith("Bearer ")) {
            niCloudJSONwebTokenManager.addBlackTokens(header.substring(7));
        } else {
            niCloudJSONwebTokenManager.addBlackTokens(header);
        }
        niCloudControllerLogger.log(Level.INFO, "The token from the request has been added to the blacklist");
        return ResponseEntity.ok("Success logout");
    }

    /**
     * getList
     */
    @GetMapping("/list")
    @RolesAllowed({"ROLE_USER"})
    public ResponseEntity<List<String>> getFileNames() {
        try {
            niCloudControllerLogger.log(Level.INFO, "The response is generated with the list of all the files");
            return ResponseEntity.ok(niCloudFileService.getAllFilesNames());
        } catch (Exception exception) {
            niCloudControllerLogger.log(Level.WARNING, "An exception was caught: ", exception);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * fileManagement
     */
    @DeleteMapping("/file")
    @RolesAllowed({"ROLE_USER"})
    public ResponseEntity<?> deleteFile(@RequestParam("filename") String fileName, Authentication authentication) {
        try {
            niCloudFileService.delete(fileName, authentication.getName());
            niCloudControllerLogger.log(Level.INFO, "niCloudFileService deleted the file successfully");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Deleted successfully: %s", fileName));
        } catch (Exception exception) {
            niCloudControllerLogger.log(Level.WARNING, "An exception was caught: ", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Failed to delete: %s", fileName));
        }
    }

    @DeleteMapping("/allfiles")
    @RolesAllowed({"ROLE_USER"})
    public ResponseEntity<?> deleteAllFiles() {
        try {
            niCloudFileService.deleteAll();
            niCloudControllerLogger.log(Level.INFO, "niCloudFileService deleted all the files successfully");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Deleted successfully."));
        } catch (Exception exception) {
            niCloudControllerLogger.log(Level.WARNING, "An exception was caught: ", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Failed to delete."));
        }
    }

    @GetMapping("/file")
    @RolesAllowed({"ROLE_USER"})
    public ResponseEntity<?> downloadFile(@RequestParam("file") String fileName, Authentication authentication) {
        Optional<NiCloudFile> optionalFile = niCloudFileService.findFile(fileName, authentication.getName());
        if (optionalFile.isEmpty()) {
            niCloudControllerLogger.log(Level.INFO, "The file wasn't found");
            return ResponseEntity.notFound()
                    .build();
        }
        niCloudControllerLogger.log(Level.INFO, "The file was found and will be downloaded");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + optionalFile.get().getFilename() + "\"")
                .contentType(MediaType.valueOf(optionalFile.get().getType()))
                .body(optionalFile.get().getData());
    }

    @PutMapping("/file")
    @RolesAllowed({"ROLE_USER"})
    public ResponseEntity<?> editFileName(@RequestParam("filename") String fileName,
                                          @RequestBody String newFileName, Authentication authentication) {
        try {
            niCloudFileService.update(fileName, newFileName, authentication.getName());
            niCloudControllerLogger.log(Level.INFO, "The file name was changed successfully");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Updated successfully: %s", fileName));
        } catch (Exception exception) {
            niCloudControllerLogger.log(Level.WARNING, "An exception was caught: ", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Failed to update: %s", fileName));
        }
    }

    @PostMapping("/file")
    @RolesAllowed({"ROLE_USER"})
    public ResponseEntity<?> uploadFile(@RequestParam("filename") String filename,
                                        @RequestBody MultipartFile multipartFile, Authentication authentication) {
        try {
            niCloudFileService.create(filename, multipartFile, authentication.getName());
            niCloudControllerLogger.log(Level.INFO, "The file was successfully created in the database");
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception exception) {
            niCloudControllerLogger.log(Level.WARNING, "An exception was caught: ", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}