package at.fh.swenga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.model.ArtistModel;


@Repository
public interface ArtistRepository extends JpaRepository<ArtistModel, Integer> {

	@Transactional
	List<ArtistModel> findAll();
	
	@Transactional
	ArtistModel findFirstByName(String artistName);

}
