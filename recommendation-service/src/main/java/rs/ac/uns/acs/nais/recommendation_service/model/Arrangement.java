package rs.ac.uns.acs.nais.recommendation_service.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
public class Arrangement {

    @Id
    private Long id;

    private String name;
    private String description;
    private Double price;
    private Integer durationDays;

    @Relationship(type = "LOCATED_IN", direction = Relationship.Direction.OUTGOING)
    private Destination destination;

    @Relationship(type = "HAS_TAG", direction = Relationship.Direction.OUTGOING)
    private List<Tag> tags;

    public Arrangement() {
    }

    public Arrangement(Long id, String name, String description, Double price, Integer durationDays, Destination destination, List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.durationDays = durationDays;
        this.destination = destination;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Destination getDestination() {
        return destination;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}