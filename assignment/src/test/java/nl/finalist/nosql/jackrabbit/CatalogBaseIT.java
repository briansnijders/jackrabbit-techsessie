package nl.finalist.nosql.jackrabbit;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.security.auth.login.LoginException;

import nl.finalist.nosql.jackrabbit.util.RepositoryUtil;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CatalogBaseIT {

	private static Session session;

	@BeforeClass
	public static void setUp() throws LoginException, RepositoryException {
		session = RepositoryUtil.getSession();
	}

	@Test
	public void repository_has_cars() throws RepositoryException {
		Node cars = session.getNode("/cars");
		assertThat(cars, notNullValue());
	}
			
	@AfterClass
	public static void tearDown() {
		session.logout();
	}
}