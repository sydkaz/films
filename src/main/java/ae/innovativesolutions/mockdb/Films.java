package ae.innovativesolutions.mockdb;

import ae.innovativesolutions.model.Film;
import ae.innovativesolutions.repositoriy.FilmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class Films {

    private static final Logger log = LoggerFactory.getLogger(Films.class);

    @Bean
    CommandLineRunner initDatabase(FilmRepository filmRepository) {

        return args -> {

            log.info("Preloading " + filmRepository.save(new Film(1l,"beetlejuice","Beetlejuice", "A couple of recently deceased ghosts contract the services of a bio-exorcist in order to remove the obnoxious new owners of their house.","1998","5","200","US","Comedy",
                    "https://images-na.ssl-images-amazon.com/images/M/MV5BMTUwODE3MDE0MV5BMl5BanBnXkFtZTgwNTk1MjI4MzE@._V1_SX300.jpg")));

            log.info("Preloading " + filmRepository.save(new Film(2l,"thecottonclub","The Cotton Club", "The Cotton Club was a famous night club in Harlem. The story follows the people that visited the club, those that ran it, and is peppered with the Jazz music that made it so famous.","1984","5","200","US","Comedy",
                    "https://images-na.ssl-images-amazon.com/images/M/MV5BMTU5ODAyNzA4OV5BMl5BanBnXkFtZTcwNzYwNTIzNA@@._V1_SX300.jpg")));

            log.info("Preloading " + filmRepository.save(new Film(3l,"theshawshankredemption","The Shawshank Redemption", "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.","1998","5","200","US","Comedy",
                    "https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1_SX300.jpg")));

            log.info("Preloading " + filmRepository.save(new Film(4l,"crocodiledundee","Crocodile Dundee", "An American reporter goes to the Australian outback to meet an eccentric crocodile poacher and invites him to New York City","1998","5","200","US","Comedy",
                    "https://images-na.ssl-images-amazon.com/images/M/MV5BMTg0MTU1MTg4NF5BMl5BanBnXkFtZTgwMDgzNzYxMTE@._V1_SX300.jpg")));

            log.info("Preloading " + filmRepository.save(new Film(5l,"valkyrie","Valkyrie", "A dramatization of the 20 July assassination and political coup plot by desperate renegade German Army officers against Hitler during World War II.","1998","5","200","US","Comedy",
                    "http://ia.media-imdb.com/images/M/MV5BMTg3Njc2ODEyN15BMl5BanBnXkFtZTcwNTAwMzc3NA@@._V1_SX300.jpg")));
        };
    }
}