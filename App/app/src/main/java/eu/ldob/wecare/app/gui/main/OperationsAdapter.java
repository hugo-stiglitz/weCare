package eu.ldob.wecare.app.gui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import eu.ldob.app.wecare.R;
import eu.ldob.wecare.entity.operation.Operation;

public class OperationsAdapter extends RecyclerView.Adapter<OperationsAdapter.ViewHolder> {

    private List<Operation> operations;

    public OperationsAdapter(List<Operation> operations) {
        this.operations = operations;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public Button btOpen;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.operation_name);
            btOpen = (Button) itemView.findViewById(R.id.open_button);
        }
    }

    @Override
    public OperationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_operation, parent, false);

        return new ViewHolder(contactView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(OperationsAdapter.ViewHolder viewHolder, int position) {

        Operation operation = operations.get(position);

        // Set item views based on the data model
        TextView textView = viewHolder.tvName;
        textView.setText(operation.toString());

        Button button = viewHolder.btOpen;
        button.setText("Öffnen");

    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return operations.size();
    }
}