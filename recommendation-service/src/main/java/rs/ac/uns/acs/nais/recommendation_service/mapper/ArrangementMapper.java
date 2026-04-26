package rs.ac.uns.acs.nais.recommendation_service.mapper;

import rs.ac.uns.acs.nais.recommendation_service.dto.ArrangementRequest;
import rs.ac.uns.acs.nais.recommendation_service.dto.ArrangementResponse;
import rs.ac.uns.acs.nais.recommendation_service.dto.DestinationResponse;
import rs.ac.uns.acs.nais.recommendation_service.dto.TagResponse;
import rs.ac.uns.acs.nais.recommendation_service.model.Arrangement;
import rs.ac.uns.acs.nais.recommendation_service.model.Destination;
import rs.ac.uns.acs.nais.recommendation_service.model.Tag;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ArrangementMapper {

    public static Arrangement toEntity(ArrangementRequest request) {
        Arrangement arrangement = new Arrangement();
        arrangement.setId(request.getId());
        arrangement.setName(request.getName());
        arrangement.setDescription(request.getDescription());
        arrangement.setPrice(request.getPrice());
        arrangement.setDurationDays(request.getDurationDays());
        return arrangement;
    }

    public static ArrangementResponse toResponse(Arrangement arrangement) {
        DestinationResponse destinationResponse = null;

        Destination destination = arrangement.getDestination();
        if (destination != null) {
            destinationResponse = new DestinationResponse(
                    destination.getId(),
                    destination.getName(),
                    destination.getCountry()
            );
        }

        List<TagResponse> tagResponses = Collections.emptyList();
        if (arrangement.getTags() != null) {
            tagResponses = arrangement.getTags()
                    .stream()
                    .map(tag -> new TagResponse(tag.getId(), tag.getName()))
                    .collect(Collectors.toList());
        }

        return new ArrangementResponse(
                arrangement.getId(),
                arrangement.getName(),
                arrangement.getDescription(),
                arrangement.getPrice(),
                arrangement.getDurationDays(),
                destinationResponse,
                tagResponses
        );
    }
}