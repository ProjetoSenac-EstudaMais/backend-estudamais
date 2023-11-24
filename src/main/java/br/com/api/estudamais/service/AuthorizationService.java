package br.com.api.estudamais.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import br.com.api.estudamais.repository.LoginRepository;


@Service
public class AuthorizationService implements UserDetailsService{

    @Autowired
    LoginRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) {     
        if(repository.findByLogin(username) == null ){
            throw new Error("Usuario não encontrado!");
        }
        return repository.findByLogin(username);
    }
    
}