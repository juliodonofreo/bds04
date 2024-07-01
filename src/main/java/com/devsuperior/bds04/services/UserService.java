package com.devsuperior.bds04.services;

import com.devsuperior.bds04.entities.Role;
import com.devsuperior.bds04.entities.User;
import com.devsuperior.bds04.projections.UserDetailsProjection;
import com.devsuperior.bds04.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    public UserService(UserRepository repository){
        this.repository = repository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = repository.findByEmail(username);
        if (result.isEmpty()){
            throw new UsernameNotFoundException("User not found.");
        }

        User user = new User();

        user.setEmail(username);
        user.setPassword(result.get(0).getPassword());
        for (UserDetailsProjection details: result){
            user.addRole(new Role(details.getRoleId(), details.getAuthority()));
        }

        return user;
    }
}
