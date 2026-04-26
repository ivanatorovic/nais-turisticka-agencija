package rs.ac.uns.acs.nais.recommendation_service.model;

import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class Booked {

    @RelationshipId
    private Long id;

    @TargetNode
    private Arrangement arrangement;

    private String bookingDate;
    private Integer persons;
    private Double totalPrice;
    private Integer count;

    public Booked() {
    }

    public Booked(Long id, Arrangement arrangement, String bookingDate,
                  Integer persons, Double totalPrice, Integer count) {
        this.id = id;
        this.arrangement = arrangement;
        this.bookingDate = bookingDate;
        this.persons = persons;
        this.totalPrice = totalPrice;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Arrangement getArrangement() {
        return arrangement;
    }

    public void setArrangement(Arrangement arrangement) {
        this.arrangement = arrangement;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Integer getPersons() {
        return persons;
    }

    public void setPersons(Integer persons) {
        this.persons = persons;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}