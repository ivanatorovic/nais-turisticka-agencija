package rs.ac.uns.acs.nais.workflow_service.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("Arrangement")
public class Arrangement {

    @Id
    private Long id;

    private String name;
    private String description;
    private String destination;

    @Relationship(type = "BASED_ON", direction = Relationship.Direction.OUTGOING)
    private Workflow workflow;

    @Relationship(type = "HAS_OFFER", direction = Relationship.Direction.OUTGOING)
    private List<Offer> offers;

    public Arrangement() {}

    public Arrangement(Long id, String name, String description, String destination) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.destination = destination;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDestination() {
        return destination;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}