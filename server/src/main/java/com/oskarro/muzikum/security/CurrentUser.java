package com.oskarro.muzikum.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.lang.annotation.*;

/**
 * It's special meta-annotation for reducing the dependency of Spring Security
 *
 * Spring security provides an annotation called @AuthenticationPrincipal
 * to access the currently authenticated user in the controllers.
 * */

@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {
}
