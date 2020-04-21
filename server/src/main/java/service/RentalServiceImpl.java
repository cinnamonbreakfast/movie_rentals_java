package service;

import domain.Rental;
import domain.exceptions.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import repository.RentalRepository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component("rentalService")
public class RentalServiceImpl implements RentalService{
    @Autowired
    private RentalRepository rentalRepository;

    @Override
    public void addRental(Rental entity) throws ValidatorException {
        rentalRepository.save(entity);
    }

    @Override
    public void removeRental(Long ID) {
        rentalRepository.deleteById(ID);
    }

    @Override
    public Long mostRentedMovie() {
        Iterable<Rental> rentals = rentalRepository.findAll();
        List<Long> listOfMovies = new ArrayList<Long>();
        rentals.forEach(x->listOfMovies.add(x.getMovie()));
        listOfMovies.stream().
                collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse((long) -1);
            return null;
    }

    @Override
    public Long mostLoyalCustomer() {
        Iterable<Rental> rentals = rentalRepository.findAll();
        List<Long> listOfMovies = new ArrayList<Long>();
        rentals.forEach(x->listOfMovies.add(x.getClient()));
        listOfMovies.stream().
                collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse((long) -1);
            return null;
    }

    @Override
    public List<Rental> getAll() {
        Iterable<Rental> rentals = rentalRepository.findAll();
        return StreamSupport.stream(
                rentals.spliterator(),
                false)
                .collect(Collectors.toList());
    }

    @Override
    public List<Rental> getAllSortedAscendingByFields(String... fields) {
        Sort sort = new Sort(Sort.Direction.ASC, fields);
        Iterable<Rental> rentals = rentalRepository.findAll(sort);
        return StreamSupport.stream(
                rentals.spliterator(),
                false)
                .collect(Collectors.toList());
    }

    @Override
    public List<Rental> getAllSortedDescendingByFields(String... fields) {
        Sort sort = new Sort(Sort.Direction.DESC, fields);
        Iterable<Rental> rentals = rentalRepository.findAll(sort);
        return StreamSupport.stream(
                rentals.spliterator(),
                false)
                .collect(Collectors.toList());
    }

    @Override
    public Rental getByID(Long aLong) {
        Optional<Rental> rental = rentalRepository.findById(aLong);
        if(rental.isPresent())
            return rental.get();
        throw new RuntimeException("Could not find rental by given ID.");
    }
}
