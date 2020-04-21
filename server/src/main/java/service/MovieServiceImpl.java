package service;

import domain.Movie;
import domain.exceptions.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import repository.MovieRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component("movieService")
public class MovieServiceImpl implements MovieService{
    public static final Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public void addMovie(Movie movie) throws ValidatorException, IOException {
        movieRepository.save(movie);
    }

    @Override
    public List<Movie> getAllMovies(String... sort) {
        Sort sorted = new Sort(sort);
        Iterable<Movie> clients = movieRepository.findAll(sorted);
        return StreamSupport.stream(
                clients.spliterator(),
                false)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movie> filterMovieByName(String s) {
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        Iterable<Movie> movies = movieRepository.findAll(sort);
        return StreamSupport.stream(
                movies.spliterator(),
                false)
                .filter((e)-> e.getName().equals(s))
                .collect(Collectors.toList());

    }

    @Override
    public List<Movie> filterMovieByDescription(String d) {
        Sort sort = new Sort(Sort.Direction.ASC,"description");
        Iterable<Movie> movies = movieRepository.findAll(sort);
        return StreamSupport.stream(
                movies.spliterator(),
                false)
                .filter((e)-> e.getDescription().equals(d))
                .collect(Collectors.toList());
    }

    @Override
    public List<Movie> filterMovieByPrice(int p) {
        Sort sort = new Sort(Sort.Direction.ASC,"price");
        Iterable<Movie> movies = movieRepository.findAll(sort);
        return StreamSupport.stream(
                movies.spliterator(),
                false)
                .filter((e)->e.getPrice()==p)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movie> filterMovieByRating(int r) {
        Sort sort = new Sort(Sort.Direction.ASC,"rating");
        Iterable<Movie> movies = movieRepository.findAll(sort);
        return StreamSupport.stream(
                movies.spliterator(),
                false)
                .filter((e)->e.getRating()==r)
                .collect(Collectors.toList());
    }

    @Override
    public void removeMovie(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateMovie(Movie entity) {
        movieRepository.findById(entity.getId())
                .ifPresent(s -> {
                    s.setName(entity.getName());
                    s.setDescription(entity.getDescription());
                    s.setPrice(entity.getPrice());
                    s.setRating(entity.getRating());
                });
    }

    @Override
    public List<Movie> getAll() {
        Iterable<Movie> movies = movieRepository.findAll();
        return StreamSupport.stream(
                movies.spliterator(),
                false)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movie> getAllSortedAscendingByFields(String... fields) {
        Sort sort = new Sort(Sort.Direction.ASC, fields);
        Iterable<Movie> movies = movieRepository.findAll(sort);
        return StreamSupport.stream(
                movies.spliterator(),
                false)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movie> getAllSortedDescendingByFields(String... fields) {
        Sort sort = new Sort(Sort.Direction.DESC, fields);
        Iterable<Movie> movies = movieRepository.findAll(sort);
        return StreamSupport.stream(
                movies.spliterator(),
                false)
                .collect(Collectors.toList());
    }

    @Override
    public Movie getByID(Long aLong) {
        Optional<Movie> movie = movieRepository.findById(aLong);
        if(movie.isPresent())
            return movie.get();
        throw new RuntimeException("Could not find client by given ID.");
    }
}
