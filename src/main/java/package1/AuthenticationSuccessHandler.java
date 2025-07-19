package package1;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        boolean isEtudiant = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ETUDIANT"));
        boolean isProf = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_PROF"));

        if (isAdmin) {
            setDefaultTargetUrl("/etudiants/etudiant");
        } else if (isEtudiant) {
            setDefaultTargetUrl("/home");
        } else if (isProf) {
            setDefaultTargetUrl("/etudiants/etudiant");
        } else {
            // Fallback URL if no roles match
            setDefaultTargetUrl("/home");
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}