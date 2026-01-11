package daoimpl;

import dao.Brands_Dao;
import model.Brands;
import util.XJdbc;
import util.XQuery;

import java.util.List;

public class Brands_Daoimpl implements Brands_Dao {

    private String sqlCreate = "INSERT INTO Brands (BrandName) VALUES (?)";
    private String sqlUpdate = "UPDATE Brands SET BrandName=? WHERE BrandId=?";
    private String sqlDelete = "DELETE FROM Brands WHERE BrandId=?";
    private String sqlFindById = "SELECT * FROM Brands WHERE BrandId=?";
    private String sqlFindAll = "SELECT * FROM Brands";

    @Override
    public void create(Brands entity) {
        XJdbc.executeUpdate(sqlCreate, entity.getBrandName());
    }

    @Override
    public void update(Brands entity) {
        XJdbc.executeUpdate(sqlUpdate, entity.getBrandName(), entity.getBrandId());
    }

    @Override
    public void deleteById(Integer integer) {
        XJdbc.executeUpdate(sqlDelete, integer);
    }

    @Override
    public List<Brands> findAll() {
        return XQuery.getBeanList(Brands.class, sqlFindAll);
    }

    @Override
    public Brands findById(Integer integer) {
        return XQuery.getSingleBean(Brands.class, sqlFindById, integer);
    }
}
