package ae.innovativesolutions.mockdb;

import ae.innovativesolutions.model.*;
import ae.innovativesolutions.repositoriy.CommentRepository;
import ae.innovativesolutions.repositoriy.FilmRepository;
import ae.innovativesolutions.repositoriy.RoleRepository;
import ae.innovativesolutions.repositoriy.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
class MockDB {

    private static final Logger log = LoggerFactory.getLogger(MockDB.class);

    @Autowired
    RoleRepository roleRepository;


    @Autowired
    UserRepository userRepository;

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    CommentRepository commentRepository;

    public String passwordHasher(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository roleRepository, FilmRepository filmRepository, CommentRepository commentRepository) {
        String hashedPassword = passwordHasher("123");
        return args -> {

            User admin = new User();
            admin.setName("admin");
            admin.setUsername("admin");
            admin.setPassword(hashedPassword);
            admin.setEmail("admin@admin.com");


            //Role r = roleRepository.findByRole(RoleName.ROLE_ADMIN);
            Role adminRole = new Role();
            adminRole.setName(RoleName.ROLE_ADMIN);
            Set<Role> adminRoles = new HashSet<Role>();
            adminRoles.add(adminRole);
            log.info("Preloading " +roleRepository.save(adminRole));

            admin.setRoles(adminRoles);
            log.info("Preloading " +userRepository.save(admin));



            User user = new User();
            user.setUsername("user");
            user.setName("user");
            user.setPassword(hashedPassword);
            user.setEmail("user@user.com");
            Role userRole = new Role();
            userRole.setName(RoleName.ROLE_USER);
            Set<Role> userRoles= new HashSet<Role>();
            userRoles.add(userRole);
            log.info("Preloading " +roleRepository.save(userRole));

            user.setRoles(userRoles);

            log.info("Preloading " +userRepository.save(user));



            /*User userAdmin = new User();
            userAdmin.setUsername("superAdmin");
            user.setName("superAdmin");
            userAdmin.setPassword(hashedPassword);
            userAdmin.setEmail("useradmin@useradmin.com");
            Set<Role> superAdminRoles= new HashSet<Role>();
            superAdminRoles.add(userRole);
            superAdminRoles.add(adminRole);
            userAdmin.setRoles(superAdminRoles);
            userRepository.save(userAdmin);*/

            Film f1 = new Film(1,"beetlejuice","Beetlejuice", "A couple of recently deceased ghosts contract the services of a bio-exorcist in order to remove the obnoxious new owners of their house.","10/30/1987","5","200","US","Comedy",
                    "http://localhost:7777/upload/static/images/banner.gif");

            Film f2 = new Film(2,"thecottonclub","The Cotton Club", "The Cotton Club was a famous night club in Harlem. The story follows the people that visited the club, those that ran it, and is peppered with the Jazz music that made it so famous.","06/25/1987","5","200","US","Comedy",
                    "http://localhost:7777/upload/static/images/banner.gif");

            Film f3 = new Film(3,"theshawshankredemption","The Shawshank Redemption", "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.","02/28/1990","5","200","US","Comedy",
                    "http://localhost:7777/upload/static/images/banner.gif");

            Film f4 = new Film(4,"crocodiledundee","Crocodile Dundee", "An American reporter goes to the Australian outback to meet an eccentric crocodile poacher and invites him to New York City","06/25/1987","5","200","US","Comedy",
                    "http://localhost:7777/upload/static/images/banner.gif");

            Film f5 = new Film(5,"valkyrie","Valkyrie", "A dramatization of the 20 July assassination and political coup plot by desperate renegade German Army officers against Hitler during World War II.","08/15/2000","5","200","US","Comedy",
                    "http://localhost:7777/upload/static/images/banner.gif");

            log.info("Preloading " + filmRepository.save(f1));

            log.info("Preloading " + filmRepository.save(f2));

            log.info("Preloading " + filmRepository.save(f3));

            log.info("Preloading " + filmRepository.save(f4));

            log.info("Preloading " + filmRepository.save(f5));


            Comment commen1 = new Comment(f1, user, "Test Comment 1");
            commentRepository.save(commen1);

            Comment commen2 = new Comment(f1, user, "Test Comment 2");
            commentRepository.save(commen2);

            Comment commen3 = new Comment(f1, user, "Test Comment 3");
            commentRepository.save(commen3);


            Comment commen4 = new Comment(f1, admin, "Test Comment 1");
            commentRepository.save(commen4);

            Comment commen5 = new Comment(f1, admin, "Test Comment 2");
            commentRepository.save(commen5);

            Comment commen6 = new Comment(f1, admin, "Test Comment 3");
            commentRepository.save(commen6);

        };
    }
}