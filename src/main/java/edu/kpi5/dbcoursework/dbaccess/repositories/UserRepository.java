package edu.kpi5.dbcoursework.dbaccess.repositories;

import edu.kpi5.dbcoursework.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
