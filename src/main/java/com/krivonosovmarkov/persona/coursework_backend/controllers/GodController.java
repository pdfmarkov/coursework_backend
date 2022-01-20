package com.krivonosovmarkov.persona.coursework_backend.controllers;

import com.krivonosovmarkov.persona.coursework_backend.exceptions.ResourceIsNotValidException;
import com.krivonosovmarkov.persona.coursework_backend.exceptions.ResourceNotFoundException;
import com.krivonosovmarkov.persona.coursework_backend.module.*;
import com.krivonosovmarkov.persona.coursework_backend.module.requested.HumanRequestDto;
import com.krivonosovmarkov.persona.coursework_backend.module.requested.IdRequestDto;
import com.krivonosovmarkov.persona.coursework_backend.module.responses.HumanEventResponseDto;
import com.krivonosovmarkov.persona.coursework_backend.module.responses.HumanResponseDto;
import com.krivonosovmarkov.persona.coursework_backend.module.responses.OpinionResponseDto;
import com.krivonosovmarkov.persona.coursework_backend.module.responses.RelationshipResponseDto;
import com.krivonosovmarkov.persona.coursework_backend.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@CrossOrigin(origins = "${cors.urls}")
@RestController
@RequestMapping("/api/god")
public class GodController {

    @Autowired
    HumanRepository humanRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ShadowRepository shadowRepository;

    @Autowired
    WishRepository wishRepository;

