package br.com.api.estudamais.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComentarioDTO {
    private Long id;
    private String conteudo;
    private UsuarioDTO autor;

}
