package com.example.focusflowsinbackend.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.example.focusflowsinbackend.R;

/**
 * Adapter para la lista de opciones de descanso en DescansoActivity.
 * Resalta la opción recomendada por el motor de decisión.
 * Permite selección única.
 */
public class BreakOptionAdapter extends RecyclerView.Adapter<BreakOptionAdapter.ViewHolder> {

    public interface OnBreakSelectedListener {
        void onBreakSelected(int level);
    }

    // ── Datos de cada opción ─────────────────────────────────────────
    private static final String[] ICONS  = { "⚡", "🧩", "🧘", "🍎", "🫁" };
    private static final String[] LABELS = {
            "Energía alta", "Distracción leve", "Cansancio moderado",
            "Agotamiento severo", "Burnout"
    };
    private static final String[] SUBS = {
            "Actividad física", "Reto mental", "Estiramientos",
            "Nutrición", "Respiración"
    };

    private final int                     recommendedLevel; // 1-5
    private final OnBreakSelectedListener listener;
    private int                           selectedLevel = -1;

    public BreakOptionAdapter(int recommendedLevel, OnBreakSelectedListener listener) {
        this.recommendedLevel = recommendedLevel;
        this.listener         = listener;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_break_option, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int level = position + 1; // 1-indexed
        Context ctx = holder.itemView.getContext();

        holder.tvIcon.setText(ICONS[position]);
        holder.tvLabel.setText(LABELS[position]);
        holder.tvSub.setText(SUBS[position]);

        // Mostrar/ocultar badge "Recomendado"
        boolean isRecommended = (level == recommendedLevel && selectedLevel == -1);
        holder.tvRecommended.setVisibility(isRecommended ? View.VISIBLE : View.GONE);

        // Estado de selección
        boolean isSelected = (level == selectedLevel);
        holder.tvCheck.setVisibility(isSelected ? View.VISIBLE : View.GONE);
        holder.card.setStrokeColor(isSelected
                ? ctx.getColor(R.color.purple_primary)
                : ctx.getColor(R.color.border));
        holder.card.setCardBackgroundColor(isSelected
                ? ctx.getColor(R.color.purple_soft)
                : ctx.getColor(R.color.surface));

        holder.card.setOnClickListener(v -> {
            int prev = selectedLevel;
            selectedLevel = level;
            notifyItemChanged(prev - 1 < 0 ? recommendedLevel - 1 : prev - 1);
            notifyItemChanged(position);
            if (listener != null) listener.onBreakSelected(level);
        });
    }

    @Override public int getItemCount() { return LABELS.length; }

    // ── ViewHolder ───────────────────────────────────────────────────
    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView card;
        TextView tvIcon, tvLabel, tvSub, tvRecommended, tvCheck;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card          = (MaterialCardView) itemView;
            tvIcon        = itemView.findViewById(R.id.tv_break_icon);
            tvLabel       = itemView.findViewById(R.id.tv_break_label);
            tvSub         = itemView.findViewById(R.id.tv_break_sub);
            tvRecommended = itemView.findViewById(R.id.tv_recommended_badge);
            tvCheck       = itemView.findViewById(R.id.tv_check);
        }
    }
}
