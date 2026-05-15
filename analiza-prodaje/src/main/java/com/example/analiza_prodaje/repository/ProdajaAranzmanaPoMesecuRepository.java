package com.example.analiza_prodaje.repository;



import com.example.analiza_prodaje.dto.ProdajaAranzmanaStatistikaDTO;
import com.example.analiza_prodaje.model.ProdajaAranzmanaPoMesecu;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface ProdajaAranzmanaPoMesecuRepository
        extends CassandraRepository<ProdajaAranzmanaPoMesecu, UUID> {


    @Query("SELECT * FROM prodaja_aranzmana_po_mesecu " +
            "WHERE aranzman_id = ?0 AND godina = ?1 AND mesec = ?2 AND rezervacija_id = ?3")
    ProdajaAranzmanaPoMesecu findOne(
            UUID aranzmanId,
            Integer godina,
            Integer mesec,
            UUID rezervacijaId
    );

    @Query("DELETE FROM prodaja_aranzmana_po_mesecu " +
            "WHERE aranzman_id = ?0 AND godina = ?1 " +
            "AND mesec = ?2 AND rezervacija_id = ?3")
    void deleteReservation(
            UUID aranzmanId,
            Integer godina,
            Integer mesec,
            UUID rezervacijaId
    );

    @Query("SELECT * FROM prodaja_aranzmana_po_mesecu WHERE aranzman_id = ?0 AND godina = ?1")
    List<ProdajaAranzmanaPoMesecu> findByAranzmanAndGodina(UUID aranzmanId, Integer godina);

    @Query("SELECT * FROM prodaja_aranzmana_po_mesecu WHERE aranzman_id = ?0 AND godina = ?1 AND mesec = ?2")
    List<ProdajaAranzmanaPoMesecu> findByAranzmanGodinaAndMesec(UUID aranzmanId, Integer godina, Integer mesec);

    @Query("SELECT mesec, COUNT(*) AS broj_rezervacija, SUM(ukupna_cena) AS ukupan_prihod " +
            "FROM prodaja_aranzmana_po_mesecu " +
            "WHERE aranzman_id = ?0 AND godina = ?1 " +
            "GROUP BY mesec")
    List<Map<String, Object>> getStatistikaPoMesecimaRaw(UUID aranzmanId, Integer godina);
}
