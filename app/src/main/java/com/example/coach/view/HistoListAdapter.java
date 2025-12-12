package com.example.coach.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coach.R;
import com.example.coach.api.ICallbackApi;
import com.example.coach.contract.IHistoView;
import com.example.coach.model.Profil;
import com.example.coach.presenter.HistoPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoListAdapter extends RecyclerView.Adapter<HistoListAdapter.ViewHolder> {

    private List<Profil> profils;
    private IHistoView vue;

    public HistoListAdapter(List<Profil> profils, IHistoView vue) {
        this.profils = profils;
        this.vue = vue;
    }

    /**
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context parentContext = parent.getContext();
        LayoutInflater layout = LayoutInflater.from(parentContext);

        View view = layout.inflate(R.layout.layout_list_histo, parent, false);
        return new ViewHolder(view);
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Double img = profils.get(position).getImg();
        String img1desimal = String.format("%.1f", img);
        holder.txtListIMG.setText(img1desimal);

        Date dateMesure = profils.get(position).getDateMesure();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String dateFormatee = sdf.format(dateMesure);
        holder.txtListDate.setText(dateFormatee);
    }

    /**
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return profils.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView txtListDate;
        public final TextView txtListIMG;
        public final ImageButton btnListSuppr;
        private HistoPresenter presenter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtListDate = (TextView) itemView.findViewById(R.id.txtListDate);
            txtListIMG = (TextView) itemView.findViewById(R.id.txtListIMG);
            btnListSuppr = (ImageButton) itemView.findViewById(R.id.btnListSuppr);
            init();
        }

        private void init() {
            presenter = new HistoPresenter(vue);
            btnListSuppr.setOnClickListener(v -> btnListSuppr_clic());
            txtListDate.setOnClickListener(v -> txtListDateOrImg_clic());
            txtListIMG.setOnClickListener(v -> txtListDateOrImg_clic());
        }

        private void btnListSuppr_clic() {
            int position = getBindingAdapterPosition();
            presenter.supprProfil(profils.get(position), new ICallbackApi<Void>() {
                @Override
                public void onSuccess(Void result) {
                    profils.remove(position);
                    notifyItemRemoved(position);
                }

                @Override
                public void onError() {

                }
            });
        }

        private void txtListDateOrImg_clic(){
            int position =  getBindingAdapterPosition();
            presenter.transfertProfil(profils.get(position));
        }
    }
}
