package rs.ac.uns.acs.nais.workflow_service.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Transport")
public class Transport {

    @Id
    private Long id;

    private TransportType type;
    private String company;
    private Double rating;

    public Transport() {}

    public Transport(Long id, TransportType type, String company, Double rating) {
        this.id = id;
        this.type = type;
        this.company = company;
        this.rating = rating;
    }

    public Long getId() { return id; }
    public TransportType getType() { return type; }
    public String getCompany() { return company; }
    public Double getRating() { return rating; }

    public void setId(Long id) { this.id = id; }
    public void setType(TransportType type) { this.type = type; }
    public void setCompany(String company) { this.company = company; }
    public void setRating(Double rating) { this.rating = rating; }
}