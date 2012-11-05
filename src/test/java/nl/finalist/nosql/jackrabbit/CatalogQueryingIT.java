package nl.finalist.nosql.jackrabbit;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIn.isIn;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.query.QueryResult;
import javax.security.auth.login.LoginException;

import nl.finalist.nosql.jackrabbit.util.QueryUtil;
import nl.finalist.nosql.jackrabbit.util.RepositoryUtil;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CatalogQueryingIT {

	private static Session session;

	@BeforeClass
	public static void setUp() throws LoginException, RepositoryException {
		session = RepositoryUtil.getSession();
	}
	
	@Test
	public void find_all_cars_with_a_brand_by_xpath() throws RepositoryException {
		String statement = CatalogQueries.ALL_CAR_BRANDS_XPATH;
		QueryResult result = QueryUtil.getResultByXPath(session, statement);
		
		assertThat(QueryUtil.toNodeList(result), hasSize(2));
	}
	
	@Test
	public void find_all_cars_with_a_brand_by_sql() throws RepositoryException {
		String statement = CatalogQueries.ALL_CAR_BRANDS_SQL;
		QueryResult result = QueryUtil.getResultBySql(session, statement);
	
		assertThat(QueryUtil.toNodeList(result), hasSize(2));
	}
	
	@Test
	public void find_all_colors_by_xpath() throws RepositoryException {
		String statement = CatalogQueries.ALL_CAR_COLORS_XPATH;
		QueryResult result = QueryUtil.getResultByXPath(session, statement);
	
		Set<String> colors = new HashSet<String>();
		for(Node node : QueryUtil.toNodeList(result)) {
			colors.add(node.getProperty("catalog:color").getString());
		}
		
		assertThat("red", isIn(colors));
		assertThat("black", isIn(colors));
	}
	
	@Test
	public void find_all_colors_by_sql() throws RepositoryException {
		String statement = CatalogQueries.ALL_CAR_COLORS_SQL;
		QueryResult result = QueryUtil.getResultBySql(session, statement);
		
		Set<String> colors = new HashSet<String>();
		for(Node node : QueryUtil.toNodeList(result)) {
			colors.add(node.getProperty("catalog:color").getString());
		}
		
		assertThat("red", isIn(colors));
		assertThat("black", isIn(colors));
	}
	
	@Test
	public void find_all_engines_by_xpath() throws RepositoryException {
		String statement = CatalogQueries.ALL_CAR_ENGINES_XPATH;
		QueryResult result = QueryUtil.getResultByXPath(session, statement);
	
		Set<Long> capacities = new HashSet<Long>();
		for(Node node : QueryUtil.toNodeList(result)) {
			Value[] values = node.getProperty("catalog:enginecapacity").getValues();
			for(Value value : values) capacities.add(value.getLong());
		}
		
		assertThat(2000L, isIn(capacities));
		assertThat(3000L, isIn(capacities));
		assertThat(3500L, isIn(capacities));
	}
	
	@Test
	public void find_all_engines_by_sql() throws RepositoryException {
		String statement = CatalogQueries.ALL_CAR_ENGINES_SQL;
		QueryResult result = QueryUtil.getResultBySql(session, statement);
		
		Set<Long> capacities = new HashSet<Long>();
		for(Node node : QueryUtil.toNodeList(result)) {
			Value[] values = node.getProperty("catalog:enginecapacity").getValues();
			for(Value value : values) capacities.add(value.getLong());
		}
		
		assertThat(2000L, isIn(capacities));
		assertThat(3000L, isIn(capacities));
		assertThat(3500L, isIn(capacities));
	}
	
	@Test
	public void find_all_uuids_by_xpath() throws RepositoryException {
		String statement = CatalogQueries.ALL_UUIDS_XPATH;
		QueryResult result = QueryUtil.getResultByXPath(session, statement);
	
		Set<String> uuids = new HashSet<String>();
		for(Node node : QueryUtil.toNodeList(result)) {
			uuids.add(node.getIdentifier());
		}
		
		assertThat(uuids, hasSize(2));
	}
	
	@Test
	public void find_all_uuids_by_sql() throws RepositoryException {
		String statement = CatalogQueries.ALL_UUIDS_SQL;
		QueryResult result = QueryUtil.getResultBySql(session, statement);
		
		Set<String> uuids = new HashSet<String>();
		for(Node node : QueryUtil.toNodeList(result)) {
			uuids.add(node.getIdentifier());
		}
		
		assertThat(uuids, hasSize(2));
	}
	
	@AfterClass
	public static void tearDown() {
		session.logout();
	}
}