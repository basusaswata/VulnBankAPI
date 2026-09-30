package com.homelab.vulbankapi;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;

class VulnBankControllerTest {

    @Test
    void doesNotExposeEnvironmentDebugEndpoint() {
        boolean exposed = Arrays.stream(VulnBankController.class.getDeclaredMethods())
                .map(m -> m.getAnnotation(GetMapping.class))
                .filter(a -> a != null)
                .flatMap(a -> Arrays.stream(a.value()))
                .anyMatch(p -> p.contains("debug/env"));
        assertFalse(exposed, "/api/debug/env must not be exposed");
    }

    @Test
    void noHandlerReturnsProcessEnvironment() {
        boolean returnsEnv = Arrays.stream(VulnBankController.class.getDeclaredMethods())
                .map(Method::getName)
                .anyMatch("getEnv"::equals);
        assertFalse(returnsEnv, "no handler should return System.getenv()");
    }
}
