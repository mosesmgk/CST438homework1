package cst438hw1;


import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Hello {
	
	@Autowired
	MovieRepo movieRepo;
	
	// localhost:8080/hello?name=moses
	@GetMapping("/hello")
	public String hello(@RequestParam("name") String name, Model model) {
		model.addAttribute("name", name);
		model.addAttribute("time", new java.util.Date().toString());
		return "index1";
	}
	
	@GetMapping("/movies")
	public String getAllMovies(Model model) {
		List<MovieRatings>  movie_list = movieRepo.findAllMovieRatingsOrderByTitleDateDesc(); 
		model.addAttribute("movieRatings", movie_list);
		
		return "movie_list";
	}
	
	@GetMapping("/movies/new")
	public String createMovie(Model model) {
		MovieRatings movieRatings = new MovieRatings();
		model.addAttribute("movieRatings", movieRatings);
		return "movie_form";
	}
	
	@PostMapping("/movies")
	public String processMovieForm(@Valid MovieRatings movieRatings, BindingResult result, Model modle) {
		
		if(result.hasErrors()) {
			System.out.print("Error");
			return "movie_form";
		}
		movieRatings.setTime(new java.util.Date().toString());
		movieRepo.save(movieRatings);
		
		return "movie_show";
	}

}
