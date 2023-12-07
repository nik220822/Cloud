package com.Nickode.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
@Data
@Entity
@Table(name = "niCloudFiles")
public class NiCloudFile {
    @Id
    @GeneratedValue(generator = "NickIDgenerator")
    @GenericGenerator(
            name = "NickIDgenerator",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", nullable = true)
    private NiCloudUser user;

    @Column(name = "filename", nullable = false)
    private String filename;

    @Column(name = "type")
    private String type;

    @Column(name = "size", nullable = false)
    private Long size;

    @Lob
    @Column(name = "data", nullable = false, columnDefinition = "BLOB")
    private byte[] data;

    @Autowired
    public NiCloudFile(String authenticationGetName, String fileName, String type, long size, byte[] data) {
    }

}
