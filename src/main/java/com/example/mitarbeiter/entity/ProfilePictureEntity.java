package com.example.mitarbeiter.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "profile_picture", schema = "public")
public class ProfilePictureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(name = "picture_data", nullable = false)
    private byte[] pictureData;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private EmployeeEntity employee;

    public ProfilePictureEntity() {

    }

    public Long getId() {
        return id;
    }

    public byte[] getPictureData() {
        return pictureData;
    }

    public void setPictureData(byte[] pictureData) {
        this.pictureData = pictureData;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }
}
