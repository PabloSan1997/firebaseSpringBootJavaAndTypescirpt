package com.ejemplo.springboor.firebase.springbootfirebase.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;


import java.io.FileInputStream;
import java.io.IOException;
import static  com.ejemplo.springboor.firebase.springbootfirebase.utils.MainConstants.*;

@Configuration
public class FireBaseCofig {
    @PostConstruct
    public void init() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("src/main/resources/ejemploimagenes-key.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket(BUCKET)
                .build();

        FirebaseApp.initializeApp(options);

    }
}
