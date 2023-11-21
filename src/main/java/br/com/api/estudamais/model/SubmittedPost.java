package br.com.api.estudamais.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SubmittedPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String conteudo;

    @ManyToOne
    @JoinColumn(name = "autor_id") 
    private User autor;

    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;

    @ManyToOne // Muitos posts pertencem a um usuário
    @JoinColumn(name = "user_id")
    private User user;

    private boolean approved;

    public void setPostContent(String conteudo2) {
    }
}
