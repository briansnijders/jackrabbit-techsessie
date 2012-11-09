package nl.finalist.nosql.jackrabbit;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFactory;

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
		Node mercedes = parentNode.addNode("mercedes");

		// Add the brand
		mercedes.setProperty("catalog:brand", "Mercedes Benz");

		// Add the model
		mercedes.setProperty("catalog:model", "SLK 350");

		// Add the color
		mercedes.setProperty("catalog:color", "black");

		// Add the engine capacities using a JCR ValueFactory
		ValueFactory valueFactory = session.getValueFactory();
		Value[] capacities = new Value[] { valueFactory.createValue(2000), valueFactory.createValue(3000) };
		mercedes.setProperty("catalog:enginecapacity", capacities);

		// Add the wheels
		Node frontWheel1 = mercedes.addNode("catalog:wheel");
		frontWheel1.setProperty("catalog:wheeltype", "front");
		Node frontWheel2 = mercedes.addNode("catalog:wheel");
		frontWheel2.setProperty("catalog:wheeltype", "front");
		Node rearWheel1 = mercedes.addNode("catalog:wheel");
		rearWheel1.setProperty("catalog:wheeltype", "rear");
		Node rearWheel2 = mercedes.addNode("catalog:wheel");
		rearWheel2.setProperty("catalog:wheeltype", "rear");
		Node spareWheel = mercedes.addNode("catalog:wheel");
		spareWheel.setProperty("catalog:wheeltype", "spare");

		// Add the doors
		Node door1 = mercedes.addNode("catalog:door");
		Node door2 = mercedes.addNode("catalog:door");
		Node door3 = mercedes.addNode("catalog:door");
		Node door4 = mercedes.addNode("catalog:door");

		// Add the trunk
		Node trunk = mercedes.addNode("catalog:trunk");

		// Allow the node to be locked by the JCR LockManager
		mercedes.addMixin("mix:lockable");

		// Allow the node to be referenced using UUID-based retrieval operations
		mercedes.addMixin("mix:referenceable");

		return mercedes;
	}

	private static Node loadFerrari(Node parentNode, Session session) throws RepositoryException {
		Node ferrari = parentNode.addNode("ferrari");

		// Add the brand
		ferrari.setProperty("catalog:brand", "Ferrari");

		// Add the model
		ferrari.setProperty("catalog:model", "458 Spider");

		// Add the color
		ferrari.setProperty("catalog:color", "red");

		// Add the engine capacities using a JCR ValueFactory
		ValueFactory valueFactory = session.getValueFactory();
		Value[] capacity = new Value[]{valueFactory.createValue(3500)};
		ferrari.setProperty("catalog:enginecapacity", capacity);
		
		// Add the wheels
		Node frontWheel1 = ferrari.addNode("catalog:wheel");
		frontWheel1.setProperty("catalog:wheeltype", "front");
		Node frontWheel2 = ferrari.addNode("catalog:wheel");
		frontWheel2.setProperty("catalog:wheeltype", "front");
		Node rearWheel1 = ferrari.addNode("catalog:wheel");
		rearWheel1.setProperty("catalog:wheeltype", "rear");
		Node rearWheel2 = ferrari.addNode("catalog:wheel");
		rearWheel2.setProperty("catalog:wheeltype", "rear");

		// Add the doors
		Node door1 = ferrari.addNode("catalog:door");
		Node door2 = ferrari.addNode("catalog:door");

		// Allow the node to be locked by the JCR LockManager
		ferrari.addMixin("mix:lockable");

		// Allow the node to be referenced using UUID-based retrieval operations
		ferrari.addMixin("mix:referenceable");

		// Allow the node to be versioned
		ferrari.addMixin("mix:versionable");

		// Have the repository record the node creation
		ferrari.addMixin("mix:created");

		// Have the repository track the node modification
		ferrari.addMixin("mix:lastModified");

		return ferrari;
	}
}