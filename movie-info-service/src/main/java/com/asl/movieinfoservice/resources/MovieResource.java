package com.asl.movieinfoservice.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import com.asl.movieinfoservice.models.Movie;
import com.asl.movieinfoservice.models.MovieSummary;

@RestController
@RequestMapping("/movies")
public class MovieResource {

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Value("${api.key}")
	private String apiKey;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping("/{movieId}")
	public Movie getMovieInfo(@PathVariable("movieId") String movieId) {

		MovieSummary movieSummary = webClientBuilder.build().get()
				.uri("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey).retrieve()
				.bodyToMono(MovieSummary.class).block();

//		MovieSummary movieSummary = restTemplate.getForObject(
//				"https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey, MovieSummary.class);

		return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
	}
}
