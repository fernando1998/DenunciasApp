package com.cruz.denunciasapp.Repositorio;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cruz.denunciasapp.Models.Denuncia;
import com.cruz.denunciasapp.R;
import com.cruz.denunciasapp.Services.ApiService;
import com.cruz.denunciasapp.Services.ApiServiceGenerator;
import com.cruz.denunciasapp.Services.ResponseMessage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by FERNANDO on 16/11/2017.
 */

public class DenunciasAdapter extends RecyclerView.Adapter<DenunciasAdapter.ViewHolder> {

    private static final String TAG = DenunciasAdapter.class.getSimpleName();
    private List<Denuncia> denuncias;




    public DenunciasAdapter(){

        this.denuncias = new ArrayList<>();
    }

    public void setDenuncias(List<Denuncia> denuncias){

        this.denuncias = denuncias;
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView fotoImage;

        public TextView tituloText;
        public TextView comentarioText;
        public TextView   user_inpt;

        public ImageButton menuButton;



        public ViewHolder(View itemView) {
            super(itemView);
            fotoImage = (ImageView) itemView.findViewById(R.id.foto_image);
            tituloText = (TextView) itemView.findViewById(R.id.titulo_text);
            comentarioText = (TextView) itemView.findViewById(R.id.comentario_text);
            user_inpt = (TextView) itemView.findViewById(R.id.user_inpt);
            menuButton = (ImageButton) itemView.findViewById(R.id.menu_button);



        }
    }

    @Override
    public DenunciasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_denuncia, parent, false);
        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(DenunciasAdapter.ViewHolder viewHolder, final int position) {

        final Denuncia denuncia = this.denuncias.get(position);

        viewHolder.tituloText.setText(denuncia.getTitulo());
        viewHolder.comentarioText.setText(denuncia.getComentario());


        String url = ApiService.API_BASE_URL + "/images/" + denuncia.getImagen();
        Picasso.with(viewHolder.itemView.getContext()).load(url).into(viewHolder.fotoImage);

        viewHolder.user_inpt.setText(denuncia.getUsername());

            Log.d("user_name:",":"+denuncia.getUsername());

        viewHolder.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.remove_button:

                                ApiService service = ApiServiceGenerator.createService(ApiService.class);


                                Call<ResponseMessage> call = service.destroyDenuncia(denuncia.getId());

                                call.enqueue(new Callback<ResponseMessage>() {
                                    @Override
                                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                                        try {

                                            int statusCode = response.code();
                                            Log.d(TAG, "HTTP status code: " + statusCode);

                                            if (response.isSuccessful()) {

                                                ResponseMessage responseMessage = response.body();
                                                Log.d(TAG, "responseMessage: " + responseMessage);

                                                // Eliminar item del recyclerView y notificar cambios
                                                denuncias.remove(position);
                                                notifyItemRemoved(position);
                                                notifyItemRangeChanged(position, denuncias.size());

                                                Toast.makeText(v.getContext(), responseMessage.getMessage(), Toast.LENGTH_LONG).show();

                                            } else {
                                                Log.e(TAG, "onError: " + response.errorBody().string());
                                                throw new Exception("Error en el servicio");
                                            }

                                        } catch (Throwable t) {
                                            try {
                                                Log.e(TAG, "onThrowable: " + t.toString(), t);
                                                Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                            }catch (Throwable x){}
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseMessage> call, Throwable t) {
                                        Log.e(TAG, "onFailure: " + t.toString());
                                        Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                });

                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });




    }

    @Override
    public int getItemCount() {
        return this.denuncias.size();
    }

}
