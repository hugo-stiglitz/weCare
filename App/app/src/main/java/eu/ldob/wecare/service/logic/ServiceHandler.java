package eu.ldob.wecare.service.logic;

import eu.ldob.wecare.entity.rest.AuthenticationToken;

public class ServiceHandler {

    private ServiceHandler() { }

    private static Service service;
    public static Service getService() {

        if(service == null) {
            service = new Service();
        }

        return service;
    }

    private static AuthenticationToken token;
    public static void setToken(AuthenticationToken token) { ServiceHandler.token = token; }
    public static AuthenticationToken getToken() { return token; }
}
