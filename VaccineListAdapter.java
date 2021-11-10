
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VaccineListAdapter extends RecyclerView.Adapter<VaccineListAdapter.VaccineViewHolder> {

    VaccineItems[] vIDs;

    public VaccineListAdapter(VaccineItems[] vIDs){
        this.vIDs = vIDs;
    }

    @Override
    public int getItemCount() {
        return vIDs.length;
    }

    @NonNull
    @Override
    public VaccineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_vaccine_items, parent, false);
        return new VaccineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VaccineViewHolder holder, int position) {
        holder.bind(vIDs[position]);
    }

    public static class VaccineViewHolder extends RecyclerView.ViewHolder {

    ImageView jabImg;
    TextView vidTV;
    TextView vNameTV;
    TextView vmfTV;

    public VaccineViewHolder(@NonNull View itemView) {
        super(itemView);
        jabImg = itemView.findViewById(R.id.jab_image);
        vidTV = itemView.findViewById(R.id.tv_vaccine_id);
        vNameTV = itemView.findViewById(R.id.tv_vaccine_name);
        vmfTV = itemView.findViewById(R.id.tv_vaccine_mf);
    }

    @SuppressLint("SetTextI18n")
    public void bind(VaccineItems vID) {
        vidTV.setText(vID.vID);
        vNameTV.setText(vID.vName);
        vmfTV.setText(vID.manuFacturer);
    }
}
}
