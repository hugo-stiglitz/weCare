package eu.ldob.wecare.app.gui.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import eu.ldob.wecare.app.R;
import eu.ldob.wecare.app.gui.DocumentationActivity;
import eu.ldob.wecare.app.gui.OperationActivity;
import eu.ldob.wecare.app.util.DateUtil;
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

        public TextView tvStatus, tvDate, tvId, tvMessage, tvAdditional;
        public ImageButton btEdit;

        public ViewHolder(View itemView) {
            super(itemView);

            tvStatus = (TextView) itemView.findViewById(R.id.operation_status);
            tvDate = (TextView) itemView.findViewById(R.id.operation_date);
            tvId = (TextView) itemView.findViewById(R.id.operation_id);
            tvMessage = (TextView) itemView.findViewById(R.id.operation_message);
            tvAdditional = (TextView) itemView.findViewById(R.id.operation_additional);
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

    private static int tmp = 0;

    @Override
    public void onBindViewHolder(final OperationsAdapter.ViewHolder viewHolder, int position) {

        final Operation operation = operations.get(position);

        TextView tvStatus = viewHolder.tvStatus;
        tvStatus.setText(operation.getStatus().getName());

        TextView tvDate = viewHolder.tvDate;
        //tvDate.setText(DateUtil.shortDate(operation.getTimestamps().get(Operation.EStatus.RECEIVED)));
        tvDate.setText(DateUtil.shortDate(new Date(new Date().getTime() - (24*60*60*1000) * tmp++)));

        TextView tvId = viewHolder.tvId;
        tvId.setText(operation.getId());

        TextView tvMessage = viewHolder.tvMessage;
        tvMessage.setText(operation.getMessage());

        TextView tvAdditional = viewHolder.tvAdditional;

        String additional;
        if(operation.getStatus() == Operation.EStatus.RECEIVED || operation.getStatus() == Operation.EStatus.ALARMED || operation.getStatus() == Operation.EStatus.CANCELED || operation.getStatus() == Operation.EStatus.FINISHED) {
            additional = operation.getLocation().getAddress().getZipCode() + " - " + operation.getLocation().getAddress().getCity() + " " + operation.getLocation().getAddress().getStreet();
        }
        else if(operation.getStatus() == Operation.EStatus.ACCEPTED || operation.getStatus() == Operation.EStatus.ARRIVED || operation.getStatus() == Operation.EStatus.DOCUMENTED) {
            additional =
                    operation.getPatient().getFirstname() + " " + operation.getPatient().getLastname() + " " + operation.getPatient().getAge() + "J" +
                    "\n" +
                    operation.getLocation().getAddress().getZipCode() + " - " + operation.getLocation().getAddress().getCity() + " " + operation.getLocation().getAddress().getStreet();
        }
        else {
            additional = "";
        }
        tvAdditional.setText(additional);

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

        if(operation.getStatus() == Operation.EStatus.RECEIVED) {
            btEdit.setImageResource(R.drawable.accept);
        }
        else if(operation.getStatus() == Operation.EStatus.FINISHED || operation.getStatus() == Operation.EStatus.CANCELED) {
            btEdit.setImageResource(R.drawable.none);
        }
    }

    @Override
    public int getItemCount() {
        return operations.size();
    }
}