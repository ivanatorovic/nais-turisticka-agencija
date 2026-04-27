package rs.ac.uns.acs.nais.workflow_service.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import rs.ac.uns.acs.nais.workflow_service.dto.CreatesDTO;
import rs.ac.uns.acs.nais.workflow_service.model.Workflow;

import java.time.LocalDate;
import java.util.List;

public interface WorkflowRepository extends Neo4jRepository<Workflow, Long> {

    @Query("MATCH (w:Workflow) RETURN coalesce(max(w.id), 0)")
    Long findMaxId();

    @Query("""
    MATCH (:User)-[r:CREATES]->(w:Workflow {id: $workflowId})
    RETURN count(r) > 0
    """)
    Boolean workflowAlreadyHasCreator(Long workflowId);

    @Query("""
           MATCH (w:Workflow {id: $id})
           DETACH DELETE w
           """)
    void deleteWorkflowByCustomId(Long id);

    @Query("""
           MATCH (u:User {id: $userId}), (w:Workflow {id: $workflowId})
           MERGE (u)-[r:CREATES]->(w)
           SET r.createdAt = $createdAt
           RETURN w
           """)
    Workflow createCreatesRelationship(Long userId, Long workflowId, LocalDate createdAt);

    @Query("""
           MATCH (:User {id: $userId})-[r:CREATES]->(:Workflow {id: $workflowId})
           RETURN count(r) > 0
           """)
    Boolean existsCreatesRelationship(Long userId, Long workflowId);

    @Query("""
           MATCH (u:User)-[r:CREATES]->(w:Workflow)
           RETURN u.id AS userId, w.id AS workflowId, r.createdAt AS createdAt
           """)
    List<CreatesDTO> getAllCreatesRelationships();

    @Query("""
           MATCH (u:User {id: $userId})-[r:CREATES]->(w:Workflow {id: $workflowId})
           RETURN u.id AS userId, w.id AS workflowId, r.createdAt AS createdAt
           """)
    CreatesDTO getCreatesRelationship(Long userId, Long workflowId);

    @Query("""
           MATCH (:User {id: $userId})-[r:CREATES]->(:Workflow {id: $workflowId})
           SET r.createdAt = $createdAt
           """)
    void updateCreatesRelationship(Long userId, Long workflowId, LocalDate createdAt);

    @Query("""
           MATCH (:User {id: $userId})-[r:CREATES]->(:Workflow {id: $workflowId})
           DELETE r
           """)
    void deleteCreatesRelationship(Long userId, Long workflowId);

    @Query("""
           MATCH (u:User {id: $userId})-[:CREATES]->(w:Workflow)
           RETURN w
           """)
    List<Workflow> getWorkflowsCreatedByUser(Long userId);
}