package daoimpl;

import dao.Users_Dao;
import model.Users;
import util.XJdbc;

import java.sql.ResultSet;
import java.util.List;

public class Users_Daoimpl implements Users_Dao {
    private String sqlCreateUsers = "insert into users (username, password, email) values (?, ?, ?)";
    private String sqlUpdateUsers;
    private String sqlDeleteUsers;
    private String sqlCheckLogin = "SELECT * FROM users WHERE username=? AND password=?";
    @Override
    public void create(Users entity) {
        XJdbc.executeUpdate(sqlCreateUsers, entity.getUsername(), entity.getPassword(), entity.getEmail());
    }

    @Override
    public void update(Users entity) {

    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public List<Users> findAll() {
        return List.of();
    }

    @Override
    public Users findById(String s) {
        return null;
    }

    public boolean checkLogin(String username, String password) {
        try {
            ResultSet rs = XJdbc.executeQuery(sqlCheckLogin, username, password);
            return rs.next(); // có bản ghi → true
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
