package edu.kpi5.dbcoursework.dbaccess.coredb;

import jakarta.annotation.security.DeclareRoles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Deprecated
@Repository
public interface UserRepository extends CrudRepository<User, String> {
    List<User> findAll();
}
