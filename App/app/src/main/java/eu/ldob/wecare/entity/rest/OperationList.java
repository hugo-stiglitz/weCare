package eu.ldob.wecare.entity.rest;

import java.util.ArrayList;
import java.util.Collection;

import eu.ldob.wecare.entity.operation.Operation;

public class OperationList extends ArrayList<Operation> {

    public OperationList() {}
    public OperationList(Collection<Operation> original) {
        super(original);
    }

    public OperationList(Iterable<Operation> original) {
        for (Operation item : original) {
            add(item);
        }
    }
}