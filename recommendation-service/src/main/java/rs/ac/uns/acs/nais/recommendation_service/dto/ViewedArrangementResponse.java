package rs.ac.uns.acs.nais.recommendation_service.dto;

public class ViewedArrangementResponse {
    private Long arrangementId;
    private String arrangementName;
    private String viewedAt;
    private Integer count;

    public Long getArrangementId() {
        return arrangementId;
    }

    public String getArrangementName() {
        return arrangementName;
    }

    public String getViewedAt() {
        return viewedAt;
    }

    public Integer getCount() {
        return count;
    }

    public void setArrangementId(Long arrangementId) {
        this.arrangementId = arrangementId;
    }

    public void setArrangementName(String arrangementName) {
        this.arrangementName = arrangementName;
    }

    public void setViewedAt(String viewedAt) {
        this.viewedAt = viewedAt;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
