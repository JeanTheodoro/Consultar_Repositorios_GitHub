package github.com.consultarrepositoriosgithub.view.network;

import java.util.List;

import github.com.consultarrepositoriosgithub.view.model.GitHubRepo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubServiceRepos {

    @GET("users/{user}/repos")
    Call<List<GitHubRepo>> getRepo (@Path("user") String user);
}
