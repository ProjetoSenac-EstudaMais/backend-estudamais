package br.com.api.estudamais.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.api.estudamais.model.Login;


public interface LoginRepository extends JpaRepository<Login, String> {
    UserDetails findByLogin(String login);
}