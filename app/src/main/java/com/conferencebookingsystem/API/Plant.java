package com.conferencebookingsystem.API;

import java.util.ArrayList;

public class Plant<R extends Room, I extends Image, A extends Address> {
    int id;

    ArrayList<I> image;
    ArrayList<R> room;
    ArrayList<A> address;


    public Plant(int id) {
        this.id = id;
        this.image = new ArrayList<>();
        this.room = new ArrayList<>();
        this.address = new ArrayList<>();
    }

    public void addImage(I newImage){
        image.add(newImage);
    }

    public void addRoom(R newRoom){
        room.add(newRoom);
    }

    public void addAddress(A newAddress){
        address.add(newAddress);
    }

    public int getId() {
        return id;
    }

    public ArrayList<I> getImage() {
        return image;
    }

    public ArrayList<R> getRoom() {
        return room;
    }

    public ArrayList<A> getAddress() {
        return address;
    }
}
