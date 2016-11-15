package eu.ldob.wecare.entity.operation;

public class Address {

    private String city;
    private int zipCode;
    private String street;

    public Address() { }

    public Address(String city, int zipCode, String street) {

        this.city = city;
        this.zipCode = zipCode;
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public int getZipCode() {
        return zipCode;
    }

    public String getStreet() {
        return street;
    }
}
