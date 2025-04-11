package ru.abdurahman.SchedulerService.dto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class SummaryDto {
    private String receiverEmail;

    private Integer completedTodayCount;
    private List<String> completedTodayTitles;

    private Integer notCompletedCount;
    private List<String> notCompletedTitles;

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public Integer getCompletedTodayCount() {
        return completedTodayCount;
    }

    public void setCompletedTodayCount(Integer completedTodayCount) {
        this.completedTodayCount = completedTodayCount;
    }

    public List<String> getCompletedTodayTitles() {
        return completedTodayTitles;
    }

    public void setCompletedTodayTitles(List<String> completedTodayTitles) {
        this.completedTodayTitles = completedTodayTitles;
    }

    public Integer getNotCompletedCount() {
        return notCompletedCount;
    }

    public void setNotCompletedCount(Integer notCompletedCount) {
        this.notCompletedCount = notCompletedCount;
    }

    public List<String> getNotCompletedTitles() {
        return notCompletedTitles;
    }

    public void setNotCompletedTitles(List<String> notCompletedTitles) {
        this.notCompletedTitles = notCompletedTitles;
    }
}