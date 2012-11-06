package nl.finalist.nosql.jackrabbit;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import nl.finalist.nosql.jackrabbit.util.RepositoryUtil;

public class CatalogBootstrapper {

public static void main(String[] args) throws Exception {
Session session = RepositoryUtil.getSession();

try {
// Get hold of the root node of this session's workspace
Node rootNode = session.getRootNode();

// Create a node under which the cars are stored. This is a direct child
// of the root node. The root node has absolute path "/", the cars node
// has relative path "cars", thus the absolute path of the cars node will be
// "/cars"
Node carsNode = rootNode.addNode("cars");

// Bootstrap the Mercedes
Node mercedes = loadMercedes(carsNode, session);

// Bootstrap the Ferrari
Node ferrari = loadFerrari(carsNode, session);

// Ask JackRabbit to save the session, thus committing and persisting changes
session.save();

// Dump repository contents by recursively walking the hierarchy
RepositoryUtil.dump(rootNode);
} finally {
session.logout();
}
}

private static Node loadMercedes(Node parentNode, Session session) throws RepositoryException {
//TODO
// Fix the implementation which makes the tests pass!
// Create a Mercedes and return it. Returns null for now.
Node mercedes = null;

return mercedes;
}

private static Node loadFerrari(Node parentNode, Session session) throws RepositoryException {
//TODO
// Fix the implementation which makes the tests pass!
// Create a Ferrari and return it. Returns null for now.
Node ferrari = null;

return ferrari;
}
}
