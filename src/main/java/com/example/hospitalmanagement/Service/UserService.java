package com.example.hospitalmanagement.Service;


import com.example.hospitalmanagement.DTO.UserRequestDTO;
import com.example.hospitalmanagement.DTO.UserResponseDTO;
import com.example.hospitalmanagement.Repo.UserRepo;
import com.example.hospitalmanagement.model.UsersEntity;
import com.example.hospitalmanagement.utilities.UsersMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private UserRepo repo;

    @Autowired
    UserRepo usersRepository;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

//    public UserResponseDTO register(UserRequestDTO user) {
//        user.setPassword(encoder.encode(user.getPassword()));
//        log.debug("save user");
//        repo.save(user);
//        return user;
//    }
    public UserResponseDTO createUser(UserRequestDTO usersDTO) {
        UsersEntity usersEntity = UsersMapper.toEntity(usersDTO);
        // Encode the password before saving
        String encodedPassword = encoder.encode(usersDTO.getPassword());
        usersEntity.setPassword(encodedPassword);
        usersRepository.save(usersEntity);
        return UsersMapper.toResponseDTO(usersEntity);
    }
    public UserResponseDTO getUserById(int id) {
        Optional<UsersEntity> usersEntity = usersRepository.findById(id);
        return usersEntity.map(UsersMapper::toResponseDTO).orElse(null);
    }
    public String verify(UserRequestDTO user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            return "fail";
        }
    }
}