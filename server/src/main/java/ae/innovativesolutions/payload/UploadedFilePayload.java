package ae.innovativesolutions.payload;

import javax.persistence.*;


public class UploadedFilePayload {
    private String name;
    private String location;
    private Long size;
    private String type;


    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Long getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public void setType(String type) {
        this.type = type;
    }
}
