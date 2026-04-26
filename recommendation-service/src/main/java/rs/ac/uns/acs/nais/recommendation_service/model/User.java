package rs.ac.uns.acs.nais.recommendation_service.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
public class User {

    @Id
    private Long id;

    private String username;
    private String email;

    @Relationship(type = "VIEWED", direction = Relationship.Direction.OUTGOING)
    private List<Viewed> viewedArrangements;

    @Relationship(type = "BOOKED", direction = Relationship.Direction.OUTGOING)
    private List<Booked> bookedArrangements;

    public User() {
    }

    public User(Long id, String username, String email, List<Viewed> viewedArrangements, List<Booked> bookedArrangements) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.viewedArrangements = viewedArrangements;
        this.bookedArrangements = bookedArrangements;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public List<Viewed> getViewedArrangements() {
        return viewedArrangements;
    }

    public List<Booked> getBookedArrangements() {
        return bookedArrangements;
    }

    public void setViewedArrangements(List<Viewed> viewedArrangements) {
        this.viewedArrangements = viewedArrangements;
    }

    public void setBookedArrangements(List<Booked> bookedArrangements) {
        this.bookedArrangements = bookedArrangements;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}