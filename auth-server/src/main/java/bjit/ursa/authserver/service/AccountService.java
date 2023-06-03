package bjit.ursa.authserver.service;

import bjit.ursa.authserver.model.APIResponse;
import bjit.ursa.authserver.model.LoginRequest;
import bjit.ursa.authserver.model.RegisterRequest;

public interface AccountService {
    APIResponse register(RegisterRequest registerRequest);

    APIResponse login(LoginRequest loginRequest);
}
