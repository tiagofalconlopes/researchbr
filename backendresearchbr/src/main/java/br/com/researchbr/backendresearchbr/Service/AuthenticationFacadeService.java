package br.com.researchbr.backendresearchbr.Service;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacadeService {
    Authentication getAuthentication();
}
