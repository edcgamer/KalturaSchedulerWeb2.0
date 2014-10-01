package com.marcosanta.controllers;

import com.azteca.persistence.entities.Usuario;
import com.azteca.persistence.repository.KalturaEntryRepository;
import com.azteca.persistence.repository.SistemaUsuarioRepository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Santa
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService,Serializable {
//
    @Autowired
    private SistemaUsuarioRepository usuarioRepository;
    
    @Autowired
    private KalturaEntryRepository ka;
   

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        try{
        Usuario user;
        List<GrantedAuthority> autoridades = new ArrayList<>();
        user = usuarioRepository.findByUsername(username);
        if (user != null) {
           autoridades.add(new GrantedAuthorityImpl(user.getRol().getNombre()));
            return new SpringUser(username, user.getPw(), true, true, true, true, autoridades);
        } else {
            throw new UsernameNotFoundException("User  no se encontro");
        }
        }catch(Exception e){
            e.printStackTrace();
            throw new UsernameNotFoundException("User  no se encontro");
        }
       
    }
}
