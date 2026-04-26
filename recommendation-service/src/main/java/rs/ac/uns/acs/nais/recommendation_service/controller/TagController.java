package rs.ac.uns.acs.nais.recommendation_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.recommendation_service.dto.TagRequest;
import rs.ac.uns.acs.nais.recommendation_service.model.Tag;
import rs.ac.uns.acs.nais.recommendation_service.service.impl.TagService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<Tag> create(@RequestBody Tag tag) {
        return ResponseEntity.ok(tagService.save(tag));
    }

    @GetMapping
    public ResponseEntity<List<Tag>> findAll() {
        return ResponseEntity.ok(tagService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> findById(@PathVariable Long id) {
        Optional<Tag> tag = tagService.findById(id);
        return tag.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tag> update(@PathVariable Long id, @RequestBody TagRequest tag) {
        return ResponseEntity.ok(tagService.update(id, tag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}