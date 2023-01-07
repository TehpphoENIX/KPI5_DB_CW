package edu.kpi5.dbcoursework.dbaccess.userdb;

import edu.kpi5.dbcoursework.entities.userdb.*;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import reactor.core.publisher.Mono;
import java.util.Set;

public interface UserRepository extends ReactiveNeo4jRepository<User, String> {

	public Mono<User> findOneByLogin(String login);

	public Mono<Set<User>> findAllByAccess(AccessLevel accessLevel);
}