package rs.ac.uns.acs.nais.workflow_service.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Accommodation")
public class Accommodation {

    @Id
    private Long id;

    private String name;
    private AccommodationType type;
    private Double rating;

    public Accommodation() {
    }

    public Accommodation(Long id, String name, AccommodationType type, Double rating) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AccommodationType getType() {
        return type;
    }

    public Double getRating() {
        return rating;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(AccommodationType type) {
        this.type = type;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}