package com.ejemplo.springboor.firebase.springbootfirebase.controller;

import com.ejemplo.springboor.firebase.springbootfirebase.services.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/image")
public class AppController {

    @Autowired
    private FirebaseService firebaseService;

    @GetMapping
    public ResponseEntity<?> findImage(@RequestParam("fileName") String name){
        return ResponseEntity.ok(firebaseService.getFile(name));
    }

    @PostMapping
    public ResponseEntity<?> createImage(@RequestParam("file") MultipartFile file){
        return ResponseEntity.status(201).body(firebaseService.createImage(file));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteImage(@PathVariable String name){
        firebaseService.deleteImage(name);
        return ResponseEntity.noContent().build();
    }
}
