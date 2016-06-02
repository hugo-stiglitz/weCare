package eu.ldob.wecare.app.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import eu.ldob.wecare.app.R;
import eu.ldob.wecare.app.service.Service;
import eu.ldob.wecare.app.service.ServiceHandler;

public class LoginActivity extends AppCompatActivity {

    private EditText inputId, inputPassword;
    private TextInputLayout inputLayoutId, inputLayoutPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputLayoutId = (TextInputLayout) findViewById(R.id.input_layout_id);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);

        inputId = (EditText) findViewById(R.id.input_id);
        inputPassword = (EditText) findViewById(R.id.input_password);
        btnLogin = (Button) findViewById(R.id.btn_login);

        inputId.addTextChangedListener(new MyTextWatcher(inputId));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

        //btnLogin.setOnClickListener((view) -> login());
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        inputId.setText("123");
        inputPassword.setText("abc");
    }

    private void login() {
        if (validateId() && validatePassword()) {

            ServiceHandler.setService(new Service());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), getString(R.string.err_msg_unknown), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateId() {
        String id = inputId.getText().toString().trim();

        if (id.isEmpty()) {
            inputLayoutId.setError(getString(R.string.err_msg_id));
            requestFocus(inputId);
            return false;
        } else {
            inputLayoutId.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_id:
                    validateId();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }
}