package rs.ac.uns.acs.nais.recommendation_service.dto;

public class ViewedRequestDTO {

    private Long userId;
    private Long arrangementId;
    private String viewedAt;


    public ViewedRequestDTO() {
    }

    public ViewedRequestDTO(Long userId, Long arrangementId, String viewedAt) {
        this.userId = userId;
        this.arrangementId = arrangementId;
        this.viewedAt = viewedAt;

    }

    public Long getUserId() {
        return userId;
    }

    public Long getArrangementId() {
        return arrangementId;
    }

    public String getViewedAt() {
        return viewedAt;
    }



    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setArrangementId(Long arrangementId) {
        this.arrangementId = arrangementId;
    }

    public void setViewedAt(String viewedAt) {
        this.viewedAt = viewedAt;
    }


}