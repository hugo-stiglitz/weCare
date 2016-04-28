package eu.ldob.wecare.app.service;

public class ServiceHandler {

    private ServiceHandler() { }

    private static Service service;
    public static void setService(Service service) { ServiceHandler.service = service; }
    public static Service getService() { return service; }
}
