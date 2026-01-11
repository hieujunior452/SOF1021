package daoimpl;

import dao.Users_Dao;
import model.Users;
import util.XJdbc;
import util.XQuery;

import java.sql.ResultSet;
import java.util.List;

public class Users_Daoimpl implements Users_Dao {

    private String sqlCreateUsers = "INSERT INTO Users (Username, Password, FullName, Phone, Email) VALUES (?, ?, ?, ?, ?)";
    private String sqlUpdateUsers = "UPDATE Users SET FullName=?, Phone=?, Email=? WHERE UserId=?";
    private String sqlDeleteUsers = "DELETE FROM Users WHERE UserId=?";
    private String sqlFindById = "SELECT * FROM Users WHERE UserId=?";
    private String sqlFindAll = "SELECT * FROM Users";
    private String sqlCheckLogin = "SELECT * FROM Users WHERE Username=? AND Password=?";
    private String sqlFindByUsername = "SELECT * FROM Users WHERE Username=?";
    
    @Override
    public void create(Users entity) {
        XJdbc.executeUpdate(sqlCreateUsers, 
            entity.getUsername(), 
            entity.getPassword(), 
            entity.getFullName(), 
            entity.getPhone(), 
            entity.getEmail());
    }

    @Override
    public void update(Users entity) {
        XJdbc.executeUpdate(sqlUpdateUsers, entity.getFullName(), entity.getPhone(), entity.getEmail(), entity.getUserId());
    }

    @Override
    public void deleteById(Integer integer) {
        XJdbc.executeUpdate(sqlDeleteUsers, integer);
    }

    @Override
    public Users findById(Integer integer) {
        return XQuery.getSingleBean(Users.class, sqlFindById, integer);
    }

    @Override
    public List<Users> findAll() {
        return XQuery.getBeanList(Users.class, sqlFindAll);
    }

    public boolean checkLogin(String username, String password) {
        try {
            ResultSet rs = XJdbc.executeQuery(sqlCheckLogin, username, password);
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Users findByUsername(String username) {
        return XQuery.getSingleBean(Users.class, sqlFindByUsername, username);
    }

}
