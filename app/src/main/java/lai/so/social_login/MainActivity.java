package lai.so.social_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.CallbackManager;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends AppCompatActivity {
    private TextView textView1;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    ProfileTracker profileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        textView1 = (TextView)findViewById(R.id.textView1);
        loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.setReadPermissions("user_friends");
        callbackManager = CallbackManager.Factory.create();
        FacebookCallback<LoginResult> loginCallback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                textView1.setText(loginResult.getAccessToken().getUserId());
            }

            @Override
            public void onCancel() {
                textView1.setText("Cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                textView1.setText("Error:" + exception);
            }
        };

        loginButton.registerCallback(callbackManager, loginCallback);
        LoginManager.getInstance().registerCallback(callbackManager, loginCallback);

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                if (currentProfile==null){
                    textView1.setText("(Logout)");
                }else {
                    textView1.setText("Hello " + currentProfile.getName());
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
