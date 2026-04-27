package rs.ac.uns.acs.nais.workflow_service.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import rs.ac.uns.acs.nais.workflow_service.dto.FacilityDTO;
import rs.ac.uns.acs.nais.workflow_service.model.Accommodation;
import rs.ac.uns.acs.nais.workflow_service.model.Facility;

import java.util.List;

public interface AccommodationRepository extends Neo4jRepository<Accommodation, Long> {

    @Query("MATCH (a:Accommodation) RETURN coalesce(max(a.id), 0)")
    Long findMaxId();

    @Query("""
    MATCH (a:Accommodation {id: $accommodationId}), (f:Facility {id: $facilityId})
    WHERE a.type = 'HOTEL'
    MERGE (a)-[:HAS_FACILITY]->(f)
    RETURN a
    """)
    Accommodation addFacilityToHotel(Long accommodationId, Long facilityId);

    @Query("""
    MATCH (a:Accommodation {id: $accommodationId})-[:HAS_FACILITY]->(f:Facility)
    RETURN f.id AS id,
           f.name AS name
    """)
    List<FacilityDTO> getFacilitiesForAccommodation(Long accommodationId);

    @Query("""
    MATCH (f:Facility {id: $facilityId})<-[:HAS_FACILITY]-(a:Accommodation)
    RETURN a
    """)
    List<Accommodation> getAccommodationsByFacility(Long facilityId);

    @Query("""
    MATCH (a:Accommodation {id: $accommodationId})-[r:HAS_FACILITY]->(f:Facility {id: $facilityId})
    DELETE r
    """)
    void removeFacilityFromAccommodation(Long accommodationId, Long facilityId);
}