package com.msc.idol.mypa;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class RegisterActivity extends AppCompatActivity {


    private static final int REQUEST_READ_CONTACTS = 0;


    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };


    // UI references.
    private AutoCompleteTextView name;
    private EditText city, mobile;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the login form.
        name = (AutoCompleteTextView) findViewById(R.id.name);

        city = (EditText) findViewById(R.id.city);
        mobile = (EditText) findViewById(R.id.mobile);

        mobile.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.letsgo);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    private void attemptLogin() {


        // Reset errors.
        name.setError(null);
        city.setError(null);

        // Store values at the time of the login attempt.
        String name = this.name.getText().toString();
        String city = this.city.getText().toString();
        String mobile = this.mobile.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(city)) {
            this.city.setError(getString(R.string.error_field_required));
            focusView = this.city;
            cancel = true;
        }


        if (TextUtils.isEmpty(mobile)) {
            this.mobile.setError(getString(R.string.error_field_required));
            focusView = this.mobile;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(name)) {
            this.name.setError(getString(R.string.error_field_required));
            focusView = this.name;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            new UserLoginTask(name, city, mobile).execute();

        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String name, city, mobile;

        public UserLoginTask(String name, String city, String mobile) {
            this.name = name;
            this.city = city;
            this.mobile = mobile;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }


            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            showProgress(false);
            SharedPreferences preferences = getSharedPreferences(MyPAApp.PREF_USER, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(MyPAApp.PREF_KEY_NAME, name);
            editor.putString(MyPAApp.PREF_KEY_CITY, city);
            editor.putString(MyPAApp.PREF_KEY_MOBILE, mobile);
            editor.putBoolean(MyPAApp.PREF_KEY_IS_FIRST_LAUNCH, false);
            editor.commit();

            startActivity(new Intent(getBaseContext(), MyPAActivity.class));
            finish();
        }

        @Override
        protected void onCancelled() {
            showProgress(false);
        }
    }
}

