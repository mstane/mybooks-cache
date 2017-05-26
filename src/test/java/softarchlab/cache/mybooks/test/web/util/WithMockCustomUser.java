package softarchlab.cache.mybooks.test.web.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

import softarchlab.cache.mybooks.enums.SystemRole;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    long id() default (long) 1;

    String email() default "agarf.rac0@example.com";

    String password() default "Mb.1234";

    SystemRole systemRole() default SystemRole.COMMON;

    String username() default "Agarf Rac0";

}