package ae.innovativesolutions.payload;

public class Message {
    String name;
    public Message() {

    }
    public Message(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
