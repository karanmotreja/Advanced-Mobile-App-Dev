package com.example.karanmotreja.lab10;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInActivity extends AppCompatActivity {

    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth firebaseAuth;
    private SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, options);

        signInButton = findViewById(R.id.login_with_google);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.e("LOGIN", "Google sign in failed", e);
                Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account){
        String idToken = account.getIdToken();
        final String name = account.getDisplayName();

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("SIGNIN", "signInWithCredential:onComplete:" + task.isSuccessful());
                if (task.isSuccessful()) {
                    Toast.makeText(SignInActivity.this, "Login successful " + name, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignInActivity.this, TeamsMainActivity.class);
                    startActivity(intent);
                }else {
                    Log.e("SIGNIN", "signInWithCredential" + task.getException());
                    Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}