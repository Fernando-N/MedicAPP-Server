package cl.medicapp.service;

import cl.medicapp.service.configuration.SecurityPropertiesLoaderFactory;
import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.document.FeedbackDocument;
import cl.medicapp.service.document.MessageDocument;
import cl.medicapp.service.document.NationalityDocument;
import cl.medicapp.service.document.ParamedicDetailsDocument;
import cl.medicapp.service.document.RegionDocument;
import cl.medicapp.service.document.ReportDocument;
import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.document.UserDetailsDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.repository.chat.ChatRepository;
import cl.medicapp.service.repository.commune.CommuneRepository;
import cl.medicapp.service.repository.feedback.FeedbackRepository;
import cl.medicapp.service.repository.nationality.NationalityRepository;
import cl.medicapp.service.repository.paramedicdetails.ParamedicDetailsDocumentRepository;
import cl.medicapp.service.repository.region.RegionRepository;
import cl.medicapp.service.repository.report.ReportRepository;
import cl.medicapp.service.repository.role.RoleRepository;
import cl.medicapp.service.repository.user.UserDocumentRepository;
import cl.medicapp.service.repository.userdetails.UserDetailsDocumentRepository;
import cl.medicapp.service.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableAsync
@PropertySource(value = "classpath:/security.yml", factory = SecurityPropertiesLoaderFactory.class)
@Slf4j
public class MedicAppApplication implements CommandLineRunner {

    public static void main(String[] args) {
        log.info("Spring boot application running in UTC timezone :"+new Date());
        SpringApplication.run(MedicAppApplication.class, args);
    }

    @Autowired
    private UserDetailsDocumentRepository userDetailsRepository;

    @Autowired
    private UserDocumentRepository userDocumentRepository;

    @Autowired
    private ParamedicDetailsDocumentRepository paramedicDetailsDocumentRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CommuneRepository communeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private NationalityRepository nationalityRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public void run(String... args) throws Exception {
//        generateNationalities();
//        generateRegions();
//        generateCommunes();
//        generateRoles();
//        generateUsers();
//        generateMessages();
//        generateReport();
//        generateFeedback();
    }

    public void generateFeedback() {
        UserDocument userFrom = userDocumentRepository.findByEmailIgnoreCase("user@test.com").get();

        UserDocument userTo = userDocumentRepository.findByEmailIgnoreCase("paramedic@test.com").get();

        FeedbackDocument feedback = FeedbackDocument.builder()
                .from(userFrom)
                .to(userTo)
                .anon(false)
                .rate(1)
                .comment("Paramedico no recomendado, no sabe na.")
                .date(DateUtil.from(new Date()))
                .build();

        feedbackRepository.save(feedback);
    }
}
