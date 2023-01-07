package edu.kpi5.dbcoursework.dbaccess.userdb;

import edu.kpi5.dbcoursework.entities.userdb.*;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import reactor.core.publisher.Mono;

public interface AccessLevelRepository extends ReactiveNeo4jRepository<AccessLevel, String> {

	Mono<User> findOneByName(String name);
}