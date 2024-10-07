package com.swp391.koibe.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
@Entity
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(20)")
    private String name;

//    @OneToMany(mappedBy = "role")
//    private List<User> users;

    public Role(String name) {
        this.name = name;
    }

    public static String MEMBER = "MEMBER";
    public static String STAFF = "STAFF";
    public static String BREEDER = "BREEDER";
    public static String MANAGER = "MANAGER";
}
