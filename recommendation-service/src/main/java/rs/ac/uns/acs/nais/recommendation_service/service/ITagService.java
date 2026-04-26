package rs.ac.uns.acs.nais.recommendation_service.service;

import rs.ac.uns.acs.nais.recommendation_service.dto.TagRequest;
import rs.ac.uns.acs.nais.recommendation_service.model.Tag;

import java.util.List;
import java.util.Optional;

public interface ITagService {
    Tag save(Tag tag);
    List<Tag> findAll();
    Optional<Tag> findById(Long id);
    Tag update(Long id, TagRequest tag);
    void delete(Long id);
}