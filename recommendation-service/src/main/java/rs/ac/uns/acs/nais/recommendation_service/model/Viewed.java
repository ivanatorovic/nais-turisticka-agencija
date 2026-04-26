package rs.ac.uns.acs.nais.recommendation_service.model;

import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class Viewed {

    @RelationshipId
    private Long id;

    @TargetNode
    private Arrangement arrangement;

    private String viewedAt;
    private Integer count;

    public Viewed() {
    }

    public Viewed(Long id, Arrangement arrangement, String viewedAt, Integer count) {
        this.id = id;
        this.arrangement = arrangement;
        this.viewedAt = viewedAt;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public Arrangement getArrangement() {
        return arrangement;
    }

    public void setArrangement(Arrangement arrangement) {
        this.arrangement = arrangement;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(String viewedAt) {
        this.viewedAt = viewedAt;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}