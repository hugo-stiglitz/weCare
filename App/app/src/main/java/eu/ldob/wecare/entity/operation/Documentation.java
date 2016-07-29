package eu.ldob.wecare.entity.operation;

import java.util.HashMap;
import java.util.Map;

public class Documentation {

    private Operation operation;
    private FirstResponder firstResponder;

    private String event;
    private String action;
    private Map<ESchema,Boolean> mapSchemaCritical = new HashMap<>();

    public Documentation(Operation operation, FirstResponder firstResponder) {
        this.operation = operation;
        this.firstResponder = firstResponder;
    }

    public enum ESchema {
        AIRWAY, BREATHING, CIRCULATION, DISABILITY, EXPOSURE
    }

    public Operation getOperation() {
        return operation;
    }

    public FirstResponder getFirstResponder() {
        return firstResponder;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean getSchemaCritical(ESchema schema) {

        if(!mapSchemaCritical.containsKey(schema)) {
            return false;
        }

        return mapSchemaCritical.get(schema);
    }

    public void setSchemaCritical(ESchema schema, boolean critical) {
        mapSchemaCritical.put(schema, critical);
    }
}
