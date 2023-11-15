package com.Nickode.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "files")
public class File {

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
    private String fileName;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "type")
    private String type;
    @Lob
    @Column(name = "data", nullable = false, columnDefinition = "BLOB")
    private byte[] data;
}
