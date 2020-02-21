package xyz.thetbw.blog.data.model;

public class DefaultMsgModel {
    private boolean successful=true;
    private String massage;

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
