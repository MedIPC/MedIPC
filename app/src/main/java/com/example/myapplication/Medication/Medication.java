package com.example.myapplication.Medication;

import java.io.Serializable;
import java.util.ArrayList;

// Classe que guarda a informação de todas as medicações
public class Medication implements Serializable {
    private ArrayList<MedicationData> MedicationData = new ArrayList<>();

    public ArrayList<MedicationData> getMedicationData() { return MedicationData; }

    public void setMedicationData(ArrayList<MedicationData> medicationData) { this.MedicationData = medicationData; }
}


