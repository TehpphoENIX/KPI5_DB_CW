package edu.kpi5.dbcoursework.dbaccess.userdb;

import edu.kpi5.dbcoursework.entities.userdb.*;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import reactor.core.publisher.Mono;

public interface AccessLevelRepository extends Neo4jRepository<AccessLevel, String> {
}
