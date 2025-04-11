package ru.abdur.SecurityApp.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created")
    @CreatedDate
    private Date created;
    @Column(name = "updated")
    @LastModifiedDate
    private Date updated;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;

}