package rs.ac.uns.acs.nais.workflow_service.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("Offer")
public class Offer {

    @Id
    private Long id;

    private String startDate;
    private String endDate;
    private Double priceForChildren;
    private Double priceForAdults;

    @Relationship(type = "HAS_ACCOMMODATION", direction = Relationship.Direction.OUTGOING)
    private Accommodation accommodation;

    @Relationship(type = "HAS_TRANSPORT", direction = Relationship.Direction.OUTGOING)
    private Transport transport;

    public Offer() {
    }

    public Offer(Long id, String startDate, String endDate, Double priceForChildren, Double priceForAdults) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceForChildren = priceForChildren;
        this.priceForAdults = priceForAdults;
    }

    public Long getId() {
        return id;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Double getPriceForChildren() {
        return priceForChildren;
    }

    public Double getPriceForAdults() {
        return priceForAdults;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setPriceForChildren(Double priceForChildren) {
        this.priceForChildren = priceForChildren;
    }

    public void setPriceForAdults(Double priceForAdults) {
        this.priceForAdults = priceForAdults;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

}