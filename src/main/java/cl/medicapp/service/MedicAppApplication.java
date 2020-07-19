package cl.medicapp.service;

import cl.medicapp.service.configuration.SecurityPropertiesLoaderFactory;
import cl.medicapp.service.document.*;
import cl.medicapp.service.repository.chat.ChatRepository;
import cl.medicapp.service.repository.commune.CommuneRepository;
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

    @Override
    public void run(String... args) throws Exception {
//        generateNationalities();
//        generateRegions();
//        generateCommunes();
//        generateRoles();
//        generateUsers();
//        generateMessages();
//        generateReport();
    }

    public void generateReport() {
        UserDocument userFrom = userDocumentRepository.findByEmailIgnoreCase("user@test.com").get();

        UserDocument userTo = userDocumentRepository.findByEmailIgnoreCase("paramedic@test.com").get();

        ReportDocument report1 = ReportDocument.builder()
                .from(userFrom)
                .to(userTo)
                .alreadyRead(false)
                .date(DateUtil.from(new Date()))
                .message("REPORTE POR X MOTIVO")
                .build();

        reportRepository.save(report1);
    }

    public void generateMessages() {
        UserDocument user = userDocumentRepository.findByEmailIgnoreCase("user@test.com").get();
        UserDocument admin = userDocumentRepository.findByEmailIgnoreCase("admin@test.com").get();
        UserDocument paramedic = userDocumentRepository.findByEmailIgnoreCase("paramedic@test.com").get();


        MessageDocument msg1 = MessageDocument.builder()
                //.id()
                .date(DateUtil.from(new Date()))
                .from(user)
                .to(admin)
                .message("Hola admin soy user")
                .alreadyRead(false)
                .build();

        MessageDocument msg2 = MessageDocument.builder()
                //.id()
                .date(DateUtil.from(new Date()))
                .from(paramedic)
                .to(admin)
                .message("Hola admin soy paramedic")
                .alreadyRead(false)
                .build();

        MessageDocument msg3 = MessageDocument.builder()
                //.id()
                .date(DateUtil.from(new Date()))
                .from(paramedic)
                .to(user)
                .message("Hola user soy paramedic")
                .alreadyRead(false)
                .build();

        MessageDocument msg4 = MessageDocument.builder()
                //.id()
                .date(DateUtil.from(new Date()))
                .from(admin)
                .to(user)
                .message("Hola user soy admin")
                .alreadyRead(false)
                .build();

        MessageDocument msg5 = MessageDocument.builder()
                //.id()
                .date(DateUtil.from(new Date()))
                .from(user)
                .to(paramedic)
                .message("Hola paramedic soy user")
                .alreadyRead(false)
                .build();

        MessageDocument msg6 = MessageDocument.builder()
                //.id()
                .date(DateUtil.from(new Date()))
                .from(admin)
                .to(paramedic)
                .message("Hola paramedic soy admin")
                .alreadyRead(false)
                .build();

        chatRepository.saveAll(Arrays.asList(msg1, msg2, msg3, msg4, msg5, msg6));


    }

    public void generateUsers() {

        RoleDocument admin = roleRepository.findByNameIgnoreCaseEndsWith("ADMIN").get();
        RoleDocument user = roleRepository.findByNameIgnoreCaseEndsWith("USER").get();
        RoleDocument paramedic = roleRepository.findByNameIgnoreCaseEndsWith("PARAMEDIC").get();

        CommuneDocument temuco = communeRepository.findByNameIgnoreCase("temuco").get();
        CommuneDocument santiago = communeRepository.findByNameIgnoreCase("santiago").get();
        CommuneDocument antofagasta = communeRepository.findByNameIgnoreCase("antofagasta").get();

        UserDetailsDocument adminDetails = userDetailsRepository.save(
                UserDetailsDocument.builder()
                        .rut("11111111-1")
                        .firstName("ADMIN")
                        .lastName("TESTADMIN")
                        .address("Temuco #123")
                        .profileImageURI("https://firebasestorage.googleapis.com/v0/b/medicapp-5b6fb.appspot.com/o/HomeroLoco.jpg?alt=media&token=8d276f91-76b8-41e2-8f77-d6777a42c6f6")
                        .birthDay(DateUtil.from(15, 10, 1999))
                        .commune(temuco)
                        .build()
        );

        UserDetailsDocument userDetails = userDetailsRepository.save(
                UserDetailsDocument.builder()
                        .rut("11111111-1")
                        .firstName("USER")
                        .lastName("TESTUSER")
                        .address("Santiago #123")
                        .profileImageURI("https://firebasestorage.googleapis.com/v0/b/medicapp-5b6fb.appspot.com/o/Tabla.jpg?alt=media&token=3a98f266-7a30-4b35-a794-050c1928cbe5")
                        .birthDay(DateUtil.from(1, 1, 1973))
                        .commune(santiago)
                        .build()
        );

        UserDetailsDocument paramedicDetails = userDetailsRepository.save(
                UserDetailsDocument.builder()
                        .rut("22222222-2")
                        .firstName("PARAMEDIC")
                        .lastName("TESTPARAMEDIC")
                        .address("Bulnes #321")
                        .profileImageURI("https://firebasestorage.googleapis.com/v0/b/medicapp-5b6fb.appspot.com/o/Frink.jpg?alt=media&token=173281fa-fb61-48f9-9461-64f539709dd9")
                        .birthDay(DateUtil.from(5, 4, 1960))
                        .commune(antofagasta)
                        .build()
        );

        ParamedicDetailsDocument paramedicDetailsDocument = paramedicDetailsDocumentRepository.save(
                ParamedicDetailsDocument.builder()
                        .titleImageURI("https://firebasestorage.googleapis.com/v0/b/medicapp-5b6fb.appspot.com/o/tituloVivianaParedesSalazar.jpg?alt=media&token=8094ec11-0128-40b3-bd42-8d10ef724271")
                        .graduationYear(2005)
                        .certificateNationalHealthURI("https://firebasestorage.googleapis.com/v0/b/medicapp-5b6fb.appspot.com/o/CertificadoPrestadorDeSAlud.png?alt=media&token=0cb97d9a-0769-495d-b2b1-70fb91b2e6ed")
                        .carnetImageURI("https://firebasestorage.googleapis.com/v0/b/medicapp-5b6fb.appspot.com/o/Carnet.jpg?alt=media&token=337cce46-4863-4679-9d4f-d9e618aa80e9")
                        .build()
        );

        List<UserDocument> users = Arrays.asList(
                UserDocument.builder()
                        .email("admin@test.com")
                        .password(passwordEncoder.encode("test"))
                        .attempts(0)
                        .createdOn(new Date())
                        .enabled(true)
                        .roleEntities(Collections.singletonList(admin))
                        .userDetails(adminDetails)
                        .build(),

                UserDocument.builder()
                        .email("user@test.com")
                        .password(passwordEncoder.encode("test"))
                        .attempts(0)
                        .createdOn(new Date())
                        .enabled(true)
                        .roleEntities(Collections.singletonList(user))
                        .userDetails(userDetails)
                        .build(),

                UserDocument.builder()
                        .email("paramedic@test.com")
                        .password(passwordEncoder.encode("test"))
                        .attempts(0)
                        .createdOn(new Date())
                        .enabled(true)
                        .roleEntities(Collections.singletonList(paramedic))
                        .userDetails(paramedicDetails)
                        .paramedicDetails(paramedicDetailsDocument)
                        .build()
        );

        userDocumentRepository.saveAll(users);
    }

    public void generateRegions() {
        List<RegionDocument> regions = Arrays.asList(
                RegionDocument.builder().name("Arica y Parinacota").ordinal("XV").build(),
                RegionDocument.builder().name("Tarapacá").ordinal("I").build(),
                RegionDocument.builder().name("Antofagasta").ordinal("II").build(),
                RegionDocument.builder().name("Atacama").ordinal("III").build(),
                RegionDocument.builder().name("Coquimbo").ordinal("IV").build(),
                RegionDocument.builder().name("Valparaiso").ordinal("V").build(),
                RegionDocument.builder().name("Metropolitana de Santiago").ordinal("RM").build(),
                RegionDocument.builder().name("Libertador General Bernardo O'Higgins").ordinal("VI").build(),
                RegionDocument.builder().name("Maule").ordinal("VII").build(),
                RegionDocument.builder().name("Biobío").ordinal("VIII").build(),
                RegionDocument.builder().name("La Araucanía").ordinal("IX").build(),
                RegionDocument.builder().name("Los Ríos").ordinal("XIV").build(),
                RegionDocument.builder().name("Los Lagos").ordinal("X").build(),
                RegionDocument.builder().name("Aisén del General Carlos Ibáñez del Campo").ordinal("XI").build(),
                RegionDocument.builder().name("Magallanes y de la Antártica Chilena").ordinal("XII").build()
        );

        regionRepository.saveAll(regions);
    }

    public void generateCommunes() {

        RegionDocument XV   = regionRepository.findByNameIgnoreCase("Arica y Parinacota").get();
        RegionDocument I    = regionRepository.findByNameIgnoreCase("Tarapacá").get();
        RegionDocument II   = regionRepository.findByNameIgnoreCase("Antofagasta").get();
        RegionDocument III  = regionRepository.findByNameIgnoreCase("Atacama").get();
        RegionDocument IV   = regionRepository.findByNameIgnoreCase("Coquimbo").get();
        RegionDocument V    = regionRepository.findByNameIgnoreCase("Valparaiso").get();
        RegionDocument RM   = regionRepository.findByNameIgnoreCase("Metropolitana de Santiago").get();
        RegionDocument VI   = regionRepository.findByNameIgnoreCase("Libertador General Bernardo O'Higgins").get();
        RegionDocument VII  = regionRepository.findByNameIgnoreCase("Maule").get();
        RegionDocument VIII = regionRepository.findByNameIgnoreCase("Biobío").get();
        RegionDocument IX   = regionRepository.findByNameIgnoreCase("La Araucanía").get();
        RegionDocument XIV  = regionRepository.findByNameIgnoreCase("Los Ríos").get();
        RegionDocument X    = regionRepository.findByNameIgnoreCase("Los Lagos").get();
        RegionDocument XI   = regionRepository.findByNameIgnoreCase("Aisén del General Carlos Ibáñez del Campo").get();
        RegionDocument XII  = regionRepository.findByNameIgnoreCase("Magallanes y de la Antártica Chilena").get();

        List<CommuneDocument> communes = Arrays.asList(
                CommuneDocument.builder().name("Arica").region(XV).build(),
                CommuneDocument.builder().name("Camarones").region(XV).build(),
                CommuneDocument.builder().name("General Lagos").region(XV).build(),
                CommuneDocument.builder().name("Putre").region(XV).build(),
                CommuneDocument.builder().name("Alto Hospicio").region(I).build(),
                CommuneDocument.builder().name("Iquique").region(I).build(),
                CommuneDocument.builder().name("Camiña").region(I).build(),
                CommuneDocument.builder().name("Colchane").region(I).build(),
                CommuneDocument.builder().name("Huara").region(I).build(),
                CommuneDocument.builder().name("Pica").region(I).build(),
                CommuneDocument.builder().name("Pozo Almonte").region(I).build(),
                CommuneDocument.builder().name("Antofagasta").region(II).build(),
                CommuneDocument.builder().name("Mejillones").region(II).build(),
                CommuneDocument.builder().name("Sierra Gorda").region(II).build(),
                CommuneDocument.builder().name("Taltal").region(II).build(),
                CommuneDocument.builder().name("Calama").region(II).build(),
                CommuneDocument.builder().name("Ollague").region(II).build(),
                CommuneDocument.builder().name("San Pedro de Atacama").region(II).build(),
                CommuneDocument.builder().name("María Elena").region(II).build(),
                CommuneDocument.builder().name("Tocopilla").region(II).build(),
                CommuneDocument.builder().name("Chañaral").region(III).build(),
                CommuneDocument.builder().name("Diego de Almagro").region(III).build(),
                CommuneDocument.builder().name("Caldera").region(III).build(),
                CommuneDocument.builder().name("Copiapó").region(III).build(),
                CommuneDocument.builder().name("Tierra Amarilla").region(III).build(),
                CommuneDocument.builder().name("Alto del Carmen").region(III).build(),
                CommuneDocument.builder().name("Freirina").region(III).build(),
                CommuneDocument.builder().name("Huasco").region(III).build(),
                CommuneDocument.builder().name("Vallenar").region(III).build(),
                CommuneDocument.builder().name("Canela").region(IV).build(),
                CommuneDocument.builder().name("Illapel").region(IV).build(),
                CommuneDocument.builder().name("Los Vilos").region(IV).build(),
                CommuneDocument.builder().name("Salamanca").region(IV).build(),
                CommuneDocument.builder().name("Andacollo").region(IV).build(),
                CommuneDocument.builder().name("Coquimbo").region(IV).build(),
                CommuneDocument.builder().name("La Higuera").region(IV).build(),
                CommuneDocument.builder().name("La Serena").region(IV).build(),
                CommuneDocument.builder().name("Paihuaco").region(IV).build(),
                CommuneDocument.builder().name("Vicuña").region(IV).build(),
                CommuneDocument.builder().name("Combarbalá").region(IV).build(),
                CommuneDocument.builder().name("Monte Patria").region(IV).build(),
                CommuneDocument.builder().name("Ovalle").region(IV).build(),
                CommuneDocument.builder().name("Punitaqui").region(IV).build(),
                CommuneDocument.builder().name("Río Hurtado").region(IV).build(),
                CommuneDocument.builder().name("Isla de Pascua").region(V).build(),
                CommuneDocument.builder().name("Calle Larga").region(V).build(),
                CommuneDocument.builder().name("Los Andes").region(V).build(),
                CommuneDocument.builder().name("Rinconada").region(V).build(),
                CommuneDocument.builder().name("San Esteban").region(V).build(),
                CommuneDocument.builder().name("La Ligua").region(V).build(),
                CommuneDocument.builder().name("Papudo").region(V).build(),
                CommuneDocument.builder().name("Petorca").region(V).build(),
                CommuneDocument.builder().name("Zapallar").region(V).build(),
                CommuneDocument.builder().name("Hijuelas").region(V).build(),
                CommuneDocument.builder().name("La Calera").region(V).build(),
                CommuneDocument.builder().name("La Cruz").region(V).build(),
                CommuneDocument.builder().name("Limache").region(V).build(),
                CommuneDocument.builder().name("Nogales").region(V).build(),
                CommuneDocument.builder().name("Olmué").region(V).build(),
                CommuneDocument.builder().name("Quillota").region(V).build(),
                CommuneDocument.builder().name("Algarrobo").region(V).build(),
                CommuneDocument.builder().name("Cartagena").region(V).build(),
                CommuneDocument.builder().name("El Quisco").region(V).build(),
                CommuneDocument.builder().name("El Tabo").region(V).build(),
                CommuneDocument.builder().name("San Antonio").region(V).build(),
                CommuneDocument.builder().name("Santo Domingo").region(V).build(),
                CommuneDocument.builder().name("Catemu").region(V).build(),
                CommuneDocument.builder().name("Llaillay").region(V).build(),
                CommuneDocument.builder().name("Panquehue").region(V).build(),
                CommuneDocument.builder().name("Putaendo").region(V).build(),
                CommuneDocument.builder().name("San Felipe").region(V).build(),
                CommuneDocument.builder().name("Santa María").region(V).build(),
                CommuneDocument.builder().name("Casablanca").region(V).build(),
                CommuneDocument.builder().name("Concón").region(V).build(),
                CommuneDocument.builder().name("Juan Fernández").region(V).build(),
                CommuneDocument.builder().name("Puchuncaví").region(V).build(),
                CommuneDocument.builder().name("Quilpué").region(V).build(),
                CommuneDocument.builder().name("Quintero").region(V).build(),
                CommuneDocument.builder().name("Valparaíso").region(V).build(),
                CommuneDocument.builder().name("Villa Alemana").region(V).build(),
                CommuneDocument.builder().name("Viña del Mar").region(V).build(),
                CommuneDocument.builder().name("Colina").region(RM).build(),
                CommuneDocument.builder().name("Lampa").region(RM).build(),
                CommuneDocument.builder().name("Tiltil").region(RM).build(),
                CommuneDocument.builder().name("Pirque").region(RM).build(),
                CommuneDocument.builder().name("Puente Alto").region(RM).build(),
                CommuneDocument.builder().name("San José de Maipo").region(RM).build(),
                CommuneDocument.builder().name("Buin").region(RM).build(),
                CommuneDocument.builder().name("Calera de Tango").region(RM).build(),
                CommuneDocument.builder().name("Paine").region(RM).build(),
                CommuneDocument.builder().name("San Bernardo").region(RM).build(),
                CommuneDocument.builder().name("Alhué").region(RM).build(),
                CommuneDocument.builder().name("Curacaví").region(RM).build(),
                CommuneDocument.builder().name("María Pinto").region(RM).build(),
                CommuneDocument.builder().name("Melipilla").region(RM).build(),
                CommuneDocument.builder().name("San Pedro").region(RM).build(),
                CommuneDocument.builder().name("Cerrillos").region(RM).build(),
                CommuneDocument.builder().name("Cerro Navia").region(RM).build(),
                CommuneDocument.builder().name("Conchalí").region(RM).build(),
                CommuneDocument.builder().name("El Bosque").region(RM).build(),
                CommuneDocument.builder().name("Estación Central").region(RM).build(),
                CommuneDocument.builder().name("Huechuraba").region(RM).build(),
                CommuneDocument.builder().name("Independencia").region(RM).build(),
                CommuneDocument.builder().name("La Cisterna").region(RM).build(),
                CommuneDocument.builder().name("La Granja").region(RM).build(),
                CommuneDocument.builder().name("La Florida").region(RM).build(),
                CommuneDocument.builder().name("La Pintana").region(RM).build(),
                CommuneDocument.builder().name("La Reina").region(RM).build(),
                CommuneDocument.builder().name("Las Condes").region(RM).build(),
                CommuneDocument.builder().name("Lo Barnechea").region(RM).build(),
                CommuneDocument.builder().name("Lo Espejo").region(RM).build(),
                CommuneDocument.builder().name("Lo Prado").region(RM).build(),
                CommuneDocument.builder().name("Macul").region(RM).build(),
                CommuneDocument.builder().name("Maipú").region(RM).build(),
                CommuneDocument.builder().name("Ñuñoa").region(RM).build(),
                CommuneDocument.builder().name("Pedro Aguirre Cerda").region(RM).build(),
                CommuneDocument.builder().name("Peñalolén").region(RM).build(),
                CommuneDocument.builder().name("Providencia").region(RM).build(),
                CommuneDocument.builder().name("Pudahuel").region(RM).build(),
                CommuneDocument.builder().name("Quilicura").region(RM).build(),
                CommuneDocument.builder().name("Quinta Normal").region(RM).build(),
                CommuneDocument.builder().name("Recoleta").region(RM).build(),
                CommuneDocument.builder().name("Renca").region(RM).build(),
                CommuneDocument.builder().name("San Miguel").region(RM).build(),
                CommuneDocument.builder().name("San Joaquín").region(RM).build(),
                CommuneDocument.builder().name("San Ramón").region(RM).build(),
                CommuneDocument.builder().name("Santiago").region(RM).build(),
                CommuneDocument.builder().name("Vitacura").region(RM).build(),
                CommuneDocument.builder().name("El Monte").region(RM).build(),
                CommuneDocument.builder().name("Isla de Maipo").region(RM).build(),
                CommuneDocument.builder().name("Padre Hurtado").region(RM).build(),
                CommuneDocument.builder().name("Peñaflor").region(RM).build(),
                CommuneDocument.builder().name("Talagante").region(RM).build(),
                CommuneDocument.builder().name("Codegua").region(VI).build(),
                CommuneDocument.builder().name("Coínco").region(VI).build(),
                CommuneDocument.builder().name("Coltauco").region(VI).build(),
                CommuneDocument.builder().name("Doñihue").region(VI).build(),
                CommuneDocument.builder().name("Graneros").region(VI).build(),
                CommuneDocument.builder().name("Las Cabras").region(VI).build(),
                CommuneDocument.builder().name("Machalí").region(VI).build(),
                CommuneDocument.builder().name("Malloa").region(VI).build(),
                CommuneDocument.builder().name("Mostazal").region(VI).build(),
                CommuneDocument.builder().name("Olivar").region(VI).build(),
                CommuneDocument.builder().name("Peumo").region(VI).build(),
                CommuneDocument.builder().name("Pichidegua").region(VI).build(),
                CommuneDocument.builder().name("Quinta de Tilcoco").region(VI).build(),
                CommuneDocument.builder().name("Rancagua").region(VI).build(),
                CommuneDocument.builder().name("Rengo").region(VI).build(),
                CommuneDocument.builder().name("Requínoa").region(VI).build(),
                CommuneDocument.builder().name("San Vicente de Tagua Tagua").region(VI).build(),
                CommuneDocument.builder().name("La Estrella").region(VI).build(),
                CommuneDocument.builder().name("Litueche").region(VI).build(),
                CommuneDocument.builder().name("Marchihue").region(VI).build(),
                CommuneDocument.builder().name("Navidad").region(VI).build(),
                CommuneDocument.builder().name("Peredones").region(VI).build(),
                CommuneDocument.builder().name("Pichilemu").region(VI).build(),
                CommuneDocument.builder().name("Chépica").region(VI).build(),
                CommuneDocument.builder().name("Chimbarongo").region(VI).build(),
                CommuneDocument.builder().name("Lolol").region(VI).build(),
                CommuneDocument.builder().name("Nancagua").region(VI).build(),
                CommuneDocument.builder().name("Palmilla").region(VI).build(),
                CommuneDocument.builder().name("Peralillo").region(VI).build(),
                CommuneDocument.builder().name("Placilla").region(VI).build(),
                CommuneDocument.builder().name("Pumanque").region(VI).build(),
                CommuneDocument.builder().name("San Fernando").region(VI).build(),
                CommuneDocument.builder().name("Santa Cruz").region(VI).build(),
                CommuneDocument.builder().name("Cauquenes").region(VII).build(),
                CommuneDocument.builder().name("Chanco").region(VII).build(),
                CommuneDocument.builder().name("Pelluhue").region(VII).build(),
                CommuneDocument.builder().name("Curicó").region(VII).build(),
                CommuneDocument.builder().name("Hualañé").region(VII).build(),
                CommuneDocument.builder().name("Licantén").region(VII).build(),
                CommuneDocument.builder().name("Molina").region(VII).build(),
                CommuneDocument.builder().name("Rauco").region(VII).build(),
                CommuneDocument.builder().name("Romeral").region(VII).build(),
                CommuneDocument.builder().name("Sagrada Familia").region(VII).build(),
                CommuneDocument.builder().name("Teno").region(VII).build(),
                CommuneDocument.builder().name("Vichuquén").region(VII).build(),
                CommuneDocument.builder().name("Colbún").region(VII).build(),
                CommuneDocument.builder().name("Linares").region(VII).build(),
                CommuneDocument.builder().name("Longaví").region(VII).build(),
                CommuneDocument.builder().name("Parral").region(VII).build(),
                CommuneDocument.builder().name("Retiro").region(VII).build(),
                CommuneDocument.builder().name("San Javier").region(VII).build(),
                CommuneDocument.builder().name("Villa Alegre").region(VII).build(),
                CommuneDocument.builder().name("Yerbas Buenas").region(VII).build(),
                CommuneDocument.builder().name("Constitución").region(VII).build(),
                CommuneDocument.builder().name("Curepto").region(VII).build(),
                CommuneDocument.builder().name("Empedrado").region(VII).build(),
                CommuneDocument.builder().name("Maule").region(VII).build(),
                CommuneDocument.builder().name("Pelarco").region(VII).build(),
                CommuneDocument.builder().name("Pencahue").region(VII).build(),
                CommuneDocument.builder().name("Río Claro").region(VII).build(),
                CommuneDocument.builder().name("San Clemente").region(VII).build(),
                CommuneDocument.builder().name("San Rafael").region(VII).build(),
                CommuneDocument.builder().name("Talca").region(VII).build(),
                CommuneDocument.builder().name("Arauco").region(VIII).build(),
                CommuneDocument.builder().name("Cañete").region(VIII).build(),
                CommuneDocument.builder().name("Contulmo").region(VIII).build(),
                CommuneDocument.builder().name("Curanilahue").region(VIII).build(),
                CommuneDocument.builder().name("Lebu").region(VIII).build(),
                CommuneDocument.builder().name("Los Álamos").region(VIII).build(),
                CommuneDocument.builder().name("Tirúa").region(VIII).build(),
                CommuneDocument.builder().name("Alto Biobío").region(VIII).build(),
                CommuneDocument.builder().name("Antuco").region(VIII).build(),
                CommuneDocument.builder().name("Cabrero").region(VIII).build(),
                CommuneDocument.builder().name("Laja").region(VIII).build(),
                CommuneDocument.builder().name("Los Ángeles").region(VIII).build(),
                CommuneDocument.builder().name("Mulchén").region(VIII).build(),
                CommuneDocument.builder().name("Nacimiento").region(VIII).build(),
                CommuneDocument.builder().name("Negrete").region(VIII).build(),
                CommuneDocument.builder().name("Quilaco").region(VIII).build(),
                CommuneDocument.builder().name("Quilleco").region(VIII).build(),
                CommuneDocument.builder().name("San Rosendo").region(VIII).build(),
                CommuneDocument.builder().name("Santa Bárbara").region(VIII).build(),
                CommuneDocument.builder().name("Tucapel").region(VIII).build(),
                CommuneDocument.builder().name("Yumbel").region(VIII).build(),
                CommuneDocument.builder().name("Chiguayante").region(VIII).build(),
                CommuneDocument.builder().name("Concepción").region(VIII).build(),
                CommuneDocument.builder().name("Coronel").region(VIII).build(),
                CommuneDocument.builder().name("Florida").region(VIII).build(),
                CommuneDocument.builder().name("Hualpén").region(VIII).build(),
                CommuneDocument.builder().name("Hualqui").region(VIII).build(),
                CommuneDocument.builder().name("Lota").region(VIII).build(),
                CommuneDocument.builder().name("Penco").region(VIII).build(),
                CommuneDocument.builder().name("San Pedro de La Paz").region(VIII).build(),
                CommuneDocument.builder().name("Santa Juana").region(VIII).build(),
                CommuneDocument.builder().name("Talcahuano").region(VIII).build(),
                CommuneDocument.builder().name("Tomé").region(VIII).build(),
                CommuneDocument.builder().name("Bulnes").region(VIII).build(),
                CommuneDocument.builder().name("Chillán").region(VIII).build(),
                CommuneDocument.builder().name("Chillán Viejo").region(VIII).build(),
                CommuneDocument.builder().name("Cobquecura").region(VIII).build(),
                CommuneDocument.builder().name("Coelemu").region(VIII).build(),
                CommuneDocument.builder().name("Coihueco").region(VIII).build(),
                CommuneDocument.builder().name("El Carmen").region(VIII).build(),
                CommuneDocument.builder().name("Ninhue").region(VIII).build(),
                CommuneDocument.builder().name("Ñiquen").region(VIII).build(),
                CommuneDocument.builder().name("Pemuco").region(VIII).build(),
                CommuneDocument.builder().name("Pinto").region(VIII).build(),
                CommuneDocument.builder().name("Portezuelo").region(VIII).build(),
                CommuneDocument.builder().name("Quillón").region(VIII).build(),
                CommuneDocument.builder().name("Quirihue").region(VIII).build(),
                CommuneDocument.builder().name("Ránquil").region(VIII).build(),
                CommuneDocument.builder().name("San Carlos").region(VIII).build(),
                CommuneDocument.builder().name("San Fabián").region(VIII).build(),
                CommuneDocument.builder().name("San Ignacio").region(VIII).build(),
                CommuneDocument.builder().name("San Nicolás").region(VIII).build(),
                CommuneDocument.builder().name("Treguaco").region(VIII).build(),
                CommuneDocument.builder().name("Yungay").region(VIII).build(),
                CommuneDocument.builder().name("Carahue").region(IX).build(),
                CommuneDocument.builder().name("Cholchol").region(IX).build(),
                CommuneDocument.builder().name("Cunco").region(IX).build(),
                CommuneDocument.builder().name("Curarrehue").region(IX).build(),
                CommuneDocument.builder().name("Freire").region(IX).build(),
                CommuneDocument.builder().name("Galvarino").region(IX).build(),
                CommuneDocument.builder().name("Gorbea").region(IX).build(),
                CommuneDocument.builder().name("Lautaro").region(IX).build(),
                CommuneDocument.builder().name("Loncoche").region(IX).build(),
                CommuneDocument.builder().name("Melipeuco").region(IX).build(),
                CommuneDocument.builder().name("Nueva Imperial").region(IX).build(),
                CommuneDocument.builder().name("Padre Las Casas").region(IX).build(),
                CommuneDocument.builder().name("Perquenco").region(IX).build(),
                CommuneDocument.builder().name("Pitrufquén").region(IX).build(),
                CommuneDocument.builder().name("Pucón").region(IX).build(),
                CommuneDocument.builder().name("Saavedra").region(IX).build(),
                CommuneDocument.builder().name("Temuco").region(IX).build(),
                CommuneDocument.builder().name("Teodoro Schmidt").region(IX).build(),
                CommuneDocument.builder().name("Toltén").region(IX).build(),
                CommuneDocument.builder().name("Vilcún").region(IX).build(),
                CommuneDocument.builder().name("Villarrica").region(IX).build(),
                CommuneDocument.builder().name("Angol").region(IX).build(),
                CommuneDocument.builder().name("Collipulli").region(IX).build(),
                CommuneDocument.builder().name("Curacautín").region(IX).build(),
                CommuneDocument.builder().name("Ercilla").region(IX).build(),
                CommuneDocument.builder().name("Lonquimay").region(IX).build(),
                CommuneDocument.builder().name("Los Sauces").region(IX).build(),
                CommuneDocument.builder().name("Lumaco").region(IX).build(),
                CommuneDocument.builder().name("Purén").region(IX).build(),
                CommuneDocument.builder().name("Renaico").region(IX).build(),
                CommuneDocument.builder().name("Traiguén").region(IX).build(),
                CommuneDocument.builder().name("Victoria").region(IX).build(),
                CommuneDocument.builder().name("Corral").region(XIV).build(),
                CommuneDocument.builder().name("Lanco").region(XIV).build(),
                CommuneDocument.builder().name("Los Lagos").region(XIV).build(),
                CommuneDocument.builder().name("Máfil").region(XIV).build(),
                CommuneDocument.builder().name("Mariquina").region(XIV).build(),
                CommuneDocument.builder().name("Paillaco").region(XIV).build(),
                CommuneDocument.builder().name("Panguipulli").region(XIV).build(),
                CommuneDocument.builder().name("Valdivia").region(XIV).build(),
                CommuneDocument.builder().name("Futrono").region(XIV).build(),
                CommuneDocument.builder().name("La Unión").region(XIV).build(),
                CommuneDocument.builder().name("Lago Ranco").region(XIV).build(),
                CommuneDocument.builder().name("Río Bueno").region(XIV).build(),
                CommuneDocument.builder().name("Ancud").region(X).build(),
                CommuneDocument.builder().name("Castro").region(X).build(),
                CommuneDocument.builder().name("Chonchi").region(X).build(),
                CommuneDocument.builder().name("Curaco de Vélez").region(X).build(),
                CommuneDocument.builder().name("Dalcahue").region(X).build(),
                CommuneDocument.builder().name("Puqueldón").region(X).build(),
                CommuneDocument.builder().name("Queilén").region(X).build(),
                CommuneDocument.builder().name("Quemchi").region(X).build(),
                CommuneDocument.builder().name("Quellón").region(X).build(),
                CommuneDocument.builder().name("Quinchao").region(X).build(),
                CommuneDocument.builder().name("Calbuco").region(X).build(),
                CommuneDocument.builder().name("Cochamó").region(X).build(),
                CommuneDocument.builder().name("Fresia").region(X).build(),
                CommuneDocument.builder().name("Frutillar").region(X).build(),
                CommuneDocument.builder().name("Llanquihue").region(X).build(),
                CommuneDocument.builder().name("Los Muermos").region(X).build(),
                CommuneDocument.builder().name("Maullín").region(X).build(),
                CommuneDocument.builder().name("Puerto Montt").region(X).build(),
                CommuneDocument.builder().name("Puerto Varas").region(X).build(),
                CommuneDocument.builder().name("Osorno").region(X).build(),
                CommuneDocument.builder().name("Puero Octay").region(X).build(),
                CommuneDocument.builder().name("Purranque").region(X).build(),
                CommuneDocument.builder().name("Puyehue").region(X).build(),
                CommuneDocument.builder().name("Río Negro").region(X).build(),
                CommuneDocument.builder().name("San Juan de la Costa").region(X).build(),
                CommuneDocument.builder().name("San Pablo").region(X).build(),
                CommuneDocument.builder().name("Chaitén").region(X).build(),
                CommuneDocument.builder().name("Futaleufú").region(X).build(),
                CommuneDocument.builder().name("Hualaihué").region(X).build(),
                CommuneDocument.builder().name("Palena").region(X).build(),
                CommuneDocument.builder().name("Aisén").region(XI).build(),
                CommuneDocument.builder().name("Cisnes").region(XI).build(),
                CommuneDocument.builder().name("Guaitecas").region(XI).build(),
                CommuneDocument.builder().name("Cochrane").region(XI).build(),
                CommuneDocument.builder().name("O'higgins").region(XI).build(),
                CommuneDocument.builder().name("Tortel").region(XI).build(),
                CommuneDocument.builder().name("Coihaique").region(XI).build(),
                CommuneDocument.builder().name("Lago Verde").region(XI).build(),
                CommuneDocument.builder().name("Chile Chico").region(XI).build(),
                CommuneDocument.builder().name("Río Ibáñez").region(XI).build(),
                CommuneDocument.builder().name("Antártica").region(XII).build(),
                CommuneDocument.builder().name("Cabo de Hornos").region(XII).build(),
                CommuneDocument.builder().name("Laguna Blanca").region(XII).build(),
                CommuneDocument.builder().name("Punta Arenas").region(XII).build(),
                CommuneDocument.builder().name("Río Verde").region(XII).build(),
                CommuneDocument.builder().name("San Gregorio").region(XII).build(),
                CommuneDocument.builder().name("Porvenir").region(XII).build(),
                CommuneDocument.builder().name("Primavera").region(XII).build(),
                CommuneDocument.builder().name("Timaukel").region(XII).build(),
                CommuneDocument.builder().name("Natales").region(XII).build(),
                CommuneDocument.builder().name("Torres del Paine").region(XII).build(),
                CommuneDocument.builder().name("Cabildo").region(V).build()
        );

        communeRepository.saveAll(communes);
    }

    public void generateRoles() {
        List<RoleDocument> roles = Arrays.asList(
                RoleDocument.builder().name("ROLE_ADMIN").build(),
                RoleDocument.builder().name("ROLE_USER").build(),
                RoleDocument.builder().name("ROLE_PARAMEDIC").build()
        );

        roleRepository.saveAll(roles);
    }

    public void generateNationalities() {
        nationalityRepository.saveAll(Arrays.asList(
                NationalityDocument.builder().name("AFGANA").build()
                ,NationalityDocument.builder().name("ALBANESA").build()
                ,NationalityDocument.builder().name("ALEMANA").build()
                ,NationalityDocument.builder().name("ANDORRANA").build()
                ,NationalityDocument.builder().name("ANGOLEÑA").build()
                ,NationalityDocument.builder().name("ANTIGUANA").build()
                ,NationalityDocument.builder().name("SAUDÍ").build()
                ,NationalityDocument.builder().name("ARGELINA").build()
                ,NationalityDocument.builder().name("ARGENTINA").build()
                ,NationalityDocument.builder().name("ARMENIA").build()
                ,NationalityDocument.builder().name("ARUBEÑA").build()
                ,NationalityDocument.builder().name("AUSTRALIANA").build()
                ,NationalityDocument.builder().name("AUSTRIACA").build()
                ,NationalityDocument.builder().name("AZERBAIYANA").build()
                ,NationalityDocument.builder().name("BAHAMEÑA").build()
                ,NationalityDocument.builder().name("BANGLADESÍ").build()
                ,NationalityDocument.builder().name("BARBADENSE").build()
                ,NationalityDocument.builder().name("BAREINÍ").build()
                ,NationalityDocument.builder().name("BELGA").build()
                ,NationalityDocument.builder().name("BELICEÑA").build()
                ,NationalityDocument.builder().name("BENINÉSA").build()
                ,NationalityDocument.builder().name("BIELORRUSA").build()
                ,NationalityDocument.builder().name("BIRMANA").build()
                ,NationalityDocument.builder().name("BOLIVIANA").build()
                ,NationalityDocument.builder().name("BOSNIA").build()
                ,NationalityDocument.builder().name("BOTSUANA").build()
                ,NationalityDocument.builder().name("BRASILEÑA").build()
                ,NationalityDocument.builder().name("BRUNEANA").build()
                ,NationalityDocument.builder().name("BÚLGARA").build()
                ,NationalityDocument.builder().name("BURKINÉS").build()
                ,NationalityDocument.builder().name("BURUNDÉSA").build()
                ,NationalityDocument.builder().name("BUTANÉSA").build()
                ,NationalityDocument.builder().name("CABOVERDIANA").build()
                ,NationalityDocument.builder().name("CAMBOYANA").build()
                ,NationalityDocument.builder().name("CAMERUNESA").build()
                ,NationalityDocument.builder().name("CANADIENSE").build()
                ,NationalityDocument.builder().name("CATARÍ").build()
                ,NationalityDocument.builder().name("CHADIANA").build()
                ,NationalityDocument.builder().name("CHILENA").build()
                ,NationalityDocument.builder().name("CHINA").build()
                ,NationalityDocument.builder().name("CHIPRIOTA").build()
                ,NationalityDocument.builder().name("VATICANA").build()
                ,NationalityDocument.builder().name("COLOMBIANA").build()
                ,NationalityDocument.builder().name("COMORENSE").build()
                ,NationalityDocument.builder().name("NORCOREANA").build()
                ,NationalityDocument.builder().name("SURCOREANA").build()
                ,NationalityDocument.builder().name("MARFILEÑA").build()
                ,NationalityDocument.builder().name("COSTARRICENSE").build()
                ,NationalityDocument.builder().name("CROATA").build()
                ,NationalityDocument.builder().name("CUBANA").build()
                ,NationalityDocument.builder().name("DANÉSA").build()
                ,NationalityDocument.builder().name("DOMINIQUÉS").build()
                ,NationalityDocument.builder().name("ECUATORIANA").build()
                ,NationalityDocument.builder().name("EGIPCIA").build()
                ,NationalityDocument.builder().name("SALVADOREÑA").build()
                ,NationalityDocument.builder().name("EMIRATÍ").build()
                ,NationalityDocument.builder().name("ERITREA").build()
                ,NationalityDocument.builder().name("ESLOVACA").build()
                ,NationalityDocument.builder().name("ESLOVENA").build()
                ,NationalityDocument.builder().name("ESPAÑOLA").build()
                ,NationalityDocument.builder().name("ESTADOUNIDENSE").build()
                ,NationalityDocument.builder().name("ESTONIA").build()
                ,NationalityDocument.builder().name("ETÍOPE").build()
                ,NationalityDocument.builder().name("FILIPINA").build()
                ,NationalityDocument.builder().name("FINLANDÉSA").build()
                ,NationalityDocument.builder().name("FIYIANA").build()
                ,NationalityDocument.builder().name("FRANCÉSA").build()
                ,NationalityDocument.builder().name("GABONÉSA").build()
                ,NationalityDocument.builder().name("GAMBIANA").build()
                ,NationalityDocument.builder().name("GEORGIANA").build()
                ,NationalityDocument.builder().name("GIBRALTAREÑA").build()
                ,NationalityDocument.builder().name("GHANÉSA").build()
                ,NationalityDocument.builder().name("GRANADINA").build()
                ,NationalityDocument.builder().name("GRIEGA").build()
                ,NationalityDocument.builder().name("GROENLANDÉSA").build()
                ,NationalityDocument.builder().name("GUATEMALTECA").build()
                ,NationalityDocument.builder().name("ECUATOGUINEANA").build()
                ,NationalityDocument.builder().name("GUINEANA").build()
                ,NationalityDocument.builder().name("GUINEANA").build()
                ,NationalityDocument.builder().name("GUYANESA").build()
                ,NationalityDocument.builder().name("HAITIANA").build()
                ,NationalityDocument.builder().name("HONDUREÑA").build()
                ,NationalityDocument.builder().name("HÚNGARA").build()
                ,NationalityDocument.builder().name("HINDÚ").build()
                ,NationalityDocument.builder().name("INDONESIA").build()
                ,NationalityDocument.builder().name("IRAQUÍ").build()
                ,NationalityDocument.builder().name("IRANÍ").build()
                ,NationalityDocument.builder().name("IRLANDÉSA").build()
                ,NationalityDocument.builder().name("ISLANDÉSA").build()
                ,NationalityDocument.builder().name("COOKIANA").build()
                ,NationalityDocument.builder().name("MARSHALÉSA").build()
                ,NationalityDocument.builder().name("SALOMONENSE").build()
                ,NationalityDocument.builder().name("ISRAELÍ").build()
                ,NationalityDocument.builder().name("ITALIANA").build()
                ,NationalityDocument.builder().name("JAMAIQUINA").build()
                ,NationalityDocument.builder().name("JAPONÉSA").build()
                ,NationalityDocument.builder().name("JORDANA").build()
                ,NationalityDocument.builder().name("KAZAJA").build()
                ,NationalityDocument.builder().name("KENIATA").build()
                ,NationalityDocument.builder().name("KIRGUISA").build()
                ,NationalityDocument.builder().name("KIRIBATIANA").build()
                ,NationalityDocument.builder().name("KUWAITÍ").build()
                ,NationalityDocument.builder().name("LAOSIANA").build()
                ,NationalityDocument.builder().name("LESOTENSE").build()
                ,NationalityDocument.builder().name("LETÓNA").build()
                ,NationalityDocument.builder().name("LIBANÉSA").build()
                ,NationalityDocument.builder().name("LIBERIANA").build()
                ,NationalityDocument.builder().name("LIBIA").build()
                ,NationalityDocument.builder().name("LIECHTENSTEINIANA").build()
                ,NationalityDocument.builder().name("LITUANA").build()
                ,NationalityDocument.builder().name("LUXEMBURGUÉSA").build()
                ,NationalityDocument.builder().name("MALGACHE").build()
                ,NationalityDocument.builder().name("MALASIA").build()
                ,NationalityDocument.builder().name("MALAUÍ").build()
                ,NationalityDocument.builder().name("MALDIVA").build()
                ,NationalityDocument.builder().name("MALIENSE").build()
                ,NationalityDocument.builder().name("MALTÉSA").build()
                ,NationalityDocument.builder().name("MARROQUÍ").build()
                ,NationalityDocument.builder().name("MARTINIQUÉS").build()
                ,NationalityDocument.builder().name("MAURICIANA").build()
                ,NationalityDocument.builder().name("MAURITANA").build()
                ,NationalityDocument.builder().name("MEXICANA").build()
                ,NationalityDocument.builder().name("MICRONESIA").build()
                ,NationalityDocument.builder().name("MOLDAVA").build()
                ,NationalityDocument.builder().name("MONEGASCA").build()
                ,NationalityDocument.builder().name("MONGOLA").build()
                ,NationalityDocument.builder().name("MONTENEGRINA").build()
                ,NationalityDocument.builder().name("MOZAMBIQUEÑA").build()
                ,NationalityDocument.builder().name("NAMIBIA").build()
                ,NationalityDocument.builder().name("NAURUANA").build()
                ,NationalityDocument.builder().name("NEPALÍ").build()
                ,NationalityDocument.builder().name("NICARAGÜENSE").build()
                ,NationalityDocument.builder().name("NIGERINA").build()
                ,NationalityDocument.builder().name("NIGERIANA").build()
                ,NationalityDocument.builder().name("NORUEGA").build()
                ,NationalityDocument.builder().name("NEOZELANDÉSA").build()
                ,NationalityDocument.builder().name("OMANÍ").build()
                ,NationalityDocument.builder().name("NEERLANDÉSA").build()
                ,NationalityDocument.builder().name("PAKISTANÍ").build()
                ,NationalityDocument.builder().name("PALAUANA").build()
                ,NationalityDocument.builder().name("PALESTINA").build()
                ,NationalityDocument.builder().name("PANAMEÑA").build()
                ,NationalityDocument.builder().name("PAPÚ").build()
                ,NationalityDocument.builder().name("PARAGUAYA").build()
                ,NationalityDocument.builder().name("PERUANA").build()
                ,NationalityDocument.builder().name("POLACA").build()
                ,NationalityDocument.builder().name("PORTUGUÉSA").build()
                ,NationalityDocument.builder().name("PUERTORRIQUEÑA").build()
                ,NationalityDocument.builder().name("BRITÁNICA").build()
                ,NationalityDocument.builder().name("CENTROAFRICANA").build()
                ,NationalityDocument.builder().name("CHECA").build()
                ,NationalityDocument.builder().name("MACEDONIA").build()
                ,NationalityDocument.builder().name("CONGOLEÑA").build()
                ,NationalityDocument.builder().name("CONGOLEÑA").build()
                ,NationalityDocument.builder().name("DOMINICANA").build()
                ,NationalityDocument.builder().name("SUDAFRICANA").build()
                ,NationalityDocument.builder().name("RUANDÉSA").build()
                ,NationalityDocument.builder().name("RUMANA").build()
                ,NationalityDocument.builder().name("RUSA").build()
                ,NationalityDocument.builder().name("SAMOANA").build()
                ,NationalityDocument.builder().name("CRISTOBALEÑA").build()
                ,NationalityDocument.builder().name("SANMARINENSE").build()
                ,NationalityDocument.builder().name("SANVICENTINA").build()
                ,NationalityDocument.builder().name("SANTALUCENSE").build()
                ,NationalityDocument.builder().name("SANTOTOMENSE").build()
                ,NationalityDocument.builder().name("SENEGALÉSA").build()
                ,NationalityDocument.builder().name("SERBIA").build()
                ,NationalityDocument.builder().name("SEYCHELLENSE").build()
                ,NationalityDocument.builder().name("SIERRALEONÉSA").build()
                ,NationalityDocument.builder().name("SINGAPURENSE").build()
                ,NationalityDocument.builder().name("SIRIA").build()
                ,NationalityDocument.builder().name("SOMALÍ").build()
                ,NationalityDocument.builder().name("CEILANÉSA").build()
                ,NationalityDocument.builder().name("SUAZI").build()
                ,NationalityDocument.builder().name("SURSUDANÉSA").build()
                ,NationalityDocument.builder().name("SUDANÉSA").build()
                ,NationalityDocument.builder().name("SUECA").build()
                ,NationalityDocument.builder().name("SUIZA").build()
                ,NationalityDocument.builder().name("SURINAMESA").build()
                ,NationalityDocument.builder().name("TAILANDÉSA").build()
                ,NationalityDocument.builder().name("TANZANA").build()
                ,NationalityDocument.builder().name("TAYIKA").build()
                ,NationalityDocument.builder().name("TIMORENSE").build()
                ,NationalityDocument.builder().name("TOGOLÉSA").build()
                ,NationalityDocument.builder().name("TONGANA").build()
                ,NationalityDocument.builder().name("TRINITENSE").build()
                ,NationalityDocument.builder().name("TUNECINA").build()
                ,NationalityDocument.builder().name("TURCOMANA").build()
                ,NationalityDocument.builder().name("TURCA").build()
                ,NationalityDocument.builder().name("TUVALUANA").build()
                ,NationalityDocument.builder().name("UCRANIANA").build()
                ,NationalityDocument.builder().name("UGANDÉSA").build()
                ,NationalityDocument.builder().name("URUGUAYA").build()
                ,NationalityDocument.builder().name("UZBEKA").build()
                ,NationalityDocument.builder().name("VANUATUENSE").build()
                ,NationalityDocument.builder().name("VENEZOLANA").build()
                ,NationalityDocument.builder().name("VIETNAMITA").build()
                ,NationalityDocument.builder().name("YEMENÍ").build()
                ,NationalityDocument.builder().name("YIBUTIANA").build()
                ,NationalityDocument.builder().name("ZAMBIANA").build()
                ,NationalityDocument.builder().name("ZIMBABUENSE").build()
        ));
    }

}
