package com.hejazi.securityApp.securityApp.services;

import com.hejazi.securityApp.securityApp.dto.SignupDTO;
import com.hejazi.securityApp.securityApp.dto.UserDTO;
import com.hejazi.securityApp.securityApp.entities.User;
import com.hejazi.securityApp.securityApp.exceptions.ResourceNotFoundException;
import com.hejazi.securityApp.securityApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    public UserDTO signup(SignupDTO signupDTO) {
        Optional<User> user= userRepository.findByEmail(signupDTO.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("User with email id "+ signupDTO.getEmail()+ " already exists.");
        }

        User toCreate= modelMapper.map(signupDTO, User.class);
        toCreate.setPassword(passwordEncoder.encode(toCreate.getPassword()));
        User savedUser= userRepository.save(toCreate);
        return modelMapper.map(savedUser,UserDTO.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(()-> new ResourceNotFoundException("User with username"+ username+" not found."));
    }

    public User getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User with id"+ userId+" not found."));

        return user;
    }
}
