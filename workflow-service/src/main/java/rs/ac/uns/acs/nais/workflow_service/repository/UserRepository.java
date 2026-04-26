package rs.ac.uns.acs.nais.workflow_service.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import rs.ac.uns.acs.nais.workflow_service.model.User;

public interface UserRepository extends Neo4jRepository<User, Long> {

    @Query("MATCH (u:User) RETURN coalesce(max(u.id), 0)")
    Long findMaxId();

    @Query("""
           MATCH (u:User {id: $id})
           DETACH DELETE u
           """)
    void deleteUserByCustomId(Long id);
}