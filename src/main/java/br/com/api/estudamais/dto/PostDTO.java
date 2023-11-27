package br.com.api.estudamais.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private Long postId; // ID do Post que foi criado ou repostado
    private String conteudo;
    private Long userId; // ID do usuário que fez o post ou o repost
    private String nome; // Nome do usuário
    private String sobrenome; // Sobrenome do usuário
    private String username; // Username do usuário
    private byte[] avatar; // Avatar do usuário
    private boolean isRepost; // Indica se é um repost

    // Campos do usuário que fez o repost
    private String nomeRepostador;
    private String sobrenomeRepostador;
    private String usernameRepostador;
    private byte[] avatarRepostador;

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
