package jdbc;

import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

public class CbManagerTest {

    @Test
    public void testConnect() throws SQLException {
        DbManager dbManager = DbManager.getInstance();
        Assert.assertNotNull(dbManager.getConnection());
    }
}
