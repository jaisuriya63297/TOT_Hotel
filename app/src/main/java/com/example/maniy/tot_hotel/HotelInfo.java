package com.example.maniy.tot_hotel;


public class HotelInfo {

    private String id;
    private String hotelName;
    private String hotelAddress;
    private String employeeNos;
    private String deliveryBoyNos;
    private String food1;
    private String food2;
    private String food3;
    private String food4;
    private String food5;

    private HotelInfo(){

    }


    public HotelInfo(String id, String hotelName, String hotelAddress, String employeeNos, String coacdeliveryBoyNos, String food1, String food2, String food3, String food4, String food5) {
        this.id = id;
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.employeeNos = employeeNos;
        this.deliveryBoyNos = coacdeliveryBoyNos;
        this.food1 = food1;
        this.food2 = food2;
        this.food3 = food3;
        this.food4 = food4;
        this.food5 = food5;
    }



    public String getHotelName() {
        return hotelName;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public String getEmployeeNos() {
        return employeeNos;
    }

    public String getDeliveryBoyNos() {
        return deliveryBoyNos;
    }

    public String getFood1() {
        return food1;
    }

    public String getFood2() {
        return food2;
    }

    public String getFood3() {
        return food3;
    }

    public String getFood4() {
        return food4;
    }

    public String getFood5() {
        return food5;
    }
}
