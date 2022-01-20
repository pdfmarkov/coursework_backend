package com.krivonosovmarkov.persona.coursework_backend.controllers;

import com.krivonosovmarkov.persona.coursework_backend.exceptions.ResourceNotFoundException;
import com.krivonosovmarkov.persona.coursework_backend.module.*;
import com.krivonosovmarkov.persona.coursework_backend.module.requested.HumanRequestDto;
import com.krivonosovmarkov.persona.coursework_backend.module.requested.UpdateEventRequestDto;
import com.krivonosovmarkov.persona.coursework_backend.module.requested.UpdatePropertyRequestDto;
import com.krivonosovmarkov.persona.coursework_backend.module.responses.*;
import com.krivonosovmarkov.persona.coursework_backend.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "${cors.urls}")
@RestController
@RequestMapping("/api/main")
public class MainController {

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

    final Integer SIZE = 8;

    @PostMapping("/register/doctor")
    @Transactional
    public ResponseEntity<?> registerDoctor(@RequestBody HumanRequestDto humanRequestDto) {

        Human human = new Human();

        human.setName(humanRequestDto.getName());
        human.setSurname(humanRequestDto.getSurname());
        // TODO: Change to give front Locations and user can choose between them
        human.setLocation(locationRepository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("Error: Location is Not Found")));
        human.setIsDoctor(true);
        human.setIsReal(true);
        human.setSex(Sex.valueOf(humanRequestDto.getSex()));
        human.setGender(humanRequestDto.getGender());
        human.setRace(humanRequestDto.getRace());
        human.setTemperament(Temperament.valueOf(humanRequestDto.getTemperament()));
        human.setStatus(Status.valueOf(humanRequestDto.getStatus()));
        human.setBirthDate(LocalDateTime.now());

        human = humanRepository.save(human);

        User user = userRepository.findByUsername(getCurrentUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Error: User not found"));

        user.setHuman(human);

        user = userRepository.save(user);

        return ResponseEntity.ok(user.getHuman().getHumanId());
    }

    @PostMapping("/change/doctor")
    @Transactional
    public ResponseEntity<?> changeDoctor(@RequestBody HumanRequestDto humanRequestDto) {

        Human doctor = getDoctorFromUser();

        if (humanRequestDto.getName() != null) doctor.setName(humanRequestDto.getName());
        if (humanRequestDto.getSurname() != null) doctor.setSurname(humanRequestDto.getSurname());
        if (humanRequestDto.getSex() != null) doctor.setSex(Sex.valueOf(humanRequestDto.getSex()));
        if (humanRequestDto.getGender() != null) doctor.setGender(humanRequestDto.getGender());
        if (humanRequestDto.getRace() != null) doctor.setRace(humanRequestDto.getRace());
        if (humanRequestDto.getTemperament() != null) doctor.setTemperament(Temperament.valueOf(humanRequestDto.getTemperament()));
        if (humanRequestDto.getStatus() != null) doctor.setStatus(Status.valueOf(humanRequestDto.getStatus()));

        return ResponseEntity.ok(getHumanResponseDto(humanRepository.save(doctor)));
    }

