package eu.ldob.wecare.entity.rest;

public class AuthenticationData {
    public AuthenticationData() {}
    public AuthenticationData(String id, String password) {
        setId(id);
        setPassword(password);
    }

    private String id;
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    private String password;
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}