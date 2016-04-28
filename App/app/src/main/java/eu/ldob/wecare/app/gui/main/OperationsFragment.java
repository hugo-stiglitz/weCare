package eu.ldob.wecare.app.gui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import eu.ldob.app.wecare.R;
import eu.ldob.wecare.app.gui.AWeCareFragment;
import eu.ldob.wecare.app.service.Service;
import eu.ldob.wecare.entity.operation.Operation;

public class OperationsFragment extends AWeCareFragment implements AdapterView.OnItemClickListener {

    private Service service;

    @Override
    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_operations, container, false);

        ListView lvOperations = (ListView) view.findViewById(R.id.lv_operations);

        ArrayAdapter<Operation> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);
        adapter.addAll(service.getOperations());

        lvOperations.setAdapter(adapter);
        lvOperations.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast
            .makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT)
            .show();
    }
}