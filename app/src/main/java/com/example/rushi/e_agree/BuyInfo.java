package com.example.rushi.e_agree;

/**
 * Created by Rushi on 18-04-2017.
 */

public class BuyInfo {
    String imagePath,name,price,quantity,mobileno,email,address;
    BuyInfo(String imagePath,String name,String price,String quantity,String mobileno,String email,String address)
    {
        this.imagePath=imagePath;
        this.name=name;
        this.price=price;
        this.quantity=quantity;
        this.mobileno=mobileno;
        this.email=email;
        this.address=address;
    }
    public String toString()
    {
        return " "+imagePath+" "+name+" "+price+" "+quantity+" "+mobileno+" "+email+" "+address;
    }
}
