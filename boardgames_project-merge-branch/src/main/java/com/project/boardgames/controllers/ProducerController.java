package com.project.boardgames.controllers;

import com.project.boardgames.ErrorUtilities.AppException;
import com.project.boardgames.entities.Producer;
import com.project.boardgames.services.GenericServiceImpl;
import com.project.boardgames.utilities.RequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1")
@RestController
public class ProducerController {
        @Autowired
        private GenericServiceImpl<Producer> producerService;

        @GetMapping
        public ResponseEntity<List<Producer>> getProducers() {
            List<Producer> producers = producerService.returnAllEntitiesById();
            return ResponseEntity.ok(producers);
        }

    @PostMapping("/addProducer")
    public ResponseEntity<RequestResponse<Producer>> addProducer(@Valid @RequestBody Producer newProducer, BindingResult bindingResult) {
        // Validate input data
        CheckValues(bindingResult);
        // Save the new producer
        Producer createdProducer = producerService.save(newProducer);
        RequestResponse<Producer> response = new RequestResponse<>(true, createdProducer, "Producer added successfully");
        return ResponseEntity.ok(response);
    }

    public static void CheckValues(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Retrieve the validation errors
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            // Throw your custom exception with the validation errors
            throw new AppException("Validation errors: " + errorMessages, HttpStatus.BAD_REQUEST.value(), "fail", true);
        }
    }

    @GetMapping("/producer/{id}")
        public ResponseEntity<Producer> getProducerById(@PathVariable("id") Long id) {
            Optional<Producer> producer = producerService.returnEntityById(id);
            return ResponseEntity.notFound().build();
        }

    @PutMapping("/editProducer/{id}")
    public ResponseEntity<RequestResponse<Producer>> updateProducer(@PathVariable("id") Long id, @Valid @RequestBody Producer updatedProducer) {
        System.out.println(id);
        Optional<Producer> existingProducer = producerService.returnEntityById(id);
        if (existingProducer.isPresent()) {
            Producer producer = existingProducer.get();
            producer.setName(updatedProducer.getName());
            producerService.updateEntity(producer);
            return ResponseEntity.ok(new RequestResponse<>(true, producer, "Producer was updated"));
        }
        return ResponseEntity.notFound().build();
    }

    //
        @DeleteMapping("/deleteProducer/{id}")
        public ResponseEntity<RequestResponse<String>> deleteProducer(@PathVariable("id") Long id) {
            Optional<Producer> producer = producerService.returnEntityById(id);
            if (producer.isPresent()) {
                producerService.deleteEntity(id);
                RequestResponse response = new RequestResponse(true, "Deletion completed", "Ok");
                return ResponseEntity.ok(response);
            }
            throw new AppException("Producer not found", HttpStatus.NOT_FOUND.value(), "fail", true);
        }
    }


