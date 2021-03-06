package eu.ldob.wecare.app.gui.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eu.ldob.wecare.app.R;
import eu.ldob.wecare.app.gui.AWeCareFragment;
import eu.ldob.wecare.service.logic.IDataChangeListener;
import eu.ldob.wecare.service.logic.Service;

public class CurrentFragment extends AWeCareFragment implements IDataChangeListener {

    private Service service;

    private OperationsAdapter adapter;

    @Override
    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_operation_list, container, false);
        
        adapter = new OperationsAdapter(getActivity(), service.getOperations());
        service.addDataChangeListener(this);

        RecyclerView rvOperations = (RecyclerView) view.findViewById(R.id.rv_operations);
        rvOperations.setAdapter(adapter);
        rvOperations.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void dataChanged() {
        adapter.notifyDataSetChanged();
    }
}