package eu.ldob.wecare.app.gui.main;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import eu.ldob.wecare.app.R;
import eu.ldob.wecare.entity.operation.Operation;

public class OperationsAdapter extends RecyclerView.Adapter<OperationsAdapter.ViewHolder> {

    private List<Operation> operations;

    public OperationsAdapter(List<Operation> operations) {
        this.operations = operations;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvId;
        public TextView tvInfo;
        public ImageButton btEdit;

        public ViewHolder(View itemView) {
            super(itemView);

            tvId = (TextView) itemView.findViewById(R.id.operation_id);
            tvInfo = (TextView) itemView.findViewById(R.id.operation_info);
            btEdit = (ImageButton) itemView.findViewById(R.id.edit_button);
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

        TextView tvId = viewHolder.tvId;
        tvId.setText(operation.getId());

        TextView tvInfo = viewHolder.tvInfo;
        tvInfo.setText(operation.getInfo());

        ImageButton btEdit = viewHolder.btEdit;
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar
                    //.make(findViewById(R.id.rootLayout),
                    .make(v, "Einsatz kann nicht bearbeitet werden", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .show();
            }
        });
    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return operations.size();
    }
}