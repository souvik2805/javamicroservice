package com.asl.moviecatalogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.asl.moviecatalogservice.models.CatelogItem;
import com.asl.moviecatalogservice.models.Movie;
import com.asl.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping("/{userId}")
	public List<CatelogItem> getCatalog(@PathVariable("userId") String userId) {
		UserRating ratings = webClientBuilder.build().get().uri("http://MOVIE-DATA-SERVICES/ratingsdata/users/12")
				.retrieve().bodyToMono(UserRating.class).block();

		return ratings.getUserRating().stream().map(rating -> {
			// Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" +
			// rating.getMovieId(), Movie.class);

			Movie movie = webClientBuilder.build().get().uri("http://MOVIE-INFO-SERIVCES/movies/" + rating.getMovieId())
					.retrieve().bodyToMono(Movie.class).block();

			return new CatelogItem(movie.getName(), movie.getOverview(), rating.getRating());
		}).collect(Collectors.toList());
		// return Collections.singletonList(new CatelogItem("Transformers", "test", 4));
	}
}
