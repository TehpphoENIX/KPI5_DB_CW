package edu.kpi5.dbcoursework.dbaccess.userdb;

import edu.kpi5.dbcoursework.entities.userdb.*;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import java.util.Set;

public interface UserRepository extends Neo4jRepository<User, String> {
}