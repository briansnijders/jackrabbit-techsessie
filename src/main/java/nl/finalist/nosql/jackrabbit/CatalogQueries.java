package nl.finalist.nosql.jackrabbit;

public class CatalogQueries {
	//TODO 
	//		Fix the queries which make the tests pass! 
	
	// Query description
	// Find all the nodes of type nt:unstructured, which contain an attribute 'catalog:brand'
	// This is to illustrate we can query all custom properties, yielding a result containing the brand AMONGST other properties
	public static final String ALL_CAR_BRANDS_XPATH 	= "";
	public static final String ALL_CAR_BRANDS_SQL 		= "";
	
	// Query description
	// Find the values of property 'catalog:color' in nodes of type nt:unstructured, 
	// which contain an attribute 'catalog:color'
	// This is to illustrate we can query a specific single-value custom property, yielding a result with ONLY the brand
	public static final String ALL_CAR_COLORS_XPATH 	= "";
	public static final String ALL_CAR_COLORS_SQL 		= "";

	// Query description
	// Find the values of property 'catalog:enginecapacity' in nodes of type nt:unstructured, 
	// which contain an attribute 'catalog:enginecapacity'
	// This is to illustrate we can query a specific multi-value custom property, yielding a result with ONLY the engine capacities
	public static final String ALL_CAR_ENGINES_XPATH 	= "";
	public static final String ALL_CAR_ENGINES_SQL 		= "";

	// Query description
	// Find all nodes having a JCR UUID.
	// This is to illustrate we can query on internal repository-managed metadata
	public static final String ALL_UUIDS_XPATH      	= "";
	public static final String ALL_UUIDS_SQL        	= "";
}