    @PostMapping("/change/event")
    @Transactional
    public ResponseEntity<?> changeEvent(@RequestBody UpdateEventRequestDto updateEventRequestDto) {

        Human doctor = getDoctorFromUser();

        Event event = eventRepository.findById(updateEventRequestDto.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Error: Event not found"));

        if (eventRepository.checkEventDoctor(doctor.getHumanId(), event.getEventId()) != null) {
            eventRepository.updateEvent(doctor.getHumanId(), event.getEventId(), updateEventRequestDto.getIsGood());
        } else {
            humanRepository.insertNewEvent(doctor.getHumanId(), event.getEventId(), updateEventRequestDto.getIsGood());
        };

        return ResponseEntity.ok("Event is changed!");
    }

    @PostMapping("/change/property")
    @Transactional
    public ResponseEntity<?> changeProperty(@RequestBody UpdatePropertyRequestDto updatePropertyRequestDto) {

        Human doctor = getDoctorFromUser();

        Property property = propertyRepository.findById(updatePropertyRequestDto.getPropertyId())
                .orElseThrow(() -> new ResourceNotFoundException("Error: Property not found"));

        if (propertyRepository.checkPropertyDoctor(doctor.getHumanId(), property.getPropertyId()) != null) {
            propertyRepository.updateProperty(doctor.getHumanId(), property.getPropertyId(), updatePropertyRequestDto.getIsGood());
        } else {
            humanRepository.insertNewProperty(doctor.getHumanId(), property.getPropertyId(), updatePropertyRequestDto.getIsGood());
        };

        return ResponseEntity.ok("Property is changed!");
    }

    @GetMapping("/get/events")
    public ResponseEntity<?> getEvents() {

        Human doctor = getDoctorFromUser();

        List<Event> events = eventRepository.findAll();

        List<CustomEventResponseDto> customEventResponseDtoList = new ArrayList<>();

        for (Event e : events) {
            customEventResponseDtoList.add(new CustomEventResponseDto(
                    e.getEventId(),
                    e.getLocation().getName(),
                    e.getName(),
                    e.getDescription(),
                    e.getDate(),
                    eventRepository.checkEvent(doctor.getHumanId(), e.getEventId())
            ));
        }

        return ResponseEntity.ok(customEventResponseDtoList);

    }

    @GetMapping("/get/properties")
    public ResponseEntity<?> getProperties() {

        Human doctor = getDoctorFromUser();

        List<Property> properties = propertyRepository.findAll();

        List<OpinionResponseDto> opinionResponseDtoList = new ArrayList<>();

        for (Property p : properties) {
            opinionResponseDtoList.add(new OpinionResponseDto(
                    p.getPropertyId(),
                    p.getName(),
                    p.getDescription(),
                    propertyRepository.checkProperty(doctor.getHumanId(), p.getPropertyId())
            ));
        }

        return ResponseEntity.ok(opinionResponseDtoList);

    }

    @GetMapping("/get/doctor")
    public ResponseEntity<?> getDoctor() {

        User user = userRepository.findByUsername(getCurrentUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Error: User not found"));

        if (user.getHuman() == null) throw new ResourceNotFoundException("Error: Doctor not found");

        return ResponseEntity.ok(getHumanResponseDto(user.getHuman()));

    }

    @GetMapping("/get/doctor/id")
    public ResponseEntity<?> getDoctorId() {

        return ResponseEntity.ok(userRepository.getHumanIdByUsername(getCurrentUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Error: Doctor or User not found")));

    }

    @GetMapping("/get/page")
    public ResponseEntity<?> getPage(@RequestParam Integer number) {

        List<Human> humans = new ArrayList<>(humanRepository.findHumansByOrderByHumanId(PageRequest.of(number,SIZE)));

        if (humans.isEmpty()) throw new ResourceNotFoundException("Error: Humans not found");

        List<HumanResponseDto> humanResponseDtoList = getHumanResponseDtoList(humans);

        return ResponseEntity.ok(humanResponseDtoList);

    }

    @GetMapping("/get/number/human")
    public ResponseEntity<?> getNumberOfValues() {

        return ResponseEntity.ok(humanRepository.count());

    }

    @GetMapping("/get/number/page")
    public ResponseEntity<?> getNumberOfPages() {

        return ResponseEntity.ok((long) Math.ceil((float) humanRepository.count() / SIZE) - 1);

    }

    @GetMapping("/get/enum/sex")
    public ResponseEntity<?> getEnumOfSex() {

        return ResponseEntity.ok(Sex.values());

    }

    @GetMapping("/get/enum/temperament")
    public ResponseEntity<?> getEnumOfTemperament() {

        return ResponseEntity.ok(Temperament.values());

    }

    @GetMapping("/get/enum/status")
    public ResponseEntity<?> getEnumOfStatus() {

        return ResponseEntity.ok(Status.values());

    }

    @GetMapping("/get/enum/typeOfRelations")
    public ResponseEntity<?> getEnumOfTypeOfRelations() {

        return ResponseEntity.ok(TypeOfRelations.values());

    }

    @GetMapping("/get/enum/typeOfWish")
    public ResponseEntity<?> getEnumOfTypeOfWish() {

        return ResponseEntity.ok(TypeOfWish.values());

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
