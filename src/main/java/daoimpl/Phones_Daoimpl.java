package daoimpl;

import dao.Phones_Dao;
import model.Phones;
import util.XJdbc;
import util.XQuery;

import java.util.List;

public class Phones_Daoimpl implements Phones_Dao {
    
    private String sqlCreate = "INSERT INTO Phones (PhoneName, Price, Image, Description, BrandId, Stock) VALUES (?, ?, ?, ?, ?, ?)";
    private String sqlUpdate = "UPDATE Phones SET PhoneName=?, Price=?, Image=?, Description=?, BrandId=?, Stock=? WHERE PhoneId=?";
    private String sqlDelete = "DELETE FROM Phones WHERE PhoneId=?";
    private String sqlFindById = "SELECT * FROM Phones WHERE PhoneId=?";
    private String sqlFindAll = "SELECT * FROM Phones";
    private String sqlFindByBrand = "SELECT * FROM Phones WHERE BrandId=?";
    
    @Override
    public void create(Phones entity) {
        XJdbc.executeUpdate(sqlCreate, 
            entity.getPhoneName(), 
            entity.getPrice(), 
            entity.getImage(), 
            entity.getDescription(), 
            entity.getBrandId(), 
            entity.getStock());
    }

    @Override
    public void update(Phones entity) {
        XJdbc.executeUpdate(sqlUpdate, 
            entity.getPhoneName(), 
            entity.getPrice(), 
            entity.getImage(), 
            entity.getDescription(), 
            entity.getBrandId(), 
            entity.getStock(), 
            entity.getPhoneId());
    }

    @Override
    public void deleteById(Integer integer) {
        XJdbc.executeUpdate(sqlDelete, integer);
    }

    @Override
    public Phones findById(Integer integer) {
        return XQuery.getSingleBean(Phones.class, sqlFindById, integer);
    }

    @Override
    public List<Phones> findAll() {
        return XQuery.getBeanList(Phones.class, sqlFindAll);
    }
    
    public List<Phones> findByBrand(int brandId) {
        return XQuery.getBeanList(Phones.class, sqlFindByBrand, brandId);
    }
}
