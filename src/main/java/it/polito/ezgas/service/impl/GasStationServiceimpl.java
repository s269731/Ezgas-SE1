package it.polito.ezgas.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
	
	//@Autowired
	private GasStationRepository gasStationRepository;
	
	//@Autowired
	private UserRepository userRepository;
	
	public GasStationServiceimpl(GasStationRepository gasStationRepository, UserRepository userRepository) {
		this.gasStationRepository = gasStationRepository;
		this.userRepository = userRepository;
	} 

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
		if (gasStationDto.getLat() < -90 || gasStationDto.getLat() > 90 || gasStationDto.getLon() < -180 || gasStationDto.getLon() > 180)
			throw new GPSDataException("Invalid GPS coordinates");	
		GasStation gasStation;
		if (gasStationDto.getGasStationId() != null) {	// gas station already inserted --> update
			gasStation = gasStationRepository.findByGasStationId(gasStationDto.getGasStationId());
			if (gasStation.getGasStationAddress().compareTo(gasStationDto.getGasStationAddress()) != 0 
					|| gasStation.getLat() != gasStationDto.getLat() || gasStation.getLon() != gasStationDto.getLon()) {
				GasStation gs = gasStationRepository.findByGasStationAddressAndLatAndLon(gasStationDto.getGasStationAddress(), gasStationDto.getLat(), gasStationDto.getLon());
				if (gs != null)
					return null;
			}
			if (gasStationDto.getHasDiesel() == false)
				gasStation.setDieselPrice(5);
			if (gasStationDto.getHasSuper() == false)
				gasStation.setSuperPrice(5);
			if (gasStationDto.getHasSuperPlus() == false)
				gasStation.setSuperPlusPrice(5);
			if (gasStationDto.getHasGas() == false)
				gasStation.setGasPrice(5);
			if (gasStationDto.getHasMethane() == false)
				gasStation.setMethanePrice(5);
		} else {	// new gas station --> insert
			GasStation gs = gasStationRepository.findByGasStationAddressAndLatAndLon(gasStationDto.getGasStationAddress(), gasStationDto.getLat(), gasStationDto.getLon());
			if (gs != null)
				return null;
			else {
				gasStation = new GasStation();
				gasStation.setDieselPrice(5);
				gasStation.setSuperPrice(5);
				gasStation.setSuperPlusPrice(5);
				gasStation.setGasPrice(5);
				gasStation.setMethanePrice(5);
			}
		}
		gasStationDto.setDieselPrice(5);
		gasStationDto.setSuperPrice(5);
		gasStationDto.setSuperPlusPrice(5);
		gasStationDto.setGasPrice(5);
		gasStationDto.setMethanePrice(5);
		gasStation.setGasStationName(gasStationDto.getGasStationName());
		gasStation.setGasStationAddress(gasStationDto.getGasStationAddress());
		gasStation.setLat(gasStationDto.getLat());
		gasStation.setLon(gasStationDto.getLon());
		gasStation.setCarSharing(gasStationDto.getCarSharing());
		gasStation.setHasDiesel(gasStationDto.getHasDiesel());
		gasStation.setHasSuper(gasStationDto.getHasSuper());
		gasStation.setHasSuperPlus(gasStationDto.getHasSuperPlus());
		gasStation.setHasGas(gasStationDto.getHasGas());
		gasStation.setHasMethane(gasStationDto.getHasMethane());
		
		if (gasStationDto.getDieselPrice() <= 0 || gasStationDto.getSuperPrice() <= 0 || gasStationDto.getSuperPlusPrice() <= 0
				|| gasStationDto.getGasPrice() <= 0 || gasStationDto.getMethanePrice() <= 0)
			throw new PriceException("Prices cannot be negative or equal to 0");
		
		GasStation gs = gasStationRepository.save(gasStation);

		return GasStationConverter.toGasStationDto(gs);
	}

	@Override
	public List<GasStationDto> getAllGasStations() {
		List<GasStation> gasStations = gasStationRepository.findAll();

		return GasStationConverter.toGasStationDto(gasStations);
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
		List<GasStation> gasStations;
		switch (gasolinetype) {
		case "Diesel":
			gasStations = gasStationRepository.findByHasDieselTrueOrderByDieselPriceAsc();
			break;
		case "Super":
			gasStations = gasStationRepository.findByHasSuperTrueOrderBySuperPriceAsc();
			break;
		case "SuperPlus":
			gasStations = gasStationRepository.findByHasSuperPlusTrueOrderBySuperPlusPriceAsc();
			break;
		case "Gas":
			gasStations = gasStationRepository.findByHasGasTrueOrderByGasPriceAsc();
			break;
		case "Methane":
			gasStations = gasStationRepository.findByHasMethaneTrueOrderByMethanePriceAsc();
			break;
		default:
			throw new InvalidGasTypeException("Invalid gasoline type");
		}
		this.updateDependabilities(gasStations);
		return GasStationConverter.toGasStationDto(gasStations);
	}

	@Override
	public List<GasStationDto> getGasStationsByProximity(double lat, double lon) throws GPSDataException {
		if (lat < -90 || lat > 90 || lon < -180 || lon > 180)
			throw new GPSDataException("Invalid GPS coordinates");	
		else {
			//values that i will need later		
			double MIN_LAT = Math.toRadians(-90d);  // -PI/2
			double MAX_LAT = Math.toRadians(90d);   //  PI/2
			double MIN_LON = Math.toRadians(-180d); // -PI
			double MAX_LON = Math.toRadians(180d);  //  PI

			//lat e lon are an in degrees, convert to radians
			double radLat = Math.toRadians(lat);
			double radLon = Math.toRadians(lon);
			
			//earth radius and max distance allowed (bound) in km
			double radius= 6371.01; 
			double bound= 1.0;
			
			//Define a square that contains the "circle of search" of radius bound to avoid doing more calculations than needed
			// (minLat, minLon) and (maxLat, maxLon) are opposite corners of a bounding rectangle.
			 
			double radDist = bound / radius; // angular distance in radians on a great circle

			double minLat = radLat - radDist;
			double maxLat = radLat + radDist;

			double minLon, maxLon;
			if (minLat > MIN_LAT && maxLat < MAX_LAT) {
				double deltaLon = Math.asin(Math.sin(radDist) /Math.cos(radLat));
				minLon = radLon - deltaLon;
				if (minLon < MIN_LON) 
					minLon += 2 * Math.PI;
				maxLon = radLon + deltaLon;
				if (maxLon > MAX_LON) 
					maxLon -= 2 * Math.PI;
			} 
			else {
				// a pole is within the distance
				minLat = Math.max(minLat, MIN_LAT);
				maxLat = Math.min(maxLat, MAX_LAT);
				minLon = MIN_LON;
				maxLon = MAX_LON;
			}
			
			//Initialise gas station array of results in the square
			List<GasStation> gasStations = gasStationRepository.findByLatBetweenAndLonBetween(Math.toDegrees(minLat), Math.toDegrees(maxLat), Math.toDegrees(minLon), Math.toDegrees(maxLon));
			Map<Double,GasStationDto> gasStationsDto = new TreeMap<Double, GasStationDto>();
			double gsradLat, gsradLon, distance;
			
			//begin search
			for(GasStation gasStation:gasStations) {
				
				//get gasStation coordinates in radians
				gsradLat=Math.toRadians(gasStation.getLat());
				gsradLon=Math.toRadians(gasStation.getLon());
				
				//calculate deltas  for lat and lon
				double dlat=gsradLat-radLat;
				double dlon=gsradLon-radLon;
				
				//calculate distance from input location to gasStation
				double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(radLat) * Math.cos(gsradLat) * Math.pow(Math.sin(dlon / 2),2);
				double b = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
				distance= radius*b;
				
				//analise distance
				if (distance<=bound) {
					this.updateDependabilities(Arrays.asList(gasStation));
					gasStationsDto.put(distance, GasStationConverter.toGasStationDto(gasStation));
				}	

			}
			ArrayList<GasStationDto> result=new ArrayList<GasStationDto>(gasStationsDto.values());
			return result;
			
			//https://stackoverflow.com/questions/19412462/getting-distance-between-two-points-based-on-latitude-longitude
			//http://janmatuschek.de/LatitudeLongitudeBoundingCoordinates	
		}
	}

	@Override
	public List<GasStationDto> getGasStationsWithCoordinates(double lat, double lon, String gasolinetype,
			String carsharing) throws InvalidGasTypeException, GPSDataException {
		List<GasStationDto> gasStations = new ArrayList<GasStationDto>();
				List<GasStationDto> gasStations1 = getGasStationsByProximity(lat, lon);
				if ((gasolinetype.compareTo("null") != 0 && carsharing.compareTo("null") != 0)) {
					List<GasStationDto> gasStations2 = getGasStationsWithoutCoordinates(gasolinetype, carsharing);
					gasStations2.retainAll(gasStations1);
					gasStations.addAll(gasStations2);
				} else if (gasolinetype.compareTo("null") != 0 && carsharing.compareTo("null") == 0) {
					List<GasStationDto> gasStations2 = getGasStationsByGasolineType(gasolinetype);
					gasStations2.retainAll(gasStations1);
					gasStations.addAll(gasStations2);
				} else if (carsharing.compareTo("null") != 0 && gasolinetype.compareTo("null") == 0) {
					List<GasStationDto> gasStations2 = getGasStationByCarSharing(carsharing);
					gasStations1.retainAll(gasStations2);
					gasStations.addAll(gasStations1);
				} else {
					gasStations.addAll(gasStations1);
				}
			
		return gasStations;
	}


	@Override
	public List<GasStationDto> getGasStationsWithoutCoordinates(String gasolinetype, String carsharing)
			throws InvalidGasTypeException {
		List<GasStationDto> gasStationDtos = new ArrayList<GasStationDto>();
		List<GasStationDto> gasStations = getGasStationsByGasolineType(gasolinetype);
		for (GasStationDto dto:gasStations) {
			if (dto.getCarSharing().compareTo(carsharing) == 0)
				gasStationDtos.add(dto);
		}
		
		return gasStationDtos;
	}

	@Override
	public void setReport(Integer gasStationId, double dieselPrice, double superPrice, double superPlusPrice,
			double gasPrice, double methanePrice, Integer userId)
			throws InvalidGasStationException, PriceException, InvalidUserException {
		if(dieselPrice==-1)
			dieselPrice=5;
		if(superPrice==-1)
			superPrice=5;
		if(superPlusPrice==-1)
			superPlusPrice=5;
		if(gasPrice==-1)
			gasPrice=5;
		if(methanePrice==-1)
			methanePrice=5;
		if(dieselPrice <= 0 || superPrice <= 0 || superPlusPrice <= 0 || gasPrice <= 0 || methanePrice <= 0)
			throw new PriceException("Price cannot be negative or null");
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
		if(!gasStations.isEmpty()) {
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
	}

	@Override
	public List<GasStationDto> getGasStationByCarSharing(String carSharing) {
		List<GasStation> gasStations;
		if (carSharing.compareTo("null") != 0) {
			gasStations = gasStationRepository.findByCarSharing(carSharing);
		} else {
			gasStations = gasStationRepository.findAll();
		}
		this.updateDependabilities(gasStations);
		return GasStationConverter.toGasStationDto(gasStations);
	}
	
}
