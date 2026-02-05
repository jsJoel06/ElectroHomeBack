package com.empleos.electrohome.service.impl;

import com.empleos.electrohome.models.UserEntity;
import com.empleos.electrohome.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
      UserEntity userEntity = userEntityRepository.findByEmail(email)
              .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return UserDetailService.build(userEntity);
    }
}
