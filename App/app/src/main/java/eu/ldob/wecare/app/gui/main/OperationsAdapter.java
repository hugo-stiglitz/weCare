package eu.ldob.wecare.app.gui.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import eu.ldob.wecare.app.R;
import eu.ldob.wecare.app.gui.DocumentationActivity;
import eu.ldob.wecare.app.gui.OperationActivity;
import eu.ldob.wecare.app.util.WeCareVariables;
import eu.ldob.wecare.entity.operation.Operation;

public class OperationsAdapter extends RecyclerView.Adapter<OperationsAdapter.ViewHolder> {

    private Activity activity;
    private List<Operation> operations;

    public OperationsAdapter(Activity activity, List<Operation> operations) {
        this.activity = activity;
        this.operations = operations;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvId;
        public TextView tvMessage;
        public ImageButton btEdit;

        public ViewHolder(View itemView) {
            super(itemView);

            tvId = (TextView) itemView.findViewById(R.id.operation_header);
            tvMessage = (TextView) itemView.findViewById(R.id.operation_message);
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
    public void onBindViewHolder(final OperationsAdapter.ViewHolder viewHolder, int position) {

        final Operation operation = operations.get(position);

        TextView tvId = viewHolder.tvId;
        tvId.setText(operation.getId());

        TextView tvMessage = viewHolder.tvMessage;
        tvMessage.setText(operation.getMessage());

        ImageButton btEdit = viewHolder.btEdit;
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class<?> clazz = operation.getStatus() == Operation.EStatus.FINISHED || operation.getStatus() == Operation.EStatus.DOCUMENTED ? DocumentationActivity.class : OperationActivity.class;

                Intent intent = new Intent(activity, clazz);
                intent.putExtra(WeCareVariables.OPERATION_ID, operation.getId());
                activity.startActivity(intent);
            }
        });
    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return operations.size();
    }
}