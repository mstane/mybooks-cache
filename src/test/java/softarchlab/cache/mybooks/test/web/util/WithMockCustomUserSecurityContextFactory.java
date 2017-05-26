package softarchlab.cache.mybooks.test.web.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import softarchlab.cache.mybooks.domain.Reader;
import softarchlab.cache.mybooks.security.UserDetailsImpl;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser mockUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Reader reader = new Reader();
        reader.setId(mockUser.id());
        reader.setEmail(mockUser.email());
        reader.setPassword(mockUser.password());
        reader.setSystemRole(mockUser.systemRole());
        reader.setUsername(mockUser.username());

        UserDetailsImpl principal = new UserDetailsImpl(reader);
        Authentication auth = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(),
                principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
    
}