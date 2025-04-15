package pl.edu.pwr.library;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LibraryApplicationTest {

	@Autowired
	private DataSource dataSource;

	@Test
	public void testConnection() throws Exception {
		try (Connection conn = dataSource.getConnection()) {
			assertFalse(conn.isClosed());
			System.out.println("✅ Połączenie działa!");
		}
	}
}
