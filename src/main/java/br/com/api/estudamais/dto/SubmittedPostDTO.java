package br.com.api.estudamais.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmittedPostDTO {
    private Long userId; // ID do usuário que está fazendo a postagem
    private String title;
    private String conteudo; // Conteúdo da postagem
}
