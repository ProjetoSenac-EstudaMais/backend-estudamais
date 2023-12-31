package br.com.api.estudamais.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String sobrenome;
    private String username;
    private String email;

    @Column(length = 50000000)
    private byte[] avatar;

    @Column(length = 50000000)
    private byte[] banner;

    @OneToMany(mappedBy = "membros", cascade = CascadeType.ALL)
    private List<Community> communities = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private XP xp;

    public User() {
    }

    public List<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(List<Community> communities) {
        this.communities = communities;
    }
}
