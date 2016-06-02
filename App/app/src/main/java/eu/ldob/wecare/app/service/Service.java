package eu.ldob.wecare.app.service;

import java.util.ArrayList;
import java.util.List;

import eu.ldob.wecare.entity.operation.Operation;

public class Service {

    public Service() {

    }

    public List<Operation> getOperations() {

        List<Operation> operations = new ArrayList<>();
        for(int i = 1; i <= 15; i++) {
            operations.add(new Operation(i, "R1603" + String.format("%04d", i * 3 + (11 % i))));
        }

        return operations;
    }
}
