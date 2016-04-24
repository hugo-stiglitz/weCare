package eu.ldob.wecare.entity.user;

public class Dispatcher extends AUser {

    public Dispatcher(long id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    @Override
    public String getType() {
        return "dispatcher";
    }
}
