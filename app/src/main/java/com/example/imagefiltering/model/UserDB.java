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

    //Practice 1. show the list in a spinner in Home activity
    public ArrayList<User> GetAllUsers(){

        return UserList;
    }

    //2. from spinner user will select a name and click delete
    public boolean deleteUser(String name){
        return true;

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