    @PostMapping("/change/wish")
    @Transactional
    public ResponseEntity<?> changeWish(@RequestBody IdRequestDto idRequestDto) {

        Human human = humanRepository.findById(idRequestDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Error: Human is Not Found"));

        if (!human.getIsReal()) throw new ResourceIsNotValidException("Error: Human is Not Real");

        Human doctor = getDoctorFromUser();

        Shadow shadow = shadowRepository.findByHumanHumanId(human.getHumanId())
                .orElseThrow(() -> new ResourceNotFoundException("Error: Shadow is Not Found"));

        Set<Wish> wishes = shadow.getWishes();

        if (wishes.isEmpty()) return ResponseEntity.ok("There are no Wishes...");


        Human wish_human;

        for (Wish wish : wishes) {

            wish_human = wish.getWishHuman();

            if (wish.getType() == TypeOfWish.SELF) {

                if (wish_human.getStatus().getValue() < human.getStatus().getValue()) wish_human.setStatus(human.getStatus());

                Property property;
                for (Opinion opinion : wish_human.getOpinions()) {
                    property = opinion.getProperty();
                    if (propertyRepository.checkProperty(doctor.getHumanId(), property.getPropertyId()) !=null
                        && !propertyRepository.checkProperty(doctor.getHumanId(), property.getPropertyId()))
                        wish_human.getOpinions().remove(opinion);
                }


                Event event;
                for (HumanEvent humanEvent : wish_human.getHumanEventSet()) {
                    event = humanEvent.getEvent();
                    if (eventRepository.checkEvent(doctor.getHumanId(), event.getEventId()) != null
                            && !humanEvent.getIsGood()
                            && eventRepository.checkEvent(doctor.getHumanId(), event.getEventId()))
                        eventRepository.updateEvent(doctor.getHumanId(), event.getEventId(), true);
                }

                humanRepository.save(wish_human);

            }
        }

        return ResponseEntity.ok("Wishes have changed!");
    }

    @PostMapping("/realize/wish")
    @Transactional
    public ResponseEntity<?> realizeWish(@RequestBody IdRequestDto idRequestDto) {

        changeWish(idRequestDto);

        Human human = humanRepository.findById(idRequestDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Error: Human is Not Found"));

        if (!human.getIsReal()) throw new ResourceIsNotValidException("Error: Human is Not Real");

        Human doctor = getDoctorFromUser();

        Shadow shadow = shadowRepository.findByHumanHumanId(human.getHumanId())
                .orElseThrow(() -> new ResourceNotFoundException("Error: Shadow is Not Found"));

        Set<Wish> wishes = shadow.getWishes();

        if (wishes.isEmpty()) return ResponseEntity.ok("There are no Wishes...");

        Human wish_human;

        for (Wish wish : wishes) {

            wish_human = wish.getWishHuman();

            if (wish.getType() == TypeOfWish.SELF) {

                human.setName(wish_human.getName());
                human.setSurname(wish_human.getSurname());
                human.setLocation(wish_human.getLocation());
                human.setSex(wish_human.getSex());
                human.setRace(wish_human.getRace());
                human.setGender(wish_human.getGender());
                human.setTemperament(wish_human.getTemperament());
                human.setStatus(wish_human.getStatus());
                human.setBirthDate(wish_human.getBirthDate());
                human.setDeathDate(wish_human.getDeathDate());

                humanRepository.save(human);

                //relationshipsFrom - human 1 = human
                //relationshipsTo - human 2 = human

                Human other_human;
                Shadow other_shadow;
                Set<Wish> other_wishes;
                for (Relationship relationship: wish_human.getRelationshipsTo()) {
                    other_human = relationship.getHuman1();
                    other_shadow = shadowRepository.findByHumanHumanId(other_human.getHumanId())
                            .orElseThrow(() -> new ResourceNotFoundException("Error: Shadow is Not Found"));

                    other_wishes = other_shadow.getWishes();

                    Human other_wish_human;
                    for (Wish other_wish : other_wishes) {

                        other_wish_human = other_wish.getWishHuman();

                        if (other_wish.getType() == TypeOfWish.SELF) {

                            for (Relationship other_relationship: other_wish_human.getRelationshipsTo()) {

                                if (Objects.equals(other_relationship.getHuman1().getHumanId(), human.getHumanId())) {

                                    other_human.getRelationshipsTo().remove(other_relationship);
                                    human.getRelationshipsTo().remove(relationship);

                                    humanRepository.insertNewRelationship(human.getHumanId(), other_human.getHumanId(), relationship.getType(),
                                            relationship.getStartDate(), relationship.getEndDate());
                                    humanRepository.save(human);
                                    humanRepository.save(other_human);


                                }

                            }

                            for (Relationship other_relationship: other_wish_human.getRelationshipsFrom()) {

                                if (Objects.equals(other_relationship.getHuman2().getHumanId(), human.getHumanId())) {

                                    other_human.getRelationshipsFrom().remove(other_relationship);
                                    human.getRelationshipsTo().remove(relationship);

                                    humanRepository.insertNewRelationship(human.getHumanId(), other_human.getHumanId(), relationship.getType(),
                                            relationship.getStartDate(), relationship.getEndDate());
                                    humanRepository.save(human);
                                    humanRepository.save(other_human);


                                }

                            }

                        }

                    }

                }

                for (Relationship relationship: wish_human.getRelationshipsFrom()) {
                    other_human = relationship.getHuman2();
                    other_shadow = shadowRepository.findByHumanHumanId(other_human.getHumanId())
                            .orElseThrow(() -> new ResourceNotFoundException("Error: Shadow is Not Found"));

                    other_wishes = other_shadow.getWishes();

                    Human other_wish_human;
                    for (Wish other_wish : other_wishes) {

                        other_wish_human = other_wish.getWishHuman();

                        if (other_wish.getType() == TypeOfWish.SELF) {

                            for (Relationship other_relationship: other_wish_human.getRelationshipsTo()) {

                                if (Objects.equals(other_relationship.getHuman1().getHumanId(), human.getHumanId())) {

                                    other_human.getRelationshipsTo().remove(other_relationship);
                                    human.getRelationshipsFrom().remove(relationship);

                                    humanRepository.insertNewRelationship(human.getHumanId(), other_human.getHumanId(), relationship.getType(),
                                            relationship.getStartDate(), relationship.getEndDate());
                                    humanRepository.save(human);
                                    humanRepository.save(other_human);


                                }

                            }

                            for (Relationship other_relationship: other_wish_human.getRelationshipsFrom()) {

                                if (Objects.equals(other_relationship.getHuman2().getHumanId(), human.getHumanId())) {

                                    other_human.getRelationshipsFrom().remove(other_relationship);
                                    human.getRelationshipsFrom().remove(relationship);

                                    humanRepository.insertNewRelationship(human.getHumanId(), other_human.getHumanId(), relationship.getType(),
                                            relationship.getStartDate(), relationship.getEndDate());
                                    humanRepository.save(human);
                                    humanRepository.save(other_human);


                                }

                            }

                        }

                    }

                }

                Property property;
                for (Opinion opinion : wish_human.getOpinions()) {
                    property = opinion.getProperty();

                    if (propertyRepository.checkProperty(doctor.getHumanId(), property.getPropertyId()) != null
                                && propertyRepository.checkProperty(doctor.getHumanId(), property.getPropertyId()))

                        humanRepository.insertNewProperty(human.getHumanId(), property.getPropertyId(), true);
                }


                Event event;
                for (HumanEvent humanEvent : wish_human.getHumanEventSet()) {
                    event = humanEvent.getEvent();
                    if (eventRepository.checkEvent(doctor.getHumanId(), event.getEventId()) != null && !humanEvent.getIsGood())
                        eventRepository.delete(event);
                }


            } else {

                Human real_other_human = wish.getRealOtherHuman();
                real_other_human.setDeathDate(null);
                humanRepository.save(real_other_human);

            }

            shadow.getWishes().remove(wish);
            wish_human.getHumanEventSet().clear();
            wish_human.getOpinions().clear();
            wish_human.getRelationshipsFrom().clear();
            wish_human.getRelationshipsTo().clear();
            wishRepository.delete(wish);
            humanRepository.delete(wish_human);

        }

        return ResponseEntity.ok("Wishes have been realized!");
    }

    @GetMapping("/dammit")
    @Transactional
    public ResponseEntity<?> magic() {

        int size = 10;

        for (int i=1; i<10; i++) {
            List<Human> humanity = humanRepository.findHumansByOrderByHumanId(PageRequest.of(i, size));
            for (Human human : humanity)
                if (human.getIsReal())
                    realizeWish(new IdRequestDto(human.getHumanId()));
        }


        return ResponseEntity.ok("-M-A-G-I-C-");
    }

    private String getCurrentUsername() {

        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

    }

    private Human getDoctorFromUser() {
        return userRepository.findByUsername(getCurrentUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Error: User not found")).getHuman();
    }

    private List<HumanResponseDto> getHumanResponseDtoList(List<Human> humans) {

        List<HumanResponseDto> humanResponseDtoList = new ArrayList<>();

        for (Human human : humans) humanResponseDtoList.add(getHumanResponseDto(human));

        return humanResponseDtoList;

    }

    private HumanResponseDto getHumanResponseDto(Human human) {

        List<HumanEventResponseDto> humanEventResponseDtoList;

        List<RelationshipResponseDto> relationshipResponseDtoList;

        List<OpinionResponseDto> opinionResponseDtoList;

        humanEventResponseDtoList = new ArrayList<>();
        for (HumanEvent humanEvent : human.getHumanEventSet()) {
            humanEventResponseDtoList.add(
                    new HumanEventResponseDto(
                            humanEvent.getEvent().getEventId(),
                            humanEvent.getEvent().getLocation().getName(),
                            humanEvent.getEvent().getName(),
                            humanEvent.getEvent().getDescription(),
                            humanEvent.getEvent().getDate(),
                            humanEvent.getIsGood()
                    )
            );
        }

        opinionResponseDtoList = new ArrayList<>();

        for (Opinion opinion : human.getOpinions()) {
            opinionResponseDtoList.add(
                    new OpinionResponseDto(
                            opinion.getProperty().getPropertyId(),
                            opinion.getProperty().getName(),
                            opinion.getProperty().getDescription(),
                            opinion.getIsGood()
                    )
            );
        }

        relationshipResponseDtoList = new ArrayList<>();

        for(Relationship relationship : human.getRelationshipsFrom()) {
            relationshipResponseDtoList.add(
                    new RelationshipResponseDto(
                            relationship.getHuman2().getName(),
                            relationship.getHuman2().getSurname(),
                            relationship.getType(),
                            relationship.getStartDate(),
                            relationship.getEndDate()
                    )
            );
        }

        for(Relationship relationship : human.getRelationshipsTo()) {
            relationshipResponseDtoList.add(
                    new RelationshipResponseDto(
                            relationship.getHuman1().getName(),
                            relationship.getHuman1().getSurname(),
                            relationship.getType(),
                            relationship.getStartDate(),
                            relationship.getEndDate()
                    )
            );
        }

        return new HumanResponseDto(
                human.getHumanId(),
                human.getName(),
                human.getSurname(),
                human.getLocation().getName(),
                human.getIsReal(),
                human.getIsDoctor(),
                human.getSex().toString(),
                human.getRace(),
                human.getGender(),
                human.getTemperament().toString(),
                human.getStatus().toString(),
                human.getBirthDate(),
                human.getDeathDate(),
                humanEventResponseDtoList,
                relationshipResponseDtoList,
                opinionResponseDtoList);

    }


}
