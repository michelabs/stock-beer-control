package com.dio.api.beerstock.service;

import com.dio.api.beerstock.builder.BeerDTOBuilder;
import com.dio.api.beerstock.dto.BeerDTO;
import com.dio.api.beerstock.entity.Beer;
import com.dio.api.beerstock.exception.BeerAlreadyRegisteredException;
import com.dio.api.beerstock.exception.BeerNotFoundException;
import com.dio.api.beerstock.mapper.BeerMapper;
import com.dio.api.beerstock.repository.BeerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BeerServiceTest {

    private static final long INVALID_BEER_ID = 1L;

    @Mock
    private BeerRepository beerRepository;

    private BeerMapper beerMapper = BeerMapper.INSTANCE;

    @InjectMocks
    private BeerService beerService;

    @Test
    void whenBeerInformedThenItShouldBeCreated() throws BeerAlreadyRegisteredException {
        BeerDTO expectedBeertDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedSavedBeer = beerMapper.toModel(expectedBeertDTO);

        when(beerRepository.findByName(expectedBeertDTO.getName())).thenReturn(Optional.empty());
        when(beerRepository.save(expectedSavedBeer)).thenReturn(expectedSavedBeer);

        BeerDTO createdBeerDTO = beerService.createBeer(expectedBeertDTO);

        assertThat(createdBeerDTO.getId(), is(equalTo(expectedBeertDTO.getId())));
        assertThat(createdBeerDTO.getName(), is(equalTo(expectedBeertDTO.getName())));
        assertThat(createdBeerDTO.getQuantity(), is(equalTo(expectedBeertDTO.getQuantity())));
    }

    @Test
    void whenAlreadyRegisteredBeerInformedThenAnExceptionSouldBeThrown() throws BeerAlreadyRegisteredException {
        BeerDTO expectedBeertDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer duplicatedBeer = beerMapper.toModel(expectedBeertDTO);

        when(beerRepository.findByName(expectedBeertDTO.getName())).thenReturn(Optional.of(duplicatedBeer));

        assertThrows(BeerAlreadyRegisteredException.class, () -> beerService.createBeer(expectedBeertDTO));
    }

    @Test
    void whenValidBeerNameIsGivenThenReturneABeer() throws BeerNotFoundException {
        BeerDTO expectedFoundBeertDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedFoundBeer = beerMapper.toModel(expectedFoundBeertDTO);

        when(beerRepository.findByName(expectedFoundBeer.getName())).thenReturn(Optional.of(expectedFoundBeer));

        BeerDTO foundBeerDTO = beerService.findByName((expectedFoundBeertDTO.getName()));

        assertThat(foundBeerDTO, is(equalTo(expectedFoundBeertDTO)));
    }

    @Test
    void whenNotRegisteredBeerNameIsGivenTheReturnThrownAnExcpetion() throws BeerNotFoundException {
        BeerDTO expectedFoundBeertDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        when(beerRepository.findByName(expectedFoundBeertDTO.getName())).thenReturn(Optional.empty());

        assertThrows(BeerNotFoundException.class, () -> beerService.findByName((expectedFoundBeertDTO.getName())));
    }


}
