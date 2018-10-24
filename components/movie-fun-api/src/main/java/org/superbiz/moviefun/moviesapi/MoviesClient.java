package org.superbiz.moviefun.moviesapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

public class MoviesClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    private static ParameterizedTypeReference<List<MovieInfo>> movieListType = new ParameterizedTypeReference<List<MovieInfo>>() {
    };
    private String moviesUrl;
    private RestOperations restOperations;

    public MoviesClient(String moviesUrl, RestOperations restOperations) {
        this.moviesUrl = moviesUrl;
        this.restOperations = restOperations;
    }

    public MovieInfo find(Long id) {
        throw new RuntimeException("Not Implemented find");
    }

    public void addMovie(MovieInfo movie) {
        logger.debug("Creating movie with title {}, and year {}", movie.getTitle(), movie.getYear());

        restOperations.postForEntity(moviesUrl, movie, MovieInfo.class);
    }

    public void updateMovie(MovieInfo movie) {
        throw new RuntimeException("Not Implemented updateMovie");
    }


    public void deleteMovie(MovieInfo movie) {
        throw new RuntimeException("Not Implemented deleteMovie");
    }


    public void deleteMovieId(long id) {
        restOperations.delete(moviesUrl + "/" +id);
    }

    public List<MovieInfo> getMovies() {
        return restOperations.exchange(moviesUrl, GET, null, movieListType).getBody();
    }

    public List<MovieInfo> findAll(int firstResult, int maxResults) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl)
                .queryParam("start", firstResult)
                .queryParam("pageSize", maxResults);
        return restOperations.exchange(builder.toUriString(), GET, null, movieListType).getBody();
    }

    public int countAll() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl + "/count");
        return restOperations.getForEntity(builder.toUriString(), Integer.class).getBody();
    }

    public int count(String field, String searchTerm) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl + "/count")
                .queryParam("field", field)
                .queryParam("key", searchTerm);
        return restOperations.getForEntity(builder.toUriString(), Integer.class).getBody();
    }

    public List<MovieInfo> findRange(String field, String searchTerm, int firstResult, int maxResults) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl)
                .queryParam("field", field)
                .queryParam("key", searchTerm)
                .queryParam("start", firstResult)
                .queryParam("pageSize", maxResults);
        return restOperations.exchange(builder.toUriString(),GET,null, movieListType).getBody();
    }

    public void clean() {
//        entityManager.createQuery("delete from MovieInfo").executeUpdate();
    }
}
