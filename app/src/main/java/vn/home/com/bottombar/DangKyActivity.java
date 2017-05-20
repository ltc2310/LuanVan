package vn.home.com.bottombar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;


public class DangKyActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText edtEmailDK, edtMatKhauDK, edtHoTenDK, edtSDTDK;
    private Button btnDangKy,btnDangNhapTaiDay;
    private ProgressBar pgBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        edtEmailDK = (EditText) findViewById(R.id.edtEmailDK);
        edtMatKhauDK = (EditText) findViewById(R.id.edtMatKhauDK);
        edtHoTenDK = (EditText) findViewById(R.id.edtHoTenDK);
        btnDangKy = (Button) findViewById(R.id.btnDangKy);
        edtSDTDK = (EditText) findViewById(R.id.edtSDTDK);
        pgBar = (ProgressBar) findViewById(R.id.progressBarRegister);
        btnDangNhapTaiDay = (Button) findViewById(R.id.btnDangNhapTaiDay);

        auth = FirebaseAuth.getInstance();

        btnDangNhapTaiDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangKyActivity.this,DangNhapActivity.class));
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = edtEmailDK.getText().toString().trim();
                String matkhau = edtMatKhauDK.getText().toString().trim();

                if (TextUtils.isEmpty(edtHoTenDK.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền vào họ và tên!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền vào địa chỉ email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(matkhau)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền vào mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (matkhau.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Mật khẩu quá ngắn, tối thiểu 6 kí tự", Toast.LENGTH_SHORT).show();
                    return;
                }

                pgBar.setVisibility(View.VISIBLE);

                auth.createUserWithEmailAndPassword(email, matkhau).addOnCompleteListener(DangKyActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            sendVerificationEmail();
                        }
                        else {
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                edtEmailDK.setError(" Email sai định dạng. Vui lòng kiểm tra lại! ");
                                edtEmailDK.requestFocus();
                            } catch(FirebaseAuthUserCollisionException e) {
                                edtEmailDK.setError(" Email đã được sử dụng bởi một người khác. Vui lòng kiểm tra lại! ");
                                edtEmailDK.requestFocus();
                            } catch(Exception e) {
                                Log.e("fail", e.getMessage());
                            }
                            Toast.makeText(DangKyActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                            pgBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

    }

    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(DangKyActivity.this, "Đăng kí thành công . Vui lòng kiểm tra email để kích hoạt tài khoản", Toast.LENGTH_SHORT).show();
                        pgBar.setVisibility(View.GONE);
                        startActivity(new Intent(DangKyActivity.this,DangNhapActivity.class));
                    }
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        pgBar.setVisibility(View.GONE);
    }


}
