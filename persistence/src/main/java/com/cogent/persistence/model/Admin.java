package com.cogent.persistence.model;

import com.cogent.persistence.listener.AdminEntityListener;
import com.cogent.persistence.audit.Auditable;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "admin")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AdminEntityListener.class)
public class Admin extends Auditable<String> implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", profile=" + profile.getName() +
                '}';
    }
}