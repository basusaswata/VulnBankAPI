package com.homelab.vulbankapi;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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

        verify(jdbcTemplate).queryForList("SELECT * FROM user WHERE username = ?", name);
        verifyNoMoreInteractions(jdbcTemplate);
    }
}
