package edu.kpi5.dbcoursework.dbaccess.userdb;

import edu.kpi5.dbcoursework.entities.userdb.*;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Set;

public interface UserRepository extends Neo4jRepository<User, String> {
    @Override
    @Query("""
           MATCH (user:User) 
           RETURN user{.login, .password, User_HAS_ACCESS_LEVEL_AccessLevel: [(user)-[:HAS_ACCESS_LEVEL]->(user_accessLevels:AccessLevel) | user_accessLevels{.name}]}
           """)
    List<User> findAll();
}