package rs.ac.uns.acs.nais.recommendation_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.recommendation_service.dto.*;
import rs.ac.uns.acs.nais.recommendation_service.mapper.UserMapper;
import rs.ac.uns.acs.nais.recommendation_service.model.User;
import rs.ac.uns.acs.nais.recommendation_service.service.impl.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest request) {
        User user = UserMapper.toEntity(request);
        User saved = userService.save(user);
        return ResponseEntity.ok(UserMapper.toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        List<UserResponse> users = userService.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);

        return user.map(u -> ResponseEntity.ok(UserMapper.toResponse(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id,
                                               @RequestBody UserUpdateRequest request) {
        User updated = userService.update(id, request);
        return ResponseEntity.ok(UserMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/viewed")
    public ResponseEntity<Void> addOrUpdateViewed(@RequestBody ViewedRequestDTO dto) {
        userService.addOrUpdateViewed(
                dto.getUserId(),
                dto.getArrangementId()
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/viewed")
    public ResponseEntity<Void> deleteViewedRelationship(@RequestParam Long userId, @RequestParam Long arrangementId) {
        userService.deleteViewedRelationship(userId, arrangementId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/viewed")
    public ResponseEntity<List<ViewedArrangementResponse>> getViewedRelationships(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findUserWithViewedRelationships(userId));
    }

    @PostMapping("/booked")
    public ResponseEntity<Void> addOrUpdateBooked(@RequestBody BookedRequestDTO dto) {
        userService.addOrUpdateBooked(
                dto.getUserId(),
                dto.getArrangementId(),
                dto.getPersons(),
                dto.getTotalPrice()
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/booked")
    public ResponseEntity<Void> deleteBookedRelationship(@RequestParam Long userId, @RequestParam Long arrangementId) {
        userService.deleteBookedRelationship(userId, arrangementId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/booked")
    public ResponseEntity< List<BookedArrangementResponse>> getBookedRelationships(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findUserWithBookedRelationships(userId));
    }

    @GetMapping("/{userId}/recommendations/viewed")
    public ResponseEntity<List<ArrangementRecommendationDto>> recommendBasedOnViewed(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.recommendBasedOnViewed(userId));
    }

    @GetMapping("/{userId}/recommendations/booked")
    public ResponseEntity<List<ArrangementRecommendationDto>> recommendBasedOnBooked(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.recommendBasedOnBooked(userId));
    }

}