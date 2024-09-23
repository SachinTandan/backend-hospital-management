package com.example.hospitalmanagement.Service;


import com.example.hospitalmanagement.DTO.AuthenticateUsersRequest;
import com.example.hospitalmanagement.DTO.UserRegistrationRequestDTO;
import com.example.hospitalmanagement.Repo.UserRepo;
import com.example.hospitalmanagement.model.UsersEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;


    @Autowired
    UserRepo usersRepository;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

//    public AuthenticateUsersRequest register(UserRegistrationRequestDTO user) {
//        user.setPassword(encoder.encode(user.getPassword()));
//        log.debug("save user");
//        repo.save(user);
//        return user;
//    }
    public void createUser(UserRegistrationRequestDTO usersDTO) {
        UsersEntity usersEntity = UsersEntity.builder()
                .username(usersDTO.getUsername())
                .password(encoder.encode(usersDTO.getPassword()))
                .firstName(usersDTO.getFirstName())
                .email(usersDTO.getEmail())
                .lastName(usersDTO.getLastName())
                .dateOfBirth(usersDTO.getDateOfBirth())
                .role(usersDTO.getRole())
                .build();
        UsersEntity user = usersRepository.save(usersEntity);
        log.info("User created: " + user);

    }

    //    public AuthenticateUsersRequest getUserById(int id) {
//        Optional<UsersEntity> usersEntity = usersRepository.findById(id);
//        return usersEntity.map(UsersMapper::toResponseDTO).orElse(null);
//    }
    public String verify(AuthenticateUsersRequest usersLoginRequest) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(usersLoginRequest.getUsername(), usersLoginRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            UsersEntity user = usersRepository.findByUsername(usersLoginRequest.getUsername());
            String token = jwtService.generateToken(usersLoginRequest.getUsername(), user.getRole());
            log.info("Token generated: " + token);
            return token;
        } else {
            return "fail";
        }
    }
}