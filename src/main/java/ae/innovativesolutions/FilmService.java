package ae.innovativesolutions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FilmService {

  private final static List<Film> films = new ArrayList<>();

  static {
      films.add(new Film(1l,"Beetlejuice", "A couple of recently deceased ghosts contract the services of a bio-exorcist in order to remove the obnoxious new owners of their house.","1998","5","200","US","Comedy",
              "https://images-na.ssl-images-amazon.com/images/M/MV5BMTUwODE3MDE0MV5BMl5BanBnXkFtZTgwNTk1MjI4MzE@._V1_SX300.jpg"));

      films.add(new Film(2l,"The Cotton Club", "The Cotton Club was a famous night club in Harlem. The story follows the people that visited the club, those that ran it, and is peppered with the Jazz music that made it so famous.","1984","5","200","US","Comedy",
              "https://images-na.ssl-images-amazon.com/images/M/MV5BMTU5ODAyNzA4OV5BMl5BanBnXkFtZTcwNzYwNTIzNA@@._V1_SX300.jpg"));

      films.add(new Film(3l,"The Shawshank Redemption", "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.","1998","5","200","US","Comedy",
              "https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1_SX300.jpg"));

      films.add(new Film(4l,"Crocodile Dundee", "An American reporter goes to the Australian outback to meet an eccentric crocodile poacher and invites him to New York City","1998","5","200","US","Comedy",
              "https://images-na.ssl-images-amazon.com/images/M/MV5BMTg0MTU1MTg4NF5BMl5BanBnXkFtZTgwMDgzNzYxMTE@._V1_SX300.jpg"));

      films.add(new Film(5l,"Valkyrie", "A dramatization of the 20 July assassination and political coup plot by desperate renegade German Army officers against Hitler during World War II.","1998","5","200","US","Comedy",
              "http://ia.media-imdb.com/images/M/MV5BMTg3Njc2ODEyN15BMl5BanBnXkFtZTcwNTAwMzc3NA@@._V1_SX300.jpg"));

  }

  public List<Film> getFilms(){
    return films;
  }

}
