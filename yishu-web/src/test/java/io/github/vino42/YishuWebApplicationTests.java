package io.github.vino42;

import io.github.vino42.web.YishuWebApplication;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles({"prod"})
@SpringBootTest(classes = YishuWebApplication.class)
class YishuWebApplicationTests {

    @Autowired
    StringEncryptor stringEncryptor;

    @Test
    void contextLoads() {
    }

}
