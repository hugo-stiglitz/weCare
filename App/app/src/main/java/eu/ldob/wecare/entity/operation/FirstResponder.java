package eu.ldob.wecare.entity.operation;

import eu.ldob.wecare.entity.user.AUser;

public class FirstResponder extends AUser {

    @Override
    public String getType() {
        return "firstresponder";
    }
}
