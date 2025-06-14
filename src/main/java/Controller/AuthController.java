/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;



import Dao.UserDao;
import Model.User;

public class AuthController {
    public User login(String username, String password) {
        UserDao userDao = new UserDao();
        return userDao.checkLogin(username, password);
    }
}
