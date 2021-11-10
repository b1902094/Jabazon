
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BatchItemAdapter extends RecyclerView.Adapter<BatchItemAdapter.BatchViewHolder> {

    BatchItems[] batches;

    public BatchItemAdapter(BatchItems[] batches){
        this.batches = batches;
    }

    @NonNull
    @Override
    public BatchItemAdapter.BatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_batch_items, parent, false);
        return new BatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BatchViewHolder holder, int position) {
        holder.bind(batches[position]);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class BatchViewHolder extends RecyclerView.ViewHolder{
        ImageView batchImg;
        TextView batchNoTV;
        TextView vBName;
        TextView numAppts;
        TextView expDate;
        TextView qtyAdministered;
        TextView qtyAvailable;

        public BatchViewHolder(@NonNull View itemView){
            super(itemView);
            batchImg = itemView.findViewById(R.id.batch_image);
            batchNoTV = itemView.findViewById(R.id.tv_selected_batchNo);
            vBName = itemView.findViewById(R.id.tv_selected_vName);
            numAppts = itemView.findViewById(R.id.tv_num_pending_appta);
            expDate = itemView.findViewById(R.id.tv_selected_exp_date);
            qtyAdministered = itemView.findViewById(R.id.tv_selected_qty_admind);
            qtyAvailable = itemView.findViewById(R.id.tv_selected_qty_avail);
        }

        @SuppressLint("SetTextI18n")
        public void bind(BatchItems batches){
            batchNoTV.setText(batches.batchNo);
            vBName.setText(batches.vName);
            numAppts.setText(batches.numAppts);
            expDate.setText(batches.expDate);
            qtyAdministered.setText(batches.qtyAdmind);
            qtyAvailable.setText(batches.qtyAvail);
        }



    }
}
