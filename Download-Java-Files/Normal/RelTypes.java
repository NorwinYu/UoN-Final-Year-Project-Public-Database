package wikipedia.neo4j;

import org.neo4j.graphdb.RelationshipType;

public enum RelTypes implements RelationshipType
{
    WROTE, REVISION_OF, PARENT_OF
}
