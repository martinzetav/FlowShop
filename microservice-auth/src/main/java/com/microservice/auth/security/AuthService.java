package com.microservice.auth.security;

import com.microservice.auth.dto.request.AuthLoginRequestDTO;
import com.microservice.auth.dto.request.CreateUserRequestDTO;
import com.microservice.auth.dto.response.AuthResponseDTO;
import com.microservice.auth.dto.response.CreateUserResponseDTO;
import com.microservice.auth.model.Role;
import com.microservice.auth.model.RoleEnum;
import com.microservice.auth.model.UserEntity;
import com.microservice.auth.repository.IRoleRepository;
import com.microservice.auth.repository.IUserRepository;
import com.microservice.auth.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;

    public AuthResponseDTO loginUser(AuthLoginRequestDTO request){

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtils.createToken(authentication);

        return new AuthResponseDTO(request.email(), "Usuario logeado exitosamente", token, true);

    }

    public CreateUserResponseDTO signUpUser(CreateUserRequestDTO createUserDTO){

        Role role = roleRepository.findByRoleName(RoleEnum.USER)
                .orElseThrow(() -> new RuntimeException("El rol USER no existe en la BD"));


        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.username())
                .password(passwordEncoder.encode(createUserDTO.password()))
                .email(createUserDTO.email())
                .rol(role)
                .build();

        UserEntity userCreated = userRepository.save(userEntity);
        CreateUserResponseDTO responseDTO = new CreateUserResponseDTO(userCreated.getEmail(), userCreated.getPassword(), userCreated.getRol().getRoleName().name());
        return responseDTO;
    }

}
