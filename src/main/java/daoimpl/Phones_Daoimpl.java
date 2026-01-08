package daoimpl;

import dao.Phones_Dao;
import model.Phones;
import util.XQuery;

import java.util.List;

public class Phones_Daoimpl implements Phones_Dao {
    private String sqlFindAll = "select * from phones";
    @Override
    public void create(Object entity) {

    }

    @Override
    public void update(Object entity) {

    }

    @Override
    public void deleteById(Object o) {

    }

    @Override
    public List findAll() {
        return XQuery.getBeanList(Phones.class, sqlFindAll);
    }

    @Override
    public Object findById(Object o) {
        return null;
    }
}
