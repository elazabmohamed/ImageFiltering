package com.example.imagefiltering.model;

import java.util.ArrayList;

public class UserDB {
    private static ArrayList<User> UserList;

    public UserDB(){
        UserList = new ArrayList<>();
    }

    public int AddUser(User u){
        UserList.add(u);
        return 1;
    }

    public ArrayList<User> GetAllUsers(){

        return UserList;
    }

    public static boolean Login(String username,String password){
        boolean result = false;

        if (UserList!=null) {
            for (User user : UserList) {
                if (user.getUsername().toLowerCase().equals(username.toLowerCase()) && user.getPassword().equals(password)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
