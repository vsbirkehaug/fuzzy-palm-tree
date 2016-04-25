package artrec.com.artrec.register;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import artrec.com.artrec.R;
import artrec.com.artrec.main.MainActivity;
import artrec.com.artrec.models.User;
import artrec.com.artrec.server.APICall;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private RegisterUserAsyncCall mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private EditText mPasswordViewConfirm;
    private View mProgressView;
    private View mLoginFormView;
    private boolean usernameAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupActionBar();
        // Set up the login form.
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.username);

        mUsernameView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    checkUsernameExists(); //async
                }
            }
        });
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordViewConfirm = (EditText) findViewById(R.id.passwordConfirm);

        mPasswordViewConfirm.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {

                if(passwordsMatch()) {
                    if (id == R.id.login || id == EditorInfo.IME_NULL) {
                        attemptLogin();
                        return true;
                    }
                } else {
                    mPasswordViewConfirm.setError(getString(R.string.error_password_mismatch));
                    mPasswordViewConfirm.requestFocus();
                }
                return false;
            }
        });


        final Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usernameAvailable) {
                    attemptLogin();
                } else {
                    mUsernameView.setError(getString(R.string.error_username_exists));
                }
            }
        });

        mPasswordViewConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(isPasswordValid(mPasswordViewConfirm.getText().toString())) {
                    mSignInButton.setEnabled(true);
                } else {
                    mSignInButton.setEnabled(false);
                    mPasswordViewConfirm.setError(getString(R.string.error_password_mismatch));
                }
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mSignInButton.setEnabled(false);

    }

    private void checkUsernameExists() {
        CheckUsernameExistsAsyncTask task = new CheckUsernameExistsAsyncTask(this);
        task.setCredentials(mUsernameView.getText().toString());
        task.execute(MainActivity.APIURL + "userexists");
    }

    public boolean passwordsMatch() {
        return TextUtils.equals(mPasswordView.getText(), mPasswordViewConfirm.getText());
    }


    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
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
            mAuthTask = new RegisterUserAsyncCall(this);
            mAuthTask.setCredentials(username, password);
            mAuthTask.execute(MainActivity.APIURL+"user");
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4 && passwordsMatch() && passwordsSameLength();
    }

    private boolean passwordsSameLength() {
        return mPasswordViewConfirm.getText().toString().length() == mPasswordView.getText().toString().length();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
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

    public void setUsernameAvailable(boolean usernameAvailable) {
        this.usernameAvailable = usernameAvailable;
    }

    /**
     * Aan asynchronous login/registration task used to authenticate
     * the user.
     */

    private class RegisterUserAsyncCall extends APICall {

        private String authUsername;
        private String authPassword;


        public RegisterUserAsyncCall(Activity parent) {
            super(parent);
        }

        public void setCredentials(String username, String password) {
            this.authUsername = username;
            this.authPassword = password;
        }

        @Override
        protected String doInBackground(String... urls) {

            String result = String.valueOf(POSTUSER(this.authUsername, this.authPassword));

            Log.i("vilde", "Result: " + result);

            return result;
        }


        // onPostExecute displays the results of the AsyncTask.
        @SuppressWarnings("static-access")
        @Override
        protected void onPostExecute(String result) {
            mAuthTask = null;
            showProgress(false);
            int id = 0;
            User user = null;
            try {
                resultJsonArray = new JSONArray(result);

                if(result != null) {
                    for (int i = 0; i < resultJsonArray.length(); i++) {
                        try {
                            if(resultJsonArray.getJSONObject(i).has("id")) {
                                id = resultJsonArray.getJSONObject(i).getInt("id");
                            }
                        }catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                user = new User(id, authUsername);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(id > 0) {
                setUserAuthenticated(true, user);
            } else {
                setUserAuthenticated(false, null);
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    private void setUserAuthenticated(boolean b, User user) {
        if(b) {
            Intent intent = new Intent(getApplicationContext(), SubjectPicker.class);
            intent.putExtra("userid", user.getId());
            intent.putExtra("username", user.getUsername());
            startActivity(intent);
        } else {
            mPasswordView.setError(getString(R.string.error_incorrect_password));
            mPasswordView.requestFocus();
        }
    }



    private class CheckUsernameExistsAsyncTask extends APICall {
        private String authUsername;

        public CheckUsernameExistsAsyncTask(Activity parent) {
            super(parent);
        }

        public void setCredentials(String username) {
            this.authUsername = username;
        }

        @Override
        protected String doInBackground(String... urls) {

            String result = String.valueOf(GETEXISTS(urls[0], this.authUsername));

            Log.i("vilde", "Result: " + result);

            return result;
        }


        // onPostExecute displays the results of the AsyncTask.
        @SuppressWarnings("static-access")
        @Override
        protected void onPostExecute(String result) {

            boolean exists = true;
            JSONObject resultobject;

            try {
                resultobject = new JSONObject(result);

                if (result != null) {
                    try {
                        if (resultobject.has("exists")) {
                            exists = resultobject.getBoolean("exists");
                        } else {
                            exists = true;
                        }
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }

                }

                setUsernameAvailable(!exists);
                if (exists) {
                    mUsernameView.setError(getString(R.string.error_username_exists));
                    mUsernameView.setTextColor(getColor(android.R.color.holo_red_dark));
                } else {
                    mUsernameView.setTextColor(getColor(R.color.colorPrimaryDark));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


}

