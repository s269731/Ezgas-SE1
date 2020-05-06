package it.polito.ezgas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exception.GPSDataException;
import exception.InvalidGasStationException;
import exception.InvalidGasTypeException;
import exception.InvalidUserException;
import exception.PriceException;
import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.repository.GasStationRepository;
import it.polito.ezgas.service.GasStationService;

/**
 * Created by softeng on 27/4/2020.
 */
@Service
public class GasStationServiceimpl implements GasStationService {
	
	@Autowired
	GasStationRepository gasStationRepository;

	@Override
	public GasStationDto getGasStationById(Integer gasStationId) throws InvalidGasStationException {
		if (gasStationId > 0) {
			GasStation gasStation = gasStationRepository.findByGasStationId(gasStationId);
			if (gasStation != null)
				return GasStationConverter.toGasStationDto(gasStation);
			else
				return null;
		} else
			throw new InvalidGasStationException("GasStationId cannot be negative");
	}

	@Override
	public GasStationDto saveGasStation(GasStationDto gasStationDto) throws PriceException, GPSDataException {
		GasStation gasStation;
		if (gasStationDto.getGasStationId() != null) {	// gas station already inserted --> update
			gasStation = gasStationRepository.findByGasStationId(gasStationDto.getGasStationId());
		} else {	// new gas station --> insert
			gasStation = new GasStation();
		}
		gasStation.setGasStationName(gasStationDto.getGasStationName());
		gasStation.setGasStationAddress(gasStationDto.getGasStationAddress());
		if (gasStationDto.getLat() > 0)
			gasStation.setLat(gasStationDto.getLat());
		else
			throw new GPSDataException("Latitude value cannot be negative");
		if (gasStationDto.getLon() > 0)
			gasStation.setLon(gasStationDto.getLon());
		else
			throw new GPSDataException("Longitude value cannot be negative");
		gasStation.setCarSharing(gasStationDto.getCarSharing());
		gasStation.setHasDiesel(gasStationDto.getHasDiesel());
		gasStation.setHasSuper(gasStationDto.getHasSuper());
		gasStation.setHasSuperPlus(gasStationDto.getHasSuperPlus());
		gasStation.setHasGas(gasStationDto.getHasGas());
		gasStation.setHasMethane(gasStationDto.getHasMethane());			
		gasStationRepository.save(gasStation);
		
		return GasStationConverter.toGasStationDto(gasStation);
	}

	@Override
	public List<GasStationDto> getAllGasStations() {
		List<GasStation> gasStations = gasStationRepository.findAll();
		List<GasStationDto> gasStationsDto = new ArrayList<GasStationDto>();
		for(GasStation gasStation:gasStations)
			gasStationsDto.add(GasStationConverter.toGasStationDto(gasStation));
		return gasStationsDto;
	}

	@Override
	public Boolean deleteGasStation(Integer gasStationId) throws InvalidGasStationException {
		if (gasStationId > 0) {
			GasStation gasStation = gasStationRepository.findByGasStationId(gasStationId);
	        if (gasStation != null) {
	        	gasStationRepository.delete(gasStation);
	        	return true;
	        } else
	        	return false;
		} else
			throw new InvalidGasStationException("GasStationId cannot be negative");
	}

	@Override
	public List<GasStationDto> getGasStationsByGasolineType(String gasolinetype) throws InvalidGasTypeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GasStationDto> getGasStationsByProximity(double lat, double lon) throws GPSDataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GasStationDto> getGasStationsWithCoordinates(double lat, double lon, String gasolinetype,
			String carsharing) throws InvalidGasTypeException, GPSDataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GasStationDto> getGasStationsWithoutCoordinates(String gasolinetype, String carsharing)
			throws InvalidGasTypeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReport(Integer gasStationId, double dieselPrice, double superPrice, double superPlusPrice,
			double gasPrice, double methanePrice, Integer userId)
			throws InvalidGasStationException, PriceException, InvalidUserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GasStationDto> getGasStationByCarSharing(String carSharing) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	

}
