package ae.innovativesolutions;



import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FilmsUnitTest {



    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void getProductsList() throws Exception {
        // Given
        String name = RandomStringUtils.randomAlphabetic( 8 );
        HttpUriRequest request = new HttpGet( "http://localhost:8083/film/" + name );

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        // Then
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_NOT_FOUND));

    }

    private String beetlejuice() {
    return "{\"country\":\"US\",\"description\":\"A couple of recently deceased ghosts contract the services of a bio-exorcist in order to remove the obnoxious new owners of their house.\",\"genre\":\"Comedy\",\"id\":1,\"name\":\"Beetlejuice\",\"photo\":\"https://images-na.ssl-images-amazon.com/images/M/MV5BMTUwODE3MDE0MV5BMl5BanBnXkFtZTgwNTk1MjI4MzE@._V1_SX300.jpg\",\"rating\":\"5\",\"releaseDate\":\"1998\",\"slug\":\"beetlejuice\",\"ticketPrice\":\"200\"}";
  }

  private String newFilm() {
    return "{\"country\": \"PK\",\"description\": \"test description\",\"genre\":\"Comedy\",\"id\": 10,\"name\": \"test\",\"photo\": \"https://place-hold.it/300x177/35af7b/fff&fontsize=30&text=test\",\"rating\": \"5\",\"releaseDate\": \"1998\",\"slug\": \"beetlejuice\",\"ticketPrice\": \"200\"}";
  }

}
