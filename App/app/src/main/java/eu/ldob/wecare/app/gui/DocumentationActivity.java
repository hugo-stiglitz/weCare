package eu.ldob.wecare.app.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import eu.ldob.wecare.app.R;
import eu.ldob.wecare.app.service.Service;
import eu.ldob.wecare.app.service.ServiceHandler;
import eu.ldob.wecare.app.util.DateUtil;
import eu.ldob.wecare.app.util.WeCareVariables;
import eu.ldob.wecare.entity.operation.Documentation;
import eu.ldob.wecare.entity.operation.Operation;

public class DocumentationActivity extends AppCompatActivity {

    private Service service;

    private String operationId;
    private Operation operation;

    private TextView tvStatus;
    private EditText inputId, inputDate, inputMessage, inputFirstname, inputLastname, inputAge, inputCity, inputZip, inputStreet, inputAlarmed, inputAccepted, inputArrivalMe, inputArrivalAmbulance, inputFinished, inputDocumented;
    private EditText inputEvent, inputAction;
    private Button btnAirwayCritical, btnAirwayStable, btnBreathingCritical, btnBreathingStable, btnCirculationCritical, btnCirculationStable, btnDisabilityCritical, btnDisabilityStable, btnExposureCritical, btnExposureStable;
    private Button btnSave;

