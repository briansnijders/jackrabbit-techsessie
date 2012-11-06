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
import javax.security.auth.login.LoginException;

import nl.finalist.nosql.jackrabbit.util.RepositoryUtil;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CatalogMercedesIT {

	private static Session session;

	@BeforeClass
	public static void setUp() throws LoginException, RepositoryException {
		session = RepositoryUtil.getSession();
	}
	
	@Test
	public void mercedes_is_a_car() throws RepositoryException {
		Node mercedes = session.getNode("/cars/mercedes");
		assertThat(mercedes, notNullValue());
	}
	
	@Test
	public void mercedes_has_brand() throws RepositoryException {
		Node mercedes = session.getNode("/cars/mercedes");
		String brand = mercedes.getProperty("catalog:brand").getString();
		assertThat(brand, notNullValue());
	}
	
	@Test
	public void mercedes_has_model() throws RepositoryException {
		Node mercedes = session.getNode("/cars/mercedes");
		String model = mercedes.getProperty("catalog:model").getString();
		assertThat(model, notNullValue());
	}
	
	@Test
	public void mercedes_is_available_in_black() throws RepositoryException {
		Node mercedes = session.getNode("/cars/mercedes");
		String color = mercedes.getProperty("catalog:color").getString();
		assertThat(color, is("black"));
	}
	
	@Test
	public void mercedes_is_available_with_capacity_2000cc() throws RepositoryException {
		Node mercedes = session.getNode("/cars/mercedes");
		Value[] capacities = mercedes.getProperty("catalog:enginecapacity").getValues();
		
		boolean isFound = false;
		for(Value capacity : capacities) {
			isFound = capacity.getLong() == 2000 ? true : isFound;
		}
		assertThat(isFound, is(true));
	}
	
	@Test
	public void mercedes_is_available_with_capacity_3000cc() throws RepositoryException {
		Node mercedes = session.getNode("/cars/mercedes");
		Value[] capacities = mercedes.getProperty("catalog:enginecapacity").getValues();
			
		boolean isFound = false;
		for(Value capacity : capacities) {
			isFound = capacity.getLong() == 3000 ? true : isFound;
		}
		assertThat(isFound, is(true));
	}
		
	@Test
	public void mercedes_has_four_wheels() throws RepositoryException {
		Node mercedes = session.getNode("/cars/mercedes");
		NodeIterator nodeIterator = mercedes.getNodes("catalog:wheel");

		assertThat(nodeIterator.getSize(), is(5L));
	}
	
	@Test
	public void mercedes_has_spare_wheel() throws RepositoryException {
		Node mercedes = session.getNode("/cars/mercedes");
		NodeIterator nodeIterator = mercedes.getNodes("catalog:wheel");

		int numWheels = 0;
		while(nodeIterator.hasNext()) {
			Node wheel = nodeIterator.nextNode();
			String type = wheel.getProperty("catalog:wheeltype").getString();
			if(type.equals("spare")) numWheels++;
		}
		
		assertThat(numWheels, is(1));
	}
	
	@Test
	public void mercedes_has_two_front_wheels() throws RepositoryException {
		Node mercedes = session.getNode("/cars/mercedes");
		NodeIterator nodeIterator = mercedes.getNodes("catalog:wheel");

		int numWheels = 0;
		while(nodeIterator.hasNext()) {
			Node wheel = nodeIterator.nextNode();
			String type = wheel.getProperty("catalog:wheeltype").getString();
			if(type.equals("front")) numWheels++;
		}
		
		assertThat(numWheels, is(2));
	}

	@Test
	public void mercedes_has_two_rear_wheels() throws RepositoryException {
		Node mercedes = session.getNode("/cars/mercedes");
		NodeIterator nodeIterator = mercedes.getNodes("catalog:wheel");

		int numWheels = 0;
		while(nodeIterator.hasNext()) {
			Node wheel = nodeIterator.nextNode();
			String type = wheel.getProperty("catalog:wheeltype").getString();
			if(type.equals("rear")) numWheels++;
		}
		
		assertThat(numWheels, is(2));
	}


	@Test
	public void mercedes_has_four_doors() throws RepositoryException {
		Node mercedes = session.getNode("/cars/mercedes");
		NodeIterator nodeIterator = mercedes.getNodes("catalog:door");

		assertThat(nodeIterator.getSize(), is(4L));
	}

	@Test
	public void mercedes_has_trunk() throws RepositoryException {
		Node mercedes = session.getNode("/cars/mercedes");
		Node trunk = mercedes.getNode("catalog:trunk");
	
		assertThat(trunk, notNullValue());
	}

	@Test
	public void mercedes_can_be_locked() throws RepositoryException {
		Node mercedes = session.getNode("/cars/mercedes");
		LockManager lm = session.getWorkspace().getLockManager();
		
		assertThat(lm.isLocked(mercedes.getPath()), is(false));
		lm.lock(mercedes.getPath(),true, true, 60, session.getUserID());
		assertThat(lm.isLocked(mercedes.getPath()), is(true));
		lm.unlock(mercedes.getPath());
		assertThat(lm.isLocked(mercedes.getPath()), is(false));
	}

	@Test
	public void mercedes_can_be_referenced() throws RepositoryException {
		Node mercedes = session.getNode("/cars/mercedes");
		assertThat(mercedes.getUUID(), notNullValue());	
	}

	@AfterClass
	public static void tearDown() {
		session.logout();
	}
}