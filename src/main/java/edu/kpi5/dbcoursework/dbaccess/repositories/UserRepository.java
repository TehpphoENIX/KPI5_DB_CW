package edu.kpi5.dbcoursework.dbaccess.repositories;

import edu.kpi5.dbcoursework.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    List<User> findAll();
}