    private Documentation documentation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentation);

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
        if(operation.getStatus() == Operation.EStatus.DOCUMENTED) {
            initPageNonEditable();
        }
        else {
            initPageEditable();
        }
    }

    private void initPage() {

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
        inputAlarmed = (EditText) findViewById(R.id.input_timestamp_alarmed);
        inputAccepted = (EditText) findViewById(R.id.input_timestamp_accepted);
        inputArrivalMe = (EditText) findViewById(R.id.input_timestamp_arrived_me);
        inputArrivalAmbulance = (EditText) findViewById(R.id.input_timestamp_arrived_ambulance);
        inputFinished = (EditText) findViewById(R.id.input_timestamp_finished);
        inputDocumented = (EditText) findViewById(R.id.input_timestamp_documented);

        inputEvent = (EditText) findViewById(R.id.input_documentation_event);
        inputAction = (EditText) findViewById(R.id.input_documentation_action);

        btnAirwayCritical = (Button) findViewById(R.id.btn_airway_critical);
        btnAirwayStable = (Button) findViewById(R.id.btn_airway_stable);
        btnBreathingCritical = (Button) findViewById(R.id.btn_breathing_critical);
        btnBreathingStable = (Button) findViewById(R.id.btn_breathing_stable);
        btnCirculationCritical = (Button) findViewById(R.id.btn_circulation_critical);
        btnCirculationStable = (Button) findViewById(R.id.btn_circulation_stable);
        btnDisabilityCritical = (Button) findViewById(R.id.btn_disability_critical);
        btnDisabilityStable = (Button) findViewById(R.id.btn_disability_stable);
        btnExposureCritical = (Button) findViewById(R.id.btn_exposure_critical);
        btnExposureStable = (Button) findViewById(R.id.btn_exposure_stable);

        btnSave = (Button) findViewById(R.id.btn_save);

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
        inputAlarmed.setText(DateUtil.longTime(operation.getTimestamps().get(Operation.EStatus.ALARMED)));
        inputAccepted.setText(DateUtil.longTime(operation.getTimestamps().get(Operation.EStatus.ACCEPTED)));
        inputArrivalMe.setText(DateUtil.longTime(operation.getTimestamps().get(Operation.EStatus.ARRIVED)));
        // TODO ambulance arrival
        inputArrivalAmbulance.setText(DateUtil.longTime(new Date(operation.getTimestamps().get(Operation.EStatus.ARRIVED).getTime() + 1000*60*4 + 1000*43)));
        inputFinished.setText(DateUtil.longTime(operation.getTimestamps().get(Operation.EStatus.FINISHED)));
        inputDocumented.setText(operation.getTimestamps().containsKey(Operation.EStatus.DOCUMENTED) ? DateUtil.longTime(operation.getTimestamps().get(Operation.EStatus.DOCUMENTED)) : "---");
    }

    private void initPageEditable() {

        documentation = new Documentation(operation, service.getUser());

        btnAirwayCritical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAirwayCritical.setBackgroundColor(getResources().getColor(R.color.primary_dark));
                btnAirwayStable.setBackgroundColor(getResources().getColor(R.color.transparent));
                btnAirwayCritical.setTextColor(getResources().getColor(R.color.primary_text));
                btnAirwayStable.setTextColor(getResources().getColor(R.color.general_text));
                documentation.setSchemaCritical(Documentation.ESchema.AIRWAY, true);
            }
        });
        btnAirwayStable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAirwayStable.setBackgroundColor(getResources().getColor(R.color.primary_dark));
                btnAirwayCritical.setBackgroundColor(getResources().getColor(R.color.transparent));
                btnAirwayStable.setTextColor(getResources().getColor(R.color.primary_text));
                btnAirwayCritical.setTextColor(getResources().getColor(R.color.general_text));
                documentation.setSchemaCritical(Documentation.ESchema.AIRWAY, false);
            }
        });

        btnBreathingCritical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBreathingCritical.setBackgroundColor(getResources().getColor(R.color.primary_dark));
                btnBreathingStable.setBackgroundColor(getResources().getColor(R.color.transparent));
                btnBreathingCritical.setTextColor(getResources().getColor(R.color.primary_text));
                btnBreathingStable.setTextColor(getResources().getColor(R.color.general_text));
                documentation.setSchemaCritical(Documentation.ESchema.BREATHING, true);
            }
        });
        btnBreathingStable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBreathingStable.setBackgroundColor(getResources().getColor(R.color.primary_dark));
                btnBreathingCritical.setBackgroundColor(getResources().getColor(R.color.transparent));
                btnBreathingStable.setTextColor(getResources().getColor(R.color.primary_text));
                btnBreathingCritical.setTextColor(getResources().getColor(R.color.general_text));
                documentation.setSchemaCritical(Documentation.ESchema.BREATHING, false);
            }
        });

        btnCirculationCritical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCirculationCritical.setBackgroundColor(getResources().getColor(R.color.primary_dark));
                btnCirculationStable.setBackgroundColor(getResources().getColor(R.color.transparent));
                btnCirculationCritical.setTextColor(getResources().getColor(R.color.primary_text));
                btnCirculationStable.setTextColor(getResources().getColor(R.color.general_text));
                documentation.setSchemaCritical(Documentation.ESchema.CIRCULATION, true);
            }
        });
        btnCirculationStable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCirculationStable.setBackgroundColor(getResources().getColor(R.color.primary_dark));
                btnCirculationCritical.setBackgroundColor(getResources().getColor(R.color.transparent));
                btnCirculationStable.setTextColor(getResources().getColor(R.color.primary_text));
                btnCirculationCritical.setTextColor(getResources().getColor(R.color.general_text));
                documentation.setSchemaCritical(Documentation.ESchema.CIRCULATION, false);
            }
        });

        btnDisabilityCritical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDisabilityCritical.setBackgroundColor(getResources().getColor(R.color.primary_dark));
                btnDisabilityStable.setBackgroundColor(getResources().getColor(R.color.transparent));
                btnDisabilityCritical.setTextColor(getResources().getColor(R.color.primary_text));
                btnDisabilityStable.setTextColor(getResources().getColor(R.color.general_text));
                documentation.setSchemaCritical(Documentation.ESchema.DISABILITY, true);
            }
        });
        btnDisabilityStable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDisabilityStable.setBackgroundColor(getResources().getColor(R.color.primary_dark));
                btnDisabilityCritical.setBackgroundColor(getResources().getColor(R.color.transparent));
                btnDisabilityStable.setTextColor(getResources().getColor(R.color.primary_text));
                btnDisabilityCritical.setTextColor(getResources().getColor(R.color.general_text));
                documentation.setSchemaCritical(Documentation.ESchema.DISABILITY, false);
            }
        });

        btnExposureCritical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnExposureCritical.setBackgroundColor(getResources().getColor(R.color.primary_dark));
                btnExposureStable.setBackgroundColor(getResources().getColor(R.color.transparent));
                btnExposureCritical.setTextColor(getResources().getColor(R.color.primary_text));
                btnExposureStable.setTextColor(getResources().getColor(R.color.general_text));
                documentation.setSchemaCritical(Documentation.ESchema.EXPOSURE, true);
            }
        });
        btnExposureStable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnExposureStable.setBackgroundColor(getResources().getColor(R.color.primary_dark));
                btnExposureCritical.setBackgroundColor(getResources().getColor(R.color.transparent));
                btnExposureStable.setTextColor(getResources().getColor(R.color.primary_text));
                btnExposureCritical.setTextColor(getResources().getColor(R.color.general_text));
                documentation.setSchemaCritical(Documentation.ESchema.EXPOSURE, false);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                documentation.setEvent(inputEvent.getText().toString());
                documentation.setAction(inputAction.getText().toString());
                service.saveDocumentation(documentation);

                finish();
            }
        });
    }

    private void initPageNonEditable() {

        documentation = service.getDocumentation(operationId, service.getUser());

        inputEvent.setText(documentation.getEvent());
        inputAction.setText(documentation.getAction());

        inputEvent.setFocusable(false);
        inputAction.setFocusable(false);

        if(documentation.getSchemaCritical(Documentation.ESchema.AIRWAY)) {
            btnAirwayCritical.setBackgroundColor(getResources().getColor(R.color.primary_dark));
            btnAirwayStable.setBackgroundColor(getResources().getColor(R.color.transparent));
            btnAirwayCritical.setTextColor(getResources().getColor(R.color.primary_text));
            btnAirwayStable.setTextColor(getResources().getColor(R.color.general_text));
        }
        else {
            btnAirwayStable.setBackgroundColor(getResources().getColor(R.color.primary_dark));
            btnAirwayCritical.setBackgroundColor(getResources().getColor(R.color.transparent));
            btnAirwayStable.setTextColor(getResources().getColor(R.color.primary_text));
            btnAirwayCritical.setTextColor(getResources().getColor(R.color.general_text));
        }

        if(documentation.getSchemaCritical(Documentation.ESchema.BREATHING)) {
            btnBreathingCritical.setBackgroundColor(getResources().getColor(R.color.primary_dark));
            btnBreathingStable.setBackgroundColor(getResources().getColor(R.color.transparent));
            btnBreathingCritical.setTextColor(getResources().getColor(R.color.primary_text));
            btnBreathingStable.setTextColor(getResources().getColor(R.color.general_text));
        }
        else {
            btnBreathingStable.setBackgroundColor(getResources().getColor(R.color.primary_dark));
            btnBreathingCritical.setBackgroundColor(getResources().getColor(R.color.transparent));
            btnBreathingStable.setTextColor(getResources().getColor(R.color.primary_text));
            btnBreathingCritical.setTextColor(getResources().getColor(R.color.general_text));
        }

        if(documentation.getSchemaCritical(Documentation.ESchema.CIRCULATION)) {
            btnCirculationCritical.setBackgroundColor(getResources().getColor(R.color.primary_dark));
            btnCirculationStable.setBackgroundColor(getResources().getColor(R.color.transparent));
            btnCirculationCritical.setTextColor(getResources().getColor(R.color.primary_text));
            btnCirculationStable.setTextColor(getResources().getColor(R.color.general_text));
        }
        else {
            btnCirculationStable.setBackgroundColor(getResources().getColor(R.color.primary_dark));
            btnCirculationCritical.setBackgroundColor(getResources().getColor(R.color.transparent));
            btnCirculationStable.setTextColor(getResources().getColor(R.color.primary_text));
            btnCirculationCritical.setTextColor(getResources().getColor(R.color.general_text));
        }

        if(documentation.getSchemaCritical(Documentation.ESchema.DISABILITY)) {
            btnDisabilityCritical.setBackgroundColor(getResources().getColor(R.color.primary_dark));
            btnDisabilityStable.setBackgroundColor(getResources().getColor(R.color.transparent));
            btnDisabilityCritical.setTextColor(getResources().getColor(R.color.primary_text));
            btnDisabilityStable.setTextColor(getResources().getColor(R.color.general_text));
        }
        else {
            btnDisabilityStable.setBackgroundColor(getResources().getColor(R.color.primary_dark));
            btnDisabilityCritical.setBackgroundColor(getResources().getColor(R.color.transparent));
            btnDisabilityStable.setTextColor(getResources().getColor(R.color.primary_text));
            btnDisabilityCritical.setTextColor(getResources().getColor(R.color.general_text));
        }

        if(documentation.getSchemaCritical(Documentation.ESchema.EXPOSURE)) {
            btnExposureCritical.setBackgroundColor(getResources().getColor(R.color.primary_dark));
            btnExposureStable.setBackgroundColor(getResources().getColor(R.color.transparent));
            btnExposureCritical.setTextColor(getResources().getColor(R.color.primary_text));
            btnExposureStable.setTextColor(getResources().getColor(R.color.general_text));
        }
        else {
            btnExposureStable.setBackgroundColor(getResources().getColor(R.color.primary_dark));
            btnExposureCritical.setBackgroundColor(getResources().getColor(R.color.transparent));
            btnExposureStable.setTextColor(getResources().getColor(R.color.primary_text));
            btnExposureCritical.setTextColor(getResources().getColor(R.color.general_text));
        }

        btnSave.setVisibility(View.GONE);
    }
}
