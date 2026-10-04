package com.homelab.vulbankapi;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

class VulnBankControllerSearchTest {

    @Test
    void searchBindsNameAsParameterInsteadOfConcatenating() {
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
        VulnBankController controller = new VulnBankController();
        ReflectionTestUtils.setField(controller, "jdbcTemplate", jdbcTemplate);

        String name = "x' OR '1'='1";
        controller.search(name);

        verify(jdbcTemplate).queryForList(eq("SELECT * FROM user WHERE username = ?"), eq(name));
        verify(jdbcTemplate, never()).queryForList(anyString());
    }
}
