package rs.ac.uns.acs.nais.recommendation_service.dto;

public class ViewedRequestDTO {

    private Long userId;
    private Long arrangementId;



    public ViewedRequestDTO() {
    }

    public ViewedRequestDTO(Long userId, Long arrangementId) {
        this.userId = userId;
        this.arrangementId = arrangementId;


    }

    public Long getUserId() {
        return userId;
    }

    public Long getArrangementId() {
        return arrangementId;
    }





    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setArrangementId(Long arrangementId) {
        this.arrangementId = arrangementId;
    }




}