package br.com.api.estudamais.dto;

import br.com.api.estudamais.model.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
    
}