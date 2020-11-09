package atlal.basha.hichat;

public class MSG {

    private String sender;
    private String message;
    private boolean isMe;

    public MSG() {

    }

    public MSG(String sender, String message, boolean isMe) {

        this.sender = sender;
        this.message = message;
        this.isMe = isMe;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }
}

