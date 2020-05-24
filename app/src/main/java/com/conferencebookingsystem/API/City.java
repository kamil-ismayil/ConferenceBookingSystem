package com.conferencebookingsystem.API;

import java.util.ArrayList;

public class City<P extends Plant> {

    int id;
    ArrayList<P> plant;

    public City(int id) {
        this.id = id;
        this.plant = new ArrayList<>();
    }

    public void addPlant(P newPlant){
        plant.add(newPlant);
    }

    public int getId() {
        return id;
    }

    public ArrayList<P> getPlant() {
        return plant;
    }
}
