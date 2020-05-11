package it.polito.ezgas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.polito.ezgas.entity.GasStation;

public interface GasStationRepository extends JpaRepository<GasStation, Integer> {
	
	GasStation findByGasStationId(Integer gasStationId);
	List<GasStation> findByLatBetweenAndLonBetween(Double myLat_inf, Double myLat_sup, Double myLon_inf, Double myLon_sup);
	List<GasStation> findByHasDieselTrue();
	List<GasStation> findByHasSuperTrue();
	List<GasStation> findByHasSuperPlusTrue();
	List<GasStation> findByHasGasTrue();
	List<GasStation> findByHasMethaneTrue();
	List<GasStation> findByCarSharing(String carSharing);
	
}
