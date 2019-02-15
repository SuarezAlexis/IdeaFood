package jainjo.ideafood.services;

import jainjo.ideafood.dao.UsuarioDao;
import jainjo.ideafood.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class IdeaFoodUserDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioDao usuarioDao;
    
    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.find(string);
        if(usuario == null)
            throw new UsernameNotFoundException("No se encontró un usuario con el nombre o correo " + string);
        
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        
        User user = new User
          (usuario.getUserName(), 
          usuario.getPassword(), enabled, accountNonExpired, 
          credentialsNonExpired, accountNonLocked, 
          getAuthorities(usuario.getPermisos()));
        
        return  user;
    }
    
    private static List<GrantedAuthority> getAuthorities (List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
