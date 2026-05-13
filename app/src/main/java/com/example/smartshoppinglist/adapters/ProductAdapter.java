package com.example.smartshoppinglist.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartshoppinglist.R;
import com.example.smartshoppinglist.data.Product;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> productList;
    private OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;

    public interface OnItemClickListener {
        void onCheckClick(Product product, boolean isChecked);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Product product);
    }

    public ProductAdapter(List<Product> products) {
        this.productList = products;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    public void updateList(List<Product> newList) {
        this.productList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvName.setText(product.getName());
        holder.tvQuantity.setText(product.getQuantity() + " " + product.getUnit());
        holder.cbBought.setChecked(product.isBought());

        if (product.isBought()) {
            holder.tvName.setAlpha(0.5f);
            holder.tvQuantity.setAlpha(0.5f);
            holder.cardView.setCardBackgroundColor(0xFFF5F5F5);
        } else {
            holder.tvName.setAlpha(1f);
            holder.tvQuantity.setAlpha(1f);
            holder.cardView.setCardBackgroundColor(0xFFFFFFFF);
        }

        // Убираем старый listener и добавляем новый правильно
        holder.cbBought.setOnCheckedChangeListener(null);
        holder.cbBought.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (listener != null) {
                listener.onCheckClick(product, isChecked);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onItemLongClick(product);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvQuantity;
        CheckBox cbBought;
        CardView cardView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_product_name);
            tvQuantity = itemView.findViewById(R.id.tv_product_quantity);
            cbBought = itemView.findViewById(R.id.cb_bought);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}