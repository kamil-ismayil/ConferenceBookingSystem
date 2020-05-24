package com.conferencebookingsystem.API;

public class Seat {
    int id, numberOfSeat;
    String seat_name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfSeat() {
        return numberOfSeat;
    }

    public void setNumberOfSeat(int numberOfSeat) {
        this.numberOfSeat = numberOfSeat;
    }

    public String getSeat_name() {
        return seat_name;
    }

    public void setSeat_name(String seat_name) {
        this.seat_name = seat_name;
    }
}
