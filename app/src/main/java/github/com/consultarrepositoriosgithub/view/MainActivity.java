package github.com.consultarrepositoriosgithub.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import github.com.consultarrepositoriosgithub.R;
import github.com.consultarrepositoriosgithub.view.adapter.RecyclerAdapter;
import github.com.consultarrepositoriosgithub.view.model.GitHubRepo;
import github.com.consultarrepositoriosgithub.view.network.ClientRetrofit;
import github.com.consultarrepositoriosgithub.view.network.GitHubServiceRepos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Retrofit mRetrofit;
    private ImageView mPesquisar;
    private EditText editTextPequisa;
    private List<GitHubRepo> myDados = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mMyAdapter;
    private static final int REQUEST_PERMISSION = 64;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPequisa = findViewById(R.id.editText_pesquisa);
        editTextPequisa.setOnClickListener(this);
        mPesquisar = findViewById(R.id.imagem);
        mPesquisar.setOnClickListener(this);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mMyAdapter = new RecyclerAdapter(getApplicationContext(), myDados);
        mRecyclerView.setAdapter(mMyAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view == mPesquisar) {
            if (temPermissao()) {
                buscarUser();
                fecharTeclado(view);
                mRecyclerView.setVisibility(view.VISIBLE);
            } else {

                solicitaPermissao();
            }

        } else if (view == editTextPequisa) {
            cleanUI(view);
        }
    }

    private void fecharTeclado(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void cleanUI(View view) {

        editTextPequisa.setText("");
        mRecyclerView.setVisibility(view.GONE);

    }


    private void buscarUser() {


        myDados.clear();

        GitHubServiceRepos reposService = ClientRetrofit.getRetrofit().create(GitHubServiceRepos.class);

        String usuario = editTextPequisa.getText().toString();

        Call<List<GitHubRepo>> call = reposService.getRepo(usuario);

        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {

                if (response.isSuccessful()) {
                    myDados.clear();
                    myDados.addAll(response.body());
                    mMyAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {

                Toast.makeText(MainActivity.this, getString(R.string.erro_api), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private boolean temPermissao() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
    }

    private void solicitaPermissao() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
            final Activity activity = this;
            new AlertDialog.Builder(this)
                    .setMessage(R.string.explicacao_permissao)
                    .setPositiveButton(R.string.botao_fornecer, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET}, REQUEST_PERMISSION);
                        }
                    })
                    .setNegativeButton(R.string.botao_nao_fornecer, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.INTERNET
                    },
                    REQUEST_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            for (int i = 0; i < permissions.length; i++) {

                if (permissions[i].equalsIgnoreCase(Manifest.permission.INTERNET) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    buscarUser();
                }

            }
        }
    }

}