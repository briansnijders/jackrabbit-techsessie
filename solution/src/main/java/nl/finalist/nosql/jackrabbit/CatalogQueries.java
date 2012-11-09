package nl.finalist.nosql.jackrabbit;

public class CatalogQueries {
	// Query description
	// Find all the nodes of type nt:unstructured, which contain an attribute 'catalog:brand'
	// This is to illustrate we can query all custom properties, yielding a result containing the brand AMONGST other properties
	public static final String ALL_CAR_BRANDS_XPATH 	= "//element(*,nt:unstructured)[@catalog:brand]";
	public static final String ALL_CAR_BRANDS_SQL 		= "SELECT * FROM nt:unstructured WHERE catalog:brand IS NOT NULL";
	
	// Query description
	// Find the values of property 'catalog:color' in nodes of type nt:unstructured, 
	// which contain an attribute 'catalog:color'
	// This is to illustrate we can query a specific single-value custom property, yielding a result ONLY the brand
	public static final String ALL_CAR_COLORS_XPATH 	= "//element(*,nt:unstructured)[@catalog:color]/@catalog:color";
	public static final String ALL_CAR_COLORS_SQL 		= "SELECT catalog:color FROM nt:unstructured WHERE catalog:color IS NOT NULL";

	// Query description
	// Find the values of property 'catalog:enginecapacity' in nodes of type nt:unstructured, 
	// which contain an attribute 'catalog:enginecapacity'
	// This is to illustrate we can query a specific multi-value custom property, yielding a result ONLY the engine capacities
	public static final String ALL_CAR_ENGINES_XPATH 	= "//element(*,nt:unstructured)[@catalog:enginecapacity]/@catalog:enginecapacity";
	public static final String ALL_CAR_ENGINES_SQL 		= "SELECT catalog:enginecapacity FROM nt:unstructured WHERE catalog:enginecapacity IS NOT NULL";

	// Query description
	// Find all nodes having a JCR UUID.
	// This is to illustrate we can query on internal repository-managed metadata
	public static final String ALL_UUIDS_XPATH      	= "//element(*,nt:unstructured)[@jcr:uuid]/@jcr:uuid";
	public static final String ALL_UUIDS_SQL        	= "SELECT * FROM nt:unstructured WHERE jcr:uuid IS NOT NULL";
}
