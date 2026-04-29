package com.example.focusflowsinbackend.model; // <-- Ajusta a tu paquete

import android.os.Parcel;
import android.os.Parcelable;

/**
 * MOCHILA TEMPORAL (DTO Pasivo)
 * Solo sirve para llevar los datos escritos por el usuario (objetivo, tiempo)
 * entre pantallas hasta que lleguemos al Presentador de Evaluación.
 * NO CONTIENE LÓGICA DE NEGOCIO.
 */
public class SessionData implements Parcelable {

    public static final String EXTRA_KEY = "session_data";

    private String goal;
    private int minutes;
    private int sessionNumber;
    private int stressLevel;
    private int breakLevel; // Guardará el nivel de 1 a 5 SOLO para que la UI sepa qué dibujar

    public SessionData() {
        this.goal = "";
        this.minutes = 90;
        this.sessionNumber = 1;
        this.stressLevel = 5;
        this.breakLevel = 3;
    }

    // --- GETTERS Y SETTERS PUROS ---
    public String getGoal() { return goal; }
    public void setGoal(String goal) { this.goal = goal; }

    public int getMinutes() { return minutes; }
    public void setMinutes(int minutes) { this.minutes = minutes; }

    public int getSessionNumber() { return sessionNumber; }
    public void setSessionNumber(int n) { this.sessionNumber = n; }

    public int getStressLevel() { return stressLevel; }
    public void setStressLevel(int s) { this.stressLevel = s; }

    public int getBreakLevel() { return breakLevel; }
    public void setBreakLevel(int b) { this.breakLevel = b; }

    // --- CÓDIGO ABURRIDO DE PARCELABLE (Para que Android no se queje) ---
    protected SessionData(Parcel in) {
        goal = in.readString();
        minutes = in.readInt();
        sessionNumber = in.readInt();
        stressLevel = in.readInt();
        breakLevel = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(goal);
        dest.writeInt(minutes);
        dest.writeInt(sessionNumber);
        dest.writeInt(stressLevel);
        dest.writeInt(breakLevel);
    }

    @Override
    public int describeContents() { return 0; }

    public static final Creator<SessionData> CREATOR = new Creator<SessionData>() {
        @Override
        public SessionData createFromParcel(Parcel in) { return new SessionData(in); }
        @Override
        public SessionData[] newArray(int size) { return new SessionData[size]; }
    };
}