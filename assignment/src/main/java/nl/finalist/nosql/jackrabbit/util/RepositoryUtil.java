package nl.finalist.nosql.jackrabbit.util;

import javax.jcr.LoginException;
import javax.jcr.NamespaceException;
import javax.jcr.NamespaceRegistry;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Value;
import javax.jcr.Workspace;

import org.apache.jackrabbit.core.TransientRepository;

public class RepositoryUtil {

	public static Session getSession() throws LoginException, RepositoryException {
		Repository repository = new TransientRepository();
		Session session = repository.login(new SimpleCredentials("username","password".toCharArray()));
			
		Workspace workspace = session.getWorkspace();
		NamespaceRegistry registry = workspace.getNamespaceRegistry();
		
		try {
			registry.registerNamespace("catalog", "http://jackrabbit.finalist.nl/catalog/1.0");
		} catch (NamespaceException nse) {
			// Swallow for now, apparently the namespace is already present :-)
		}
		
		return session;
	}

	public static void dump(Node node) throws RepositoryException { 
		System.out.println(node.getPath()); 
		// Skip the virtual (and large!) jcr:system subtree 
		if (node.getName().equals("jcr:system")) { 
			return; 
		} 

		// Then output the properties 
		PropertyIterator properties = node.getProperties(); 
		while (properties.hasNext()) { 
			Property property = properties.nextProperty(); 
			
			if (property.getDefinition().isMultiple()) {  
				Value[] values = property.getValues(); 
				
				for (int i = 0; i < values.length; i++) { 
					System.out.println(property.getPath() + " = " + values[i].getString()); 
				} 
			} else { 
				// A single-valued property 
				System.out.println(property.getPath() + " = " + property.getString()); 
			} 
		}
		
		// Finally output all the child nodes recursively 
		NodeIterator nodes = node.getNodes(); 
		while (nodes.hasNext()) { 
			dump(nodes.nextNode()); 
		} 
	} 
}
