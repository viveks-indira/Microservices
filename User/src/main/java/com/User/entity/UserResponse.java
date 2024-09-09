package com.User.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {

    private String clientId;
    private String name;
    private String email;
    private String phone;
    private String city;
    private LocalDate lastTradeDate;


//    public UserResponse(String clientId ,String name,String email,String phone,
////                        String city,LocalDate lastTradeDate){
////        clientId=this.clientId;
////        name=this.name;
////        email=this.email;
////        phone=this.phone;
////        city=this.city;
////        lastTradeDate=this.lastTradeDate;
////
////    }
}
