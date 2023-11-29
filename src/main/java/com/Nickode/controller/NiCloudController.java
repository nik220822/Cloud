package com.Nickode.controller;

import com.Nickode.entity.File;
import com.Nickode.security.NiCloudJSONwebTokenManager;
import com.Nickode.security.NiCloudAuthRequest;
import com.Nickode.security.NiCloudAuthResponse;
import com.Nickode.service.NiCloudFileService;
import com.Nickode.service.UserDetailsServiceImpl;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class NiCloudController {
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private final NiCloudJSONwebTokenManager niCloudJSONwebTokenManager;
    @Autowired
    private final NiCloudFileService niCloudFileService;

    public NiCloudController(AuthenticationManager authenticationManager, UserDetailsServiceImpl jwtUserDetailsService, UserDetailsServiceImpl userDetailsService, UserDetailsServiceImpl userDetailsServiceImpl, NiCloudJSONwebTokenManager niCloudJSONwebTokenManager, NiCloudFileService niCloudFileService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.niCloudJSONwebTokenManager = niCloudJSONwebTokenManager;
        this.niCloudFileService = niCloudFileService;
    }
    /**
     * authentication
     */
    @PutMapping("/login")
    public ResponseEntity<NiCloudAuthResponse> login(@RequestBody @Valid final NiCloudAuthRequest niCloudAuthRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    niCloudAuthRequest.getUsername(), niCloudAuthRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(niCloudAuthRequest.getUsername());
        final NiCloudAuthResponse niCloudAuthResponse = new NiCloudAuthResponse();
        niCloudAuthResponse.setAuthToken(niCloudJSONwebTokenManager.generateToken(userDetails));
        return ResponseEntity.ok(niCloudAuthResponse);
    }
    @PutMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody HttpServletRequest httpServletRequest) {
        final String header = httpServletRequest.getHeader("auth-token");
        if (header.startsWith("Bearer ")) {
            niCloudJSONwebTokenManager.addBlackTokens(header.substring(7));
        } else {
            niCloudJSONwebTokenManager.addBlackTokens(header);
        }
        return ResponseEntity.ok("Success logout");
    }
    /**
     * getList
     */
    @GetMapping("/list")
    public ResponseEntity<List<String>> getFileNames(Authentication authentication) {
        return ResponseEntity.ok(niCloudFileService.getFileNames(authentication.getName()));
    }
    /**
     * fileManagement
     */
    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(@RequestParam("filename") String fileName, Authentication authentication) {
        try {
            niCloudFileService.delete(fileName, authentication.getName());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Deleted successfully: %s", fileName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Failed to delete: %s", fileName));
        }
    }
    @DeleteMapping("/allfiles")
    public ResponseEntity<?> deleteAllFiles(Authentication authentication) {
        try {
            niCloudFileService.deleteAll(authentication.getName());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Failed to delete."));
        }
    }
    @GetMapping("/file")
    public ResponseEntity<?> downloadFile(@RequestParam("file") String fileName, Authentication authentication) {
        Optional<File> optionalFile = niCloudFileService.findFile(fileName, authentication.getName());
        if (optionalFile.isEmpty()) {
            return ResponseEntity.notFound()
                    .build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + optionalFile.get().getFilename() + "\"")
                .contentType(MediaType.valueOf(optionalFile.get().getType()))
                .body(optionalFile.get().getData());
    }
    @PutMapping("/file")
    public ResponseEntity<?> editFileName(@RequestParam("filename") String fileName,
                                          @RequestBody String newFileName, Authentication authentication) {
        try {
            niCloudFileService.update(fileName, newFileName, authentication.getName());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Updated successfully: %s", fileName));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Failed to update: %s", fileName));
        }
    }
    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestParam("filename") String filename,
                                        @RequestBody MultipartFile multipartFile, Authentication authentication) {
        try {
            niCloudFileService.create(filename, multipartFile, authentication.getName());
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}