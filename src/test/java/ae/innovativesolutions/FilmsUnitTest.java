package ae.innovativesolutions;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpEntities;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import org.junit.Test;

public class FilmsUnitTest extends JUnitRouteTest {

  ActorSystem system = ActorSystem.create("filmHttpServer");

  ActorRef filmActorRef = system.actorOf(FilmActor.props(), "filmActor");

  TestRoute appRoute = testRoute(new FilmServer(filmActorRef ).routes());

  @Test
  public void whenRequest_thenActorResponds() {

    appRoute.run(HttpRequest.GET("/films/beetlejuice"))
            .assertEntity(beetlejuice())
            .assertStatusCode(200);

    appRoute.run(HttpRequest.GET("/films/42"))
            .assertStatusCode(404);

    appRoute.run(HttpRequest.DELETE("/films/1"))
            .assertStatusCode(405);

    appRoute.run(HttpRequest.DELETE("/films/42"))
            .assertStatusCode(405);

    appRoute.run(HttpRequest.POST("/films")
            .withEntity(HttpEntities.create(ContentTypes.APPLICATION_JSON, newFilm())))
            .assertStatusCode(201);

  }

  private String beetlejuice() {
    return "{\"country\":\"US\",\"description\":\"A couple of recently deceased ghosts contract the services of a bio-exorcist in order to remove the obnoxious new owners of their house.\",\"genre\":\"Comedy\",\"id\":1,\"name\":\"Beetlejuice\",\"photo\":\"https://images-na.ssl-images-amazon.com/images/M/MV5BMTUwODE3MDE0MV5BMl5BanBnXkFtZTgwNTk1MjI4MzE@._V1_SX300.jpg\",\"rating\":\"5\",\"releaseDate\":\"1998\",\"slug\":\"beetlejuice\",\"ticketPrice\":\"200\"}";
  }

  private String newFilm() {
    return "{\"country\": \"PK\",\"description\": \"test description\",\"genre\":\"Comedy\",\"id\": 10,\"name\": \"test\",\"photo\": \"https://place-hold.it/300x177/35af7b/fff&fontsize=30&text=test\",\"rating\": \"5\",\"releaseDate\": \"1998\",\"slug\": \"beetlejuice\",\"ticketPrice\": \"200\"}";
  }

}
