package nl.finalist.nosql.jackrabbit.util;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.query.Row;
import javax.jcr.query.RowIterator;

public class QueryUtil {

	public static QueryResult getResultByXPath(Session session, String statement) throws RepositoryException {
		QueryManager qm = session.getWorkspace().getQueryManager();
		Query query = qm.createQuery(statement,Query.XPATH);
		
		return query.execute();
	}
	
	public static QueryResult getResultBySql(Session session, String statement) throws RepositoryException {
		QueryManager qm = session.getWorkspace().getQueryManager();
		Query query = qm.createQuery(statement,Query.SQL);
		return query.execute();
	}
	
	public static List<Node> toNodeList(QueryResult result) throws RepositoryException {
		List<Node> nodes = new ArrayList<Node>();
		NodeIterator nodeIterator = result.getNodes();
		while(nodeIterator.hasNext()) nodes.add(nodeIterator.nextNode());
		
		return nodes;
	}
	
	public static List<Row> toRowList(QueryResult result) throws RepositoryException {
		List<Row> rows = new ArrayList<Row>();
		RowIterator rowIterator = result.getRows();
		while(rowIterator.hasNext()) rows.add(rowIterator.nextRow());
		
		return rows;
	}
}
