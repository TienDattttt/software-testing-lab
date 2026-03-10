package org.bai5.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "job_title",
        uniqueConstraints = @UniqueConstraint(columnNames = "title"))
public class JobTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Job Title không được để trống")
    @Size(max = 100, message = "Job Title tối đa 100 ký tự")
    @Column(nullable = false, length = 100)
    private String title;

    @Size(max = 400, message = "Description tối đa 400 ký tự")
    private String description;

    @Size(max = 400, message = "Note tối đa 400 ký tự")
    private String note;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_size")
    private Long fileSize;

    @Lob
    private byte[] fileData;

    // getter & setter
}
