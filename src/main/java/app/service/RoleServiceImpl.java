package app.service;

import app.dao.RoleDAO;
import app.entity.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleDAO roleDAO;

    @Override
    public RoleEntity getRole(int id) {
        return roleDAO.getRole(id);
    }

    @Override
    public List<RoleEntity> getRoles() {
        return roleDAO.getRoles();
    }
}
