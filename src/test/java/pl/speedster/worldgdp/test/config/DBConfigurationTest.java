package pl.speedster.worldgdp.test.config;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

@SpringJUnitConfig(TestDBConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DBConfigurationTest {

    @Autowired
    DataSource dataSource;

    @Test
    public void testDbConfiguration() {
        assertNotNull(dataSource);
    }
}
