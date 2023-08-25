package com.example.mitarbeiter.entity;

import jakarta.persistence.*;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

@Entity
@Table(name = "profile_picture", schema = "public")
public class ProfilePictureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(name = "picture_data", nullable = false)
    private Blob pictureData;

    @OneToOne
    private EmployeeEntity employee;

    public ProfilePictureEntity() {

    }

    public Long getId() {
        return id;
    }

    public Blob getPictureData() {
        return pictureData;
    }

    public String getPictureBase64Encoded() {
        try {
            return Base64.getEncoder().encodeToString(pictureData.getBinaryStream().readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPictureData(Blob pictureData) {
        this.pictureData = pictureData;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }
}