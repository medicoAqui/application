package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.gabriela.medicoaqui.Activity.Entities.Cliente;
import com.example.gabriela.medicoaqui.Activity.Entities.Medico;
import com.example.gabriela.medicoaqui.Activity.JsonOperators.JSONReader;
import com.example.gabriela.medicoaqui.Activity.Service.HttpConnections;
import com.example.gabriela.medicoaqui.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class TelaLogin extends AppCompatActivity implements LoaderCallbacks<Cursor> {

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
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    // Henrique Autenticacao - 24/05 - INICIO
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static final String NOME_PREFERENCE = "INFORMACOES_LOGIN_AUTOMATICO";
    // Henrique Autenticacao - 24/05 - FIM

    private JSONObject jsonTT = new JSONObject();
    private JSONReader jsonReader = new JSONReader();
    private static HttpConnections http = new HttpConnections();
    public Cliente cliente;
    public String clienteBD;
    public static String emailCliente;
    public static Cliente clientePerfil;

    public static Medico medicoLogado;

    public static String perfil = "paciente"; //Opção default

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        perfil = "paciente"; //Opção default

        super.onCreate(savedInstanceState);
        // Henrique Autenticacao - 24/05 - INICIO
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("AUTH", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d("AUTH", "onAuthStateChanged:signed_out");
                }

            }
        };
        // Henrique Autenticacao - 24/05 - FIM
        boolean loginFromView = true;

        if (Splash.dadosUsuarioEmCache){
            if (Splash.perfil != null && "paciente".equals(Splash.perfil)){
                try {
                    carregaClienteEmail(Splash.login);
                    while(getClientePerfil() == null){
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                boolean exists = false;
                if(null != getClientePerfil() && null != getClientePerfil().getEmail() && !getClientePerfil().getEmail().isEmpty()) {
                    exists = true;
                }

                final AlertDialog.Builder builder = new AlertDialog.Builder(TelaLogin.this);
                builder.setTitle("Falha de autenticação");


                if(exists){
                    mAuth.signInWithEmailAndPassword(Splash.login, Splash.senha).addOnCompleteListener(TelaLogin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            Log.d("Cliente", "email existe na base dos pacientes. Realizando tentativa de login pelo firebase");
                            if (!task.isSuccessful()) {
                                Log.w("AUTH", "Falha ao efetuar o Login VIA CACHE: ", task.getException());
                                builder.setMessage("Falha ao efetuar o Login VIA CACHE, favor verificar email e senha");
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } else {
                                Log.d("AUTH", "Login Efetuado VIA CACHE com sucesso - paciente");

                                mAuthTask = new UserLoginTask(Splash.login, Splash.senha);
                                mAuthTask.execute((Void) null);

                                carregaClienteEmail(Splash.login);

                                //Intent sendIntent = new Intent(this, MedicoAqui.class);
                                Intent sendIntent = new Intent(TelaLogin.this, MenuPrincipal.class);
                                setContentView(R.layout.activity_menu_principal);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, "test2");
                                startActivity(sendIntent);

                            }
                        }
                    });
                    loginFromView = false;
                }
                else{
                    loginFromView = true;
                }
            }
            if (Splash.perfil != null && "medico".equals(Splash.perfil)){

                try {
                    recuperaMedicoPorEmail(Splash.login);
                    while(getMedicoLogado() == null){
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean exists = false;
                if(null != getMedicoLogado() && null != getMedicoLogado().getEmail() && !getMedicoLogado().getEmail().isEmpty()) {
                    exists = true;
                }

                final AlertDialog.Builder builder = new AlertDialog.Builder(TelaLogin.this);
                builder.setTitle("Falha de autenticação");

                if(exists){
                    //faz login
                    Log.d("Medico", "email existe na base dos medicos. Realizando tentativa de login pelo firebase");
                    mAuth.signInWithEmailAndPassword(Splash.login, Splash.senha).addOnCompleteListener(TelaLogin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w("AUTH", "Falha ao efetuar o Login VIA CACHE: ", task.getException());
                                builder.setMessage("Falha ao efetuar o Login VIA CACHE. Favor verificar e-mail e senha");
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } else {
                                Log.d("AUTH", "Login Efetuado com sucesso - medico");
//                                        carregaMedicoPorEmail(mEmailView.getText().toString());

                                Intent sendIntent = new Intent(TelaLogin.this, MenuPrincipalMedico.class);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, "test");
                                startActivity(sendIntent);
                            }
                        }
                    });
                    loginFromView = false;
                }
                else{
                    loginFromView = true;
                }
            }
            if (Splash.perfil == null){
                loginFromView = true;
            }
        }
        if(loginFromView){

            setContentView(R.layout.activity_tela_login);
            // Set up the login form.
            mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
            populateAutoComplete();

            mPasswordView = (EditText) findViewById(R.id.password);
            mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                        //attemptLogin();
                        return true;
                    }
                    return false;
                }
            });

            final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.group_perfil);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    if(checkedId == R.id.perfil_paciente) {
                        perfil = "paciente";
                    } else if(checkedId == R.id.perfil_medico) {
                        perfil = "medico";
                    }

                }
            });

            Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
            mEmailSignInButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    // Henrique Autenticacao - 24/05 - INICIO
                    // 1. Instantiate an AlertDialog.Builder with its constructor
                    final AlertDialog.Builder builder = new AlertDialog.Builder(TelaLogin.this);
                    builder.setTitle("Falha de autenticação");
                    setEmailCliente(mEmailView.getText().toString());

                    if (perfil.equals("paciente")) {

                        if (!("".equals(mEmailView.getText().toString()) || "".equals(mPasswordView.getText().toString()))) {

                            try {
                                carregaClienteEmail(mEmailView.getText().toString());
                                while(getClientePerfil() == null){
                                    Thread.sleep(1000);
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            boolean exists = false;
                            if(null != getClientePerfil() && null != getClientePerfil().getEmail() && !getClientePerfil().getEmail().isEmpty()) {
                                exists = true;
                            }
                            if(exists){
                                mAuth.signInWithEmailAndPassword(mEmailView.getText().toString(), mPasswordView.getText().toString()).addOnCompleteListener(TelaLogin.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        Log.d("Cliente", "email existe na base dos pacientes. Realizando tentativa de login pelo firebase");
                                        if (!task.isSuccessful()) {
                                            Log.w("AUTH", "Falha ao efetuar o Login: ", task.getException());
                                            builder.setMessage("Falha ao efetuar o Login, favor verificar email e senha");
                                            AlertDialog dialog = builder.create();
                                            dialog.show();
                                        } else {
                                            Log.d("AUTH", "Login Efetuado com sucesso - paciente");
                                            attemptLogin();
                                            carregaClienteEmail(mEmailView.getText().toString());

                                            SharedPreferences.Editor editor = getSharedPreferences(NOME_PREFERENCE, MODE_PRIVATE).edit();
                                            editor.putString("email", mEmailView.getText().toString());
                                            editor.putString("password", mPasswordView.getText().toString());
                                            editor.putString("perfil", "paciente");
                                            editor.commit();

                                        }
                                    }
                                });
                            }else{
                                Log.w("AUTH", "Falha ao efetuar o Login.");
                                builder.setMessage("Falha ao efetuar o Login, favor verificar email e senha");
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }


                        } else {
                            builder.setMessage("Favor insira os dados de email e senha");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                    } else if (perfil.equals("medico")) {
                        if (!("".equals(mEmailView.getText().toString()) || "".equals(mPasswordView.getText().toString()))) {
                            //vai no heroku e verifica se o e-mail existe

                            try {
                                recuperaMedicoPorEmail(mEmailView.getText().toString());
                                while(getMedicoLogado() == null){
                                    Thread.sleep(1000);
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            boolean exists = false;
                            if(null != getMedicoLogado() && null != getMedicoLogado().getEmail() && !getMedicoLogado().getEmail().isEmpty() && getMedicoLogado().getCrm() != null && !"".equals(getMedicoLogado().getCrm()) ) {
                                exists = true;
                            }
                            if(exists){
                                //faz login
                                Log.d("Medico", "email existe na base dos medicos. Realizando tentativa de login pelo firebase");
                                mAuth.signInWithEmailAndPassword(mEmailView.getText().toString(), mPasswordView.getText().toString()).addOnCompleteListener(TelaLogin.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            Log.w("AUTH", "Falha ao efetuar o Login: ", task.getException());
                                            builder.setMessage("Falha ao efetuar o Login. Favor verificar e-mail e senha");
                                            AlertDialog dialog = builder.create();
                                            dialog.show();
                                        } else {
                                            Log.d("AUTH", "Login Efetuado com sucesso - medico");
    //                                        carregaMedicoPorEmail(mEmailView.getText().toString());

                                            SharedPreferences.Editor editor = getSharedPreferences(NOME_PREFERENCE, MODE_PRIVATE).edit();
                                            editor.putString("email", mEmailView.getText().toString());
                                            editor.putString("password", mPasswordView.getText().toString());
                                            editor.putString("perfil", "medico");
                                            editor.commit();

                                            Intent sendIntent = new Intent(TelaLogin.this, MenuPrincipalMedico.class);
                                            sendIntent.putExtra(Intent.EXTRA_TEXT, "test");
                                            startActivity(sendIntent);
                                        }
                                    }
                                });
                            }else{
                                Log.w("AUTH", "Falha ao efetuar o Login. Medico nao cadastrado na base do heroku");
                                builder.setMessage("Falha ao efetuar o Login. Favor verificar e-mail e senha");
                                setClientePerfil(null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }


                        } else {
                            builder.setMessage("Favor insira os dados de email e senha");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }

                    // Henrique Autenticacao - 24/05 - FIM
                }
            });

            mLoginFormView = findViewById(R.id.login_form);
            mProgressView = findViewById(R.id.login_progress);
        }
    }

    public void callReset(View view){
        Intent intent = new Intent( this, EsqueciSenhaActivity.class );
        startActivity(intent);
    }

    // Henrique Autenticacao - 24/05 - INICIO

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    // Henrique Autenticacao - 24/05 - FIM

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
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
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
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
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
            //Intent sendIntent = new Intent(this, MedicoAqui.class);
            Intent sendIntent = new Intent(this, MenuPrincipal.class);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "test");
            startActivity(sendIntent);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (mLoginFormView != null && mProgressView != null) {
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
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(TelaLogin.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
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

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    public static String getEmailCliente() {
        return emailCliente;
    }
    public void setEmailCliente(String email) {
        emailCliente = email;
    }

    private void carregaClienteEmail(String email) {
        final JSONObject jsonTT = new JSONObject();
        Log.d("Cliente", "Verificando se email consta na base dos pacientes >" + email);
        try {
            jsonTT.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String clienteBD = http.sendPost("http://medicoishere.herokuapp.com/cliente/clientePorEmail", jsonTT.toString());
                    Cliente cliente = jsonReader.getClienteByEmail(clienteBD);
                    setClientePerfil(cliente);
                } catch (HttpConnections.MinhaException e) {
                    setClientePerfil(new Cliente());
                    Log.d("Cliente", "Cliente nao encontrado.");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void recuperaMedicoPorEmail(String email) {
        Log.d("Thread", "Verificando se email consta na base dos medicos >" + email);
        final JSONObject jsonTT = new JSONObject();
        //Cliente cliente;
        try {
            jsonTT.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String medicoBD = http.sendPost("http://medicoishere.herokuapp.com/medico/medicoByEmail", jsonTT.toString());
                    Medico medico = getMedicoByID(medicoBD);
                    Log.d("Thread", "Medico recuperado =" + medico.toString());
                    setMedicoLogado(medico);
                } catch (HttpConnections.MinhaException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public Medico getMedicoByID(String jsonString) {

        Medico medico = new Medico();

        try {

            JSONObject jsonObjectMedico;
            jsonObjectMedico = new JSONObject(jsonString);
            Log.d("Acompanhando", jsonObjectMedico.toString());
            String email = jsonObjectMedico.getString("email");
            String nome = jsonObjectMedico.getString("name");
            String crm = jsonObjectMedico.getString("crm");
            String sexo = jsonObjectMedico.getString("sexo");
            String id = jsonObjectMedico.getString("_id");
            String dataNascimento = jsonObjectMedico.getString("DataNascimento");
            // FALTAM ATRIBUTOS
            medico.setEmail(email);
            medico.setNome(nome);
            medico.setCrm(crm);
            medico.setGenero(sexo);
            medico.setId(id);
            // FALTAM SETAR ATRIBUTOS
        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return medico;
    }

    public static void setClientePerfil(Cliente cliente) {
        clientePerfil = cliente;
    }

    private void setMedicoLogado(Medico medico) {
        this.medicoLogado = medico;
    }

    public static Cliente getClientePerfil() { return clientePerfil; }

    public Medico getMedicoLogado() { return this.medicoLogado; }
}
