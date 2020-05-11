package it.polito.ezgas.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.GasStationRepository;
import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.service.GasStationService;

/**
 * Created by softeng on 27/4/2020.
 */
@Service
public class GasStationServiceimpl implements GasStationService {
	
	@Autowired
	GasStationRepository gasStationRepository;
	
	@Autowired
	UserRepository userRepository;

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
		if (gasStationDto.getLat() > -90 && gasStationDto.getLat() < 90)
			gasStation.setLat(gasStationDto.getLat());
		else
			throw new GPSDataException("Latitude value cannot be negative");
		if (gasStationDto.getLon() > -180 && gasStationDto.getLon() < 180)
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
	        	return null;
		} else
			throw new InvalidGasStationException("GasStationId cannot be negative");
	}

	@Override
	public List<GasStationDto> getGasStationsByGasolineType(String gasolinetype) throws InvalidGasTypeException {
		List<GasStation> gasStations;
		switch (gasolinetype) {
		case "Diesel":
			gasStations = gasStationRepository.findByHasDieselTrue();
			break;
		case "Super":
			gasStations = gasStationRepository.findByHasSuperTrue();
			break;
		case "SuperPlus":
			gasStations = gasStationRepository.findByHasSuperPlusTrue();
			break;
		case "Gas":
			gasStations = gasStationRepository.findByHasGasTrue();
			break;
		case "Methane":
			gasStations = gasStationRepository.findByHasMethaneTrue();
			break;
		default:
			throw new InvalidGasTypeException("Invalid gasoline type");
		}
		this.updateDependabilities(gasStations);
		return GasStationConverter.toGasStationDto(gasStations);
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
		if(dieselPrice==-1)
			dieselPrice=0;
		if(superPrice==-1)
			superPrice=0;
		if(superPlusPrice==-1)
			superPlusPrice=0;
		if(gasPrice==-1)
			gasPrice=0;
		if(methanePrice==-1)
			methanePrice=0;
		if(dieselPrice < 0 || superPrice < 0 || superPlusPrice < 0 || gasPrice < 0 || methanePrice < 0)
			throw new PriceException("Price cannot be negative");
		if(userId > 0) {
			if(gasStationId > 0) {
				GasStation gasStation = gasStationRepository.findByGasStationId(gasStationId);
				if(gasStation != null) {
					gasStation.setDieselPrice(dieselPrice);
					gasStation.setSuperPrice(superPrice);
					gasStation.setSuperPlusPrice(superPlusPrice);
					gasStation.setGasPrice(gasPrice);
					gasStation.setMethanePrice(methanePrice);
					gasStation.setReportUser(userId);
					String timeStamp = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss").format(new java.util.Date());
					gasStation.setReportTimestamp(timeStamp);	
					this.updateDependabilities(Arrays.asList(gasStation));
					gasStationRepository.save(gasStation);
				}
			} else
				throw new InvalidGasStationException("GasStationId cannot be negative");
		} else
			throw new InvalidUserException("UserId cannot be negative");
	}
	
	
	public void updateDependabilities(List<GasStation> gasStations) {
		String todayTimeStamp = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss").format(new java.util.Date());
		for(GasStation gasStation:gasStations) {
			if(gasStation.getReportUser() != null) {
				int diff = Integer.parseInt(todayTimeStamp.replace("/", "").split("-")[0])-Integer.parseInt(gasStation.getReportTimestamp().replace("/", "").split("-")[0]);
				double obsolescence = 0.0;
				if(diff<=7)
					obsolescence=1-diff/7;
				
				User user = userRepository.findByUserId(gasStation.getReportUser());
				gasStation.setReportDependability(50*(user.getReputation()+5)/10+50*obsolescence);
				gasStationRepository.save(gasStation);
			}
		}
	}

	@Override
	public List<GasStationDto> getGasStationByCarSharing(String carSharing) {
		// TODO Auto-generated method stub
		return null;
	
}
