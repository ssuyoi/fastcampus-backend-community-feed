package org.fastcampus.acceptance.utils;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class AcceptanceTestTemplate {

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @Autowired
    private DataLoader loader;

    @BeforeEach
    public void init() {
        databaseCleanUp.execute();
        loader.loadData();
    }

    protected void cleanUp() {
        databaseCleanUp.execute();
    }

    protected String getEmailToken(String email) {
        return loader.getEmailToken(email);
    }

}
