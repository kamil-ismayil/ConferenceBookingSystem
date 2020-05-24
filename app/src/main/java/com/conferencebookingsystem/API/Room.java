package com.conferencebookingsystem.API;

import java.util.ArrayList;
import java.util.HashMap;

public class Room<S extends Seat, I extends Image> {
    int id; //conferenceroom availability id
    int conferenceRoom, block, fullDayPrice, preNoonPrice, afterNoonPrice;
    String conferenceRoomName, conferenceRoomDescription, hoursAvailableFrom, hoursAvailableTo;
    private ArrayList<S> seat = new ArrayList<>();
    private ArrayList<I> image = new ArrayList<>();

    public Room(int id, int conferenceRoom, int block, int fullDayPrice, int preNoonPrice, int afterNoonPrice, String conferenceRoomName, String conferenceRoomDescription, String hoursAvailableFrom, String hoursAvailableTo) {
        this.id = id;
        this.conferenceRoom = conferenceRoom;
        this.block = block;
        this.fullDayPrice = fullDayPrice;
        this.preNoonPrice = preNoonPrice;
        this.afterNoonPrice = afterNoonPrice;
        this.conferenceRoomName = conferenceRoomName;
        this.conferenceRoomDescription = conferenceRoomDescription;
        this.hoursAvailableFrom = hoursAvailableFrom;
        this.hoursAvailableTo = hoursAvailableTo;
        this.seat = new ArrayList<>();
        this.image = new ArrayList<>();
    }

    public void addSeat(S newSeat){
        seat.add(newSeat);
    }

    public void addImage(I newImage){
        image.add(newImage);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConferenceRoom() {
        return conferenceRoom;
    }

    public void setConferenceRoom(int conferenceRoom) {
        this.conferenceRoom = conferenceRoom;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getFullDayPrice() {
        return fullDayPrice;
    }

    public void setFullDayPrice(int fullDayPrice) {
        this.fullDayPrice = fullDayPrice;
    }

    public int getPreNoonPrice() {
        return preNoonPrice;
    }

    public void setPreNoonPrice(int preNoonPrice) {
        this.preNoonPrice = preNoonPrice;
    }

    public int getAfterNoonPrice() {
        return afterNoonPrice;
    }

    public void setAfterNoonPrice(int afterNoonPrice) {
        this.afterNoonPrice = afterNoonPrice;
    }

    public String getConferenceRoomName() {
        return conferenceRoomName;
    }

    public void setConferenceRoomName(String conferenceRoomName) {
        this.conferenceRoomName = conferenceRoomName;
    }

    public String getConferenceRoomDescription() {
        return conferenceRoomDescription;
    }

    public void setConferenceRoomDescription(String conferenceRoomDescription) {
        this.conferenceRoomDescription = conferenceRoomDescription;
    }

    public String getHoursAvailableFrom() {
        return hoursAvailableFrom;
    }

    public void setHoursAvailableFrom(String hoursAvailableFrom) {
        this.hoursAvailableFrom = hoursAvailableFrom;
    }

    public String getHoursAvailableTo() {
        return hoursAvailableTo;
    }

    public void setHoursAvailableTo(String hoursAvailableTo) {
        this.hoursAvailableTo = hoursAvailableTo;
    }

    public ArrayList<S> getSeat() {
        return seat;
    }

    public void setSeat(ArrayList<S> seat) {
        this.seat = seat;
    }

    public ArrayList<I> getImage() {
        return image;
    }

    public void setImage(ArrayList<I> image) {
        this.image = image;
    }
}
