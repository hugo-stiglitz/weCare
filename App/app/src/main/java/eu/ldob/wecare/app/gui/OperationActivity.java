package eu.ldob.wecare.app.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import eu.ldob.wecare.app.R;
import eu.ldob.wecare.app.service.Service;
import eu.ldob.wecare.app.service.ServiceHandler;
import eu.ldob.wecare.app.util.DateUtil;
import eu.ldob.wecare.app.util.WeCareVariables;
import eu.ldob.wecare.entity.operation.Operation;

public class OperationActivity extends AppCompatActivity {

    private Service service;

    private String operationId;
    private Operation operation;

    private TextView tvStatus;
    private EditText inputId, inputDate, inputMessage, inputFirstname, inputLastname, inputAge, inputCity, inputZip, inputStreet, inputMyDistance, inputAmbulanceDistance;
    private LinearLayout llInputName, llInputAddress, llInputDistance, llButtonAlarmed, llButtonAccepted, llButtonArrived;
    private Button btnDecline, btnAccept, btnCancelAccepted, btnArrived, btnCancelArrived, btnFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);

        service = ServiceHandler.getService();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                operationId = null;
            } else {
                operationId = extras.getString(WeCareVariables.OPERATION_ID);
            }
        } else {
            operationId = (String) savedInstanceState.getSerializable(WeCareVariables.OPERATION_ID);
        }

        operation = service.getOperation(operationId);

        initPage();
        refreshPage();
    }

    private void initPage() {

        llInputName = (LinearLayout) findViewById(R.id.ll_input_name);
        llInputAddress = (LinearLayout) findViewById(R.id.ll_input_address);
        llInputDistance = (LinearLayout) findViewById(R.id.ll_input_distance);

        llButtonAlarmed = (LinearLayout) findViewById(R.id.ll_button_alarmed);
        llButtonAccepted = (LinearLayout) findViewById(R.id.ll_button_accepted);
        llButtonArrived = (LinearLayout) findViewById(R.id.ll_button_arrived);

        tvStatus = (TextView) findViewById(R.id.tv_status);

        inputId = (EditText) findViewById(R.id.input_operation_id);
        inputDate = (EditText) findViewById(R.id.input_operation_date);
        inputMessage = (EditText) findViewById(R.id.input_operation_message);
        inputFirstname = (EditText) findViewById(R.id.input_patient_firstname);
        inputLastname = (EditText) findViewById(R.id.input_patient_lastname);
        inputAge = (EditText) findViewById(R.id.input_patient_age);
        inputCity = (EditText) findViewById(R.id.input_operation_city);
        inputZip = (EditText) findViewById(R.id.input_operation_zip);
        inputStreet = (EditText) findViewById(R.id.input_operation_street);
        inputMyDistance = (EditText) findViewById(R.id.input_operation_my_distance);
        inputAmbulanceDistance = (EditText) findViewById(R.id.input_operation_ambulance_distance);

        btnDecline = (Button) findViewById(R.id.btn_decline);
        btnAccept = (Button) findViewById(R.id.btn_accept);
        btnCancelAccepted = (Button) findViewById(R.id.btn_cancel_accepted);
        btnArrived = (Button) findViewById(R.id.btn_arrived);
        btnCancelArrived = (Button) findViewById(R.id.btn_cancel_arrived);
        btnFinished = (Button) findViewById(R.id.btn_finished);


        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.accept(operationId);
                refreshPage();
            }
        });

        btnArrived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.arrive(operationId);
                refreshPage();
            }
        });

        btnFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.finish(operationId);

                Intent intent = new Intent(OperationActivity.this, DocumentationActivity.class);
                intent.putExtra(WeCareVariables.OPERATION_ID, operation.getId());
                startActivity(intent);
                finish();
            }
        });

        View.OnClickListener declineListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar
                    .make(v, "Einsatz ablehnen?", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Ja", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            service.cancel(operationId);
                            finish();
                        }
                    })
                    .show();
            }
        };

        View.OnClickListener cancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar
                .make(v, "Einsatz abbrechen?", Snackbar.LENGTH_INDEFINITE)
                .setAction("Ja", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        service.cancel(operationId);
                        finish();
                    }
                })
                .show();
            }
        };

        btnDecline.setOnClickListener(declineListener);
        btnCancelAccepted.setOnClickListener(cancelListener);
        btnCancelArrived.setOnClickListener(cancelListener);
    }

    private void refreshPage() {

        operation = service.getOperation(operationId);

        llInputName.setVisibility(View.GONE);
        llInputAddress.setVisibility(View.GONE);
        llInputDistance.setVisibility(View.GONE);

        llButtonAlarmed.setVisibility(View.GONE);
        llButtonAccepted.setVisibility(View.GONE);
        llButtonArrived.setVisibility(View.GONE);

        tvStatus.setText(operation.getStatus().getTitle());

        inputId.setText(operation.getId());
        inputDate.setText(DateUtil.shortDate(operation.getTimestamps().get(Operation.EStatus.RECEIVED)));
        inputMessage.setText(operation.getMessage());
        inputLastname.setText(operation.getPatient().getLastname());
        inputFirstname.setText(operation.getPatient().getFirstname());
        inputAge.setText(String.valueOf(operation.getPatient().getAge()));
        inputCity.setText(operation.getLocation().getAddress().getCity());
        inputZip.setText(String.valueOf(operation.getLocation().getAddress().getZipCode()));
        inputStreet.setText(operation.getLocation().getAddress().getStreet());
        inputMyDistance.setText(operation.getMyDistance());
        inputAmbulanceDistance.setText(operation.getAmbulanceDistance());

        if(operation.getStatus() == Operation.EStatus.ALARMED) {
            llButtonAlarmed.setVisibility(View.VISIBLE);

            llInputDistance.setVisibility(View.VISIBLE);
        }
        else if(operation.getStatus() == Operation.EStatus.ACCEPTED) {
            llButtonAccepted.setVisibility(View.VISIBLE);

            llInputName.setVisibility(View.VISIBLE);
            llInputAddress.setVisibility(View.VISIBLE);
            llInputDistance.setVisibility(View.VISIBLE);
        }
        else if(operation.getStatus() == Operation.EStatus.ARRIVED) {
            llButtonArrived.setVisibility(View.VISIBLE);

            llInputName.setVisibility(View.VISIBLE);
            llInputAddress.setVisibility(View.VISIBLE);
        }
    }
}
