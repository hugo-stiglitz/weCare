package eu.ldob.wecare.entity.user;

public class Paramedic extends AUser {

    public Paramedic(long id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    @Override
    public String getType() {
        return "paramedic";
    }
}
