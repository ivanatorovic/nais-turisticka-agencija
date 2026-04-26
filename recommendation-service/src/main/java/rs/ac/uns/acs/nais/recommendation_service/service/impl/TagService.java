package rs.ac.uns.acs.nais.recommendation_service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.uns.acs.nais.recommendation_service.dto.TagRequest;
import rs.ac.uns.acs.nais.recommendation_service.model.Tag;
import rs.ac.uns.acs.nais.recommendation_service.repository.TagRepository;
import rs.ac.uns.acs.nais.recommendation_service.service.ITagService;

import java.util.List;
import java.util.Optional;

@Service
public class TagService implements ITagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag save(Tag tag) {
        if (tagRepository.existsById(tag.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Tag with id " + tag.getId() + " already exists."
            );
        }

        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return tagRepository.findById(id);
    }

    @Override
    public Tag update(Long id, TagRequest tag) {
        Tag existing = tagRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tag not found with id: " + id
                ));

        existing.setName(tag.getName());

        return tagRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Tag not found with id: " + id
            );
        }

        tagRepository.deleteById(id);
    }
}