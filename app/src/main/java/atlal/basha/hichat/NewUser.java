package atlal.basha.hichat;

public class NewUser {
    private int imageResource;
    private String name;
    private String email;
    private String idFirebase;

    public NewUser() {
        // have to be empty
    }

    public NewUser(String name, String email, String idFirebase) {

        this.name = name;
        this.email = email;
        this.idFirebase = idFirebase;
    }

    public NewUser(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getIdFirebase() {
        return idFirebase;
    }

    public void setName(String name) {
        this.name = name;
    }
}
