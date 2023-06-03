package bjit.ursa.authserver.service.impl;

import bjit.ursa.authserver.entity.AccountEntity;
import bjit.ursa.authserver.entity.RoleEntity;
import bjit.ursa.authserver.exception.AccountAlreadyExists;
import bjit.ursa.authserver.model.APIResponse;
import bjit.ursa.authserver.model.LoginRequest;
import bjit.ursa.authserver.model.RegisterRequest;
import bjit.ursa.authserver.model.RegisterResponse;
import bjit.ursa.authserver.repositoty.AccountRepository;
import bjit.ursa.authserver.service.AccountService;
import bjit.ursa.authserver.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RoleService roleService;


    @Override
    public APIResponse register(RegisterRequest registerRequest) {
        Optional<AccountEntity> accountEntity= accountRepository.findByEmail(registerRequest.getEmail());
        if (accountEntity.isPresent()){
            throw new AccountAlreadyExists("The requested email "+ registerRequest.getEmail() +" already registered");
        }else {
            Set<RoleEntity> roles = new HashSet<>();
            registerRequest.getRoles().forEach(value-> roles.add(roleService.getRole(value)));
            AccountEntity accountEntity1= AccountEntity.builder()
                    .email(registerRequest.getEmail())
                    .password(registerRequest.getPassword())
                    .roles(roles)
                    .build();
            AccountEntity savedEntity= accountRepository.save(accountEntity1);
            return APIResponse.builder()
                    .data(new RegisterResponse("Successfully registered with the  email "+savedEntity.getEmail()+"."))
                    .build();
        }
    }

    @Override
    public APIResponse login(LoginRequest loginRequest) {
        return null;
    }
}
