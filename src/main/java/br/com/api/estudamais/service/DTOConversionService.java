package br.com.api.estudamais.service;

import org.springframework.stereotype.Service;

import br.com.api.estudamais.dto.ComentarioDTO;
import br.com.api.estudamais.dto.UsuarioDTO;
import br.com.api.estudamais.model.Comentario;
import br.com.api.estudamais.model.User;

@Service
public class DTOConversionService {

    public ComentarioDTO convertComentarioToDTO(Comentario comentario) {
        ComentarioDTO comentarioDTO = new ComentarioDTO();
        comentarioDTO.setId(comentario.getId());
        comentarioDTO.setConteudo(comentario.getConteudo());
        comentarioDTO.setAutor(convertUserToDTO(comentario.getAutor()));
        return comentarioDTO;
    }

    public UsuarioDTO convertUserToDTO(User usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setSobrenome(usuario.getSobrenome());
        usuarioDTO.setUsername(usuario.getUsername());
        return usuarioDTO;
    }
}
