package com.example.focusflowsinbackend.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * MOCHILA TEMPORAL (DTO)
 * Contiene los datos de la sesión y las métricas del escaneo[cite: 57].
 */
public class SessionData implements Parcelable {

    public static final String EXTRA_KEY = "session_data";

    private String goal;
    private int minutes;
    private int sessionNumber;
    private int stressLevel;
    private int breakLevel;

    // Campos recuperados para el Escaneo[cite: 45, 57]
    private int bpm;
    private String scanEnergy;
    private String scanStress;

    public SessionData() {
        this.goal = "";
        this.minutes = 90;
        this.sessionNumber = 1;
        this.stressLevel = 5;
        this.breakLevel = 3;
        this.bpm = 72;
        this.scanEnergy = "Alta";
        this.scanStress = "Bajo";
    }

    // --- NUEVOS GETTERS Y SETTERS PARA EL ESCANEO[cite: 45, 57] ---
    public int getBpm() { return bpm; }
    public void setBpm(int bpm) { this.bpm = bpm; }

    public String getScanEnergy() { return scanEnergy; }
    public void setScanEnergy(String e) { this.scanEnergy = e; }

    public String getScanStress() { return scanStress; }
    public void setScanStress(String s) { this.scanStress = s; }

    // --- GETTERS Y SETTERS ANTERIORES ---
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

    // --- PARCELABLE ACTUALIZADO ---
    protected SessionData(Parcel in) {
        goal = in.readString();
        minutes = in.readInt();
        sessionNumber = in.readInt();
        stressLevel = in.readInt();
        breakLevel = in.readInt();
        bpm = in.readInt();
        scanEnergy = in.readString();
        scanStress = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(goal);
        dest.writeInt(minutes);
        dest.writeInt(sessionNumber);
        dest.writeInt(stressLevel);
        dest.writeInt(breakLevel);
        dest.writeInt(bpm);
        dest.writeString(scanEnergy);
        dest.writeString(scanStress);
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