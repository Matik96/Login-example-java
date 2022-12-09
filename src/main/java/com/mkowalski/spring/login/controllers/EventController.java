package com.mkowalski.spring.login.controllers;

import com.mkowalski.spring.login.models.Event;
import com.mkowalski.spring.login.payload.request.EventRequest;
import com.mkowalski.spring.login.payload.response.MessageResponse;
import com.mkowalski.spring.login.repository.EventRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class EventController {


    @Autowired
    EventRepository eventRepository;

    @GetMapping("/events")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<? extends Object> getAllEvents(@RequestHeader HttpHeaders headers) {
        System.out.println("asdasd" + headers);
        List allEvents = eventRepository.findAll();

        if (!allEvents.isEmpty()) {
            return new ResponseEntity<List<JSONObject>>(allEvents, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/events/{sort}/{filtr}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<? extends Object> getAllEventsSorted(@RequestHeader HttpHeaders headers, @PathVariable("sort") long sort, @PathVariable("filtr") String filtr) {
        System.out.println("asdasd" + headers);
        List allEvents = null;
        if(filtr.equals("all")){
            allEvents = eventRepository.findAll();
        }else{
            allEvents = eventRepository.findByType(filtr);
        }

        if(sort == 0){
        Collections.sort(allEvents, new Comparator<Event>() {
            public int compare(Event o1, Event o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });}
        else if (sort == 1){
            Collections.sort(allEvents, new Comparator<Event>() {
                public int compare(Event o1, Event o2) {
                    return o2.getDate().compareTo(o1.getDate());
                }
            });
        }
         if (!allEvents.isEmpty()) {
            return new ResponseEntity<List<JSONObject>>(allEvents, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/event/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<? extends Object> getEventById(@PathVariable("id") long id) {

        Optional<Event> event = eventRepository.findById(id);

        if (event.isPresent()) {
            return new ResponseEntity<>(event.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/events")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<? extends Object> createEvent(@RequestBody EventRequest eventRequest) {

        if (eventRequest == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: There is no data in request!"));
        } else if (eventRepository.existsByName(eventRequest.getName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Event is already exists!"));
        }

        Event event = new Event(eventRequest.getName(), eventRequest.getDate(), eventRequest.getDateEnd(), eventRequest.getShortDescription(), eventRequest.getLongDescription(), eventRequest.getPictureUrl(), eventRequest.getType());

        eventRepository.save(event);

        return ResponseEntity.ok(new MessageResponse("Event created successfully!"));
    }

    @PutMapping("/events/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<? extends Object> updateEventById(@PathVariable("id") long id, @RequestBody EventRequest eventRequest) {

        Optional<Event> event = eventRepository.findById(id);

        if (event.isPresent()) {
            Event _event = event.get();
            _event.setName(eventRequest.getName());
            _event.setDate(eventRequest.getDate());
            _event.setDateEnd(eventRequest.getDateEnd());
            _event.setShortDescription(eventRequest.getShortDescription());
            _event.setLongDescription(eventRequest.getLongDescription());
            _event.setPictureUrl(eventRequest.getPictureUrl());
            _event.setType(eventRequest.getType());

            return new ResponseEntity<>(eventRepository.save(_event), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/events/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<? extends Object> deleteEventById(@PathVariable("id") long id) {

        try {
            eventRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
