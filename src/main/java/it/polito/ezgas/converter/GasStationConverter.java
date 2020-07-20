package it.polito.ezgas.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.GasStation;

@Component
public class GasStationConverter {
		
	public static GasStationDto toGasStationDto(GasStation gasStation) {
		
		GasStationDto gasStationDto = new GasStationDto();
		gasStationDto.setGasStationId(gasStation.getGasStationId());
		gasStationDto.setGasStationName(gasStation.getGasStationName());
		gasStationDto.setGasStationAddress(gasStation.getGasStationAddress());
		gasStationDto.setHasDiesel(gasStation.getHasDiesel());
		gasStationDto.setHasSuper(gasStation.getHasSuper());
		gasStationDto.setHasSuperPlus(gasStation.getHasSuperPlus());
		gasStationDto.setHasGas(gasStation.getHasGas());
		gasStationDto.setHasMethane(gasStation.getHasMethane());
		gasStationDto.setHasPremiumDiesel(gasStation.getHasPremiumDiesel());
		gasStationDto.setCarSharing(gasStation.getCarSharing());
		gasStationDto.setLat(gasStation.getLat());
		gasStationDto.setLon(gasStation.getLon());
		gasStationDto.setDieselPrice(gasStation.getDieselPrice());
		gasStationDto.setSuperPrice(gasStation.getSuperPrice());
		gasStationDto.setSuperPlusPrice(gasStation.getSuperPlusPrice());
		gasStationDto.setGasPrice(gasStation.getGasPrice());
		gasStationDto.setMethanePrice(gasStation.getMethanePrice());
		gasStationDto.setPremiumDieselPrice(gasStation.getPremiumDieselPrice());
		gasStationDto.setReportUser(gasStation.getReportUser());
		gasStationDto.setReportTimestamp(gasStation.getReportTimestamp());
		gasStationDto.setReportDependability(gasStation.getReportDependability());
		
		if (gasStation.getUser() != null) {
			UserDto userDto = UserConverter.toUserDto(gasStation.getUser());
			gasStationDto.setUserDto(userDto);
		}
		
		return gasStationDto;
	}
	
	public static List<GasStationDto> toGasStationDto(List<GasStation> gasStations) {
		List<GasStationDto> gasStationsDto = new ArrayList<GasStationDto>();
		for(GasStation gasStation:gasStations)
			gasStationsDto.add(GasStationConverter.toGasStationDto(gasStation));
		
		return gasStationsDto;
	}
}
