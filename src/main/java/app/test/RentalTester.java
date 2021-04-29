package app.test;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.assertThat;

public class TestAPI {
    @Test
    static void rentalTester(){
        HttpUriRequest request = new HttpGet( "http://127.0.0.1:8080/rental" );

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
        // Then
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_NOT_FOUND));

        //test for first 10
        for(int i=0;i<10;++i) {
            request = new HttpGet("http://127.0.0.1:8080/rental/" + i);
            httpResponse = HttpClientBuilder.create().build().execute(request);
            assertThat(
                    httpResponse.getStatusLine().getStatusCode(),
                    equalTo(HttpStatus.SC_NOT_FOUND));

        }
    }

}
