package rs.ac.uns.acs.nais.workflow_service.dto;

import java.util.List;

public class SameTransportArrangementDTO {

    private String administrator;
    private String adminArrangement;
    private String transportCompany;
    private String transportType;
    private List<String> arrangementsWithSameTransport;
    private Long numberOfArrangements;

    public SameTransportArrangementDTO() {
    }

    public SameTransportArrangementDTO(String administrator,
                                       String adminArrangement,
                                       String transportCompany,
                                       String transportType,
                                       List<String> arrangementsWithSameTransport,
                                       Long numberOfArrangements) {
        this.administrator = administrator;
        this.adminArrangement = adminArrangement;
        this.transportCompany = transportCompany;
        this.transportType = transportType;
        this.arrangementsWithSameTransport = arrangementsWithSameTransport;
        this.numberOfArrangements = numberOfArrangements;
    }

    public String getAdministrator() {
        return administrator;
    }

    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }

    public String getAdminArrangement() {
        return adminArrangement;
    }

    public void setAdminArrangement(String adminArrangement) {
        this.adminArrangement = adminArrangement;
    }

    public String getTransportCompany() {
        return transportCompany;
    }

    public void setTransportCompany(String transportCompany) {
        this.transportCompany = transportCompany;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public List<String> getArrangementsWithSameTransport() {
        return arrangementsWithSameTransport;
    }

    public void setArrangementsWithSameTransport(List<String> arrangementsWithSameTransport) {
        this.arrangementsWithSameTransport = arrangementsWithSameTransport;
    }

    public Long getNumberOfArrangements() {
        return numberOfArrangements;
    }

    public void setNumberOfArrangements(Long numberOfArrangements) {
        this.numberOfArrangements = numberOfArrangements;
    }
}