package nl.finalist.nosql.jackrabbit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.lock.LockManager;
import javax.jcr.version.Version;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionManager;
import javax.security.auth.login.LoginException;

import nl.finalist.nosql.jackrabbit.util.RepositoryUtil;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CatalogFerrariIT {

	private static Session session;

	@BeforeClass
	public static void setUp() throws LoginException, RepositoryException {
		session = RepositoryUtil.getSession();
	}
	
	@Test
	public void ferrari_is_a_car() throws RepositoryException {
		Node ferrari = session.getNode("/cars/ferrari");
		assertThat(ferrari, notNullValue());
	}
	
	@Test
	public void ferrari_has_brand() throws RepositoryException {
		Node ferrari = session.getNode("/cars/ferrari");
		String brand = ferrari.getProperty("catalog:brand").getString();
		assertThat(brand, notNullValue());
	}
	
	@Test
	public void ferrari_has_model() throws RepositoryException {
		Node ferrari = session.getNode("/cars/ferrari");
		String model = ferrari.getProperty("catalog:model").getString();
		assertThat(model, notNullValue());
	}
	
	@Test
	public void ferrari_is_available_in_black() throws RepositoryException {
		Node ferrari = session.getNode("/cars/ferrari");
		String color = ferrari.getProperty("catalog:color").getString();
		assertThat(color, is("red"));
	}
	
	@Test
	public void ferrari_is_available_with_capacity_3500cc() throws RepositoryException {
		Node ferrari = session.getNode("/cars/ferrari");
		Value[] capacities = ferrari.getProperty("catalog:enginecapacity").getValues();
		
		boolean isFound = false;
		for(Value capacity : capacities) {
			isFound = capacity.getLong() == 3500 ? true : isFound;
		}
		assertThat(isFound, is(true));
	}
		
	@Test
	public void ferrari_has_four_wheels() throws RepositoryException {
		Node ferrari = session.getNode("/cars/ferrari");
		NodeIterator nodeIterator = ferrari.getNodes("catalog:wheel");

		assertThat(nodeIterator.getSize(), is(4L));
	}
	
	@Test
	public void ferrari_has_no_spare_wheel() throws RepositoryException {
		Node ferrari = session.getNode("/cars/ferrari");
		NodeIterator nodeIterator = ferrari.getNodes("catalog:wheel");

		int numWheels = 0;
		while(nodeIterator.hasNext()) {
			Node wheel = nodeIterator.nextNode();
			String type = wheel.getProperty("catalog:wheeltype").getString();
			if(type.equals("spare")) numWheels++;
		}
		
		assertThat(numWheels, is(0));
	}
	
	@Test
	public void ferrari_has_two_front_wheels() throws RepositoryException {
		Node ferrari = session.getNode("/cars/ferrari");
		NodeIterator nodeIterator = ferrari.getNodes("catalog:wheel");

		int numWheels = 0;
		while(nodeIterator.hasNext()) {
			Node wheel = nodeIterator.nextNode();
			String type = wheel.getProperty("catalog:wheeltype").getString();
			if(type.equals("front")) numWheels++;
		}
		
		assertThat(numWheels, is(2));
	}

	@Test
	public void ferrari_has_two_rear_wheels() throws RepositoryException {
		Node ferrari = session.getNode("/cars/ferrari");
		NodeIterator nodeIterator = ferrari.getNodes("catalog:wheel");

		int numWheels = 0;
		while(nodeIterator.hasNext()) {
			Node wheel = nodeIterator.nextNode();
			String type = wheel.getProperty("catalog:wheeltype").getString();
			if(type.equals("rear")) numWheels++;
		}
		
		assertThat(numWheels, is(2));
	}


	@Test
	public void ferrari_has_two_doors() throws RepositoryException {
		Node ferrari = session.getNode("/cars/ferrari");
		NodeIterator nodeIterator = ferrari.getNodes("catalog:door");

		assertThat(nodeIterator.getSize(), is(2L));
	}

	@Test
	public void ferrari_can_be_locked() throws RepositoryException {
		Node ferrari = session.getNode("/cars/ferrari");
		LockManager lm = session.getWorkspace().getLockManager();
		
		assertThat(lm.isLocked(ferrari.getPath()), is(false));
		lm.lock(ferrari.getPath(),true, true, 60, session.getUserID());
		assertThat(lm.isLocked(ferrari.getPath()), is(true));
		lm.unlock(ferrari.getPath());
		assertThat(lm.isLocked(ferrari.getPath()), is(false));
	}

	@Test
	public void ferrari_can_be_referenced() throws RepositoryException {
		Node ferrari = session.getNode("/cars/ferrari");
		assertThat(ferrari.getUUID(), notNullValue());	
	}
	
	@Test
	public void ferrari_can_be_versioned() throws RepositoryException {
		Node ferrari = session.getNode("/cars/ferrari");
		VersionManager vm = session.getWorkspace().getVersionManager();

		Version base = vm.getBaseVersion(ferrari.getPath());
		assertThat(base, notNullValue());	
		VersionHistory history = vm.getVersionHistory(ferrari.getPath());
		assertThat(history, notNullValue());	
	}
	
	@Test
	public void ferrari_has_creation_information() throws RepositoryException {
		Node ferrari = session.getNode("/cars/ferrari");
		
		assertThat(ferrari.getProperty("jcr:createdBy"), notNullValue());	
		assertThat(ferrari.getProperty("jcr:created"), notNullValue());
	}
	
	@Test
	public void ferrari_has_modification_information() throws RepositoryException {
		Node ferrari = session.getNode("/cars/ferrari");
		
		assertThat(ferrari.getProperty("jcr:lastModifiedBy"), notNullValue());	
		assertThat(ferrari.getProperty("jcr:lastModified"), notNullValue());
	}

	@AfterClass
	public static void tearDown() {
		session.logout();
	}
}