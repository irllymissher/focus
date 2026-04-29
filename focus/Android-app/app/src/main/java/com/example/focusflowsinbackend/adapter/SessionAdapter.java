package com.example.focusflowsinbackend.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.focusflowsinbackend.R;

import java.util.List;

import focusflow.BusinessLogicLayer.Models.SesionEstudio;
import focusflow.BusinessLogicLayer.Models.TipoDescanso;

/**
 * Adaptador oficial de FocusFlow.
 * Conecta la lista de SesionEstudio proveniente del JAR con la UI de Android[cite: 14, 56].
 */
public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {

    private final List<SesionEstudio> sessions;

    // Constructor que ahora recibe los objetos de dominio de tu JAR[cite: 14, 56]
    public SessionAdapter(List<SesionEstudio> sessions) {
        this.sessions = sessions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_session_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SesionEstudio s = sessions.get(position);

        // 1. Número de sesión (usamos la posición + 1 para el historial visual)
        holder.tvNumber.setText(String.valueOf(position + 1));

        // 2. Objetivo (extraído de tu clase SesionEstudio)
        String objetivo = s.getObjetivo();
        holder.tvGoal.setText((objetivo == null || objetivo.isEmpty()) ? "Sin objetivo" : objetivo);

        // 3. Detalle (Tiempo + Nombre del Descanso)
        // Traducimos el Enum TipoDescanso a un texto legible para el usuario[cite: 14, 16]
        String nombreDescanso = traducirDescanso(s.getTipoDescanso());
        holder.tvDetail.setText(s.getTiempoEstudiado() + " min · " + nombreDescanso);

        // 4. Divisor visual (ocultar en el último elemento)[cite: 56]
        if (holder.divider != null) {
            holder.divider.setVisibility(position < sessions.size() - 1 ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * Mapeo semántico entre la lógica de negocio y la interfaz de usuario[cite: 14, 16].
     */
    private String traducirDescanso(TipoDescanso tipo) {
        if (tipo == null) return "Sin asignar";
        switch (tipo) {
            case RUNNING:      return "Energía alta (Running)";
            case FOTOGRAFIA:   return "Distracción leve (Foto)";
            case ESTIRAMIENTO: return "Cansancio moderado (Yoga)";
            case SNACK_AGUA:   return "Agotamiento (Nutrición)";
            case RESPIRACION:  return "Burnout (Respiración)";
            default:           return "Descanso estándar";
        }
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    // --- ViewHolder optimizado para los IDs de Alejandro[cite: 56] ---
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumber, tvGoal, tvDetail;
        View divider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_session_number);
            tvGoal   = itemView.findViewById(R.id.tv_session_goal);
            tvDetail = itemView.findViewById(R.id.tv_session_detail);
            divider  = itemView.findViewById(R.id.v_divider);
        }
    }
}