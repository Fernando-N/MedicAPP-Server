package cl.medicapp.service.services.firebase;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class FirebaseInitialize {

    @PostConstruct
    public void initialize() {
//        try(FileInputStream serviceAccount = new FileInputStream("serviceaccount.json")) {
//
//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                    .setDatabaseUrl("https://medicapp-5b6fb.firebaseio.com")
//                    .build();
//
//            FirebaseApp.initializeApp(options);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
