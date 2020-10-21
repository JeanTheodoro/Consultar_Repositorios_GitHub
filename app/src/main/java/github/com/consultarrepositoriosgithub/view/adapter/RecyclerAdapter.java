package github.com.consultarrepositoriosgithub.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import github.com.consultarrepositoriosgithub.R;
import github.com.consultarrepositoriosgithub.view.model.GitHubRepo;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context context;
    private List<GitHubRepo> itemRepositorio;

    public RecyclerAdapter(android.content.Context context, List<GitHubRepo> repositorio){

        this.context = context;
        this.itemRepositorio =repositorio;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView nome;
        private TextView descricao;
        private TextView linguagem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.textView_nome);
            descricao = itemView.findViewById(R.id.textView_description);
            linguagem = itemView.findViewById(R.id.tetxView_linguagem);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_view, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nome.setText(itemRepositorio.get(position).getName());
        holder.descricao.setText(itemRepositorio.get(position).getDescription());
        holder.linguagem.setText(itemRepositorio.get(position).getLanguage());
    }

    @Override
    public int getItemCount() {
        return itemRepositorio.size();
    }



}
