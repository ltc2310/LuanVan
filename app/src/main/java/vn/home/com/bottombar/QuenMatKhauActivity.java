package vn.home.com.bottombar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class QuenMatKhauActivity extends AppCompatActivity {

    private TextView txtResetPassword;
    private Button btnResetPassword,btnDangNhapLai;
    private ProgressBar pgBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);

        txtResetPassword = (TextView) findViewById(R.id.txtResetPassword);
        btnResetPassword = (Button) findViewById(R.id.btnResetPassword);
        btnDangNhapLai = (Button) findViewById(R.id.btnDangNhapLai);
        pgBar = (ProgressBar) findViewById(R.id.progressBarResetPassword);

        auth = FirebaseAuth.getInstance();

        btnDangNhapLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuenMatKhauActivity.this, DangNhapActivity.class);
                startActivity(intent);
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pgBar.setVisibility(View.VISIBLE);
                sendMailResetPassword();
            }
        });




    }

    private void sendMailResetPassword() {
        String emailResetPassword = txtResetPassword.getText().toString().trim();
        auth.sendPasswordResetEmail(emailResetPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(QuenMatKhauActivity.this, "Vui lòng kiểm tra email để lấy lại mật khẩu", Toast.LENGTH_SHORT).show();
                    Log.d("success","Email sent");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        pgBar.setVisibility(View.GONE);
    }
}
