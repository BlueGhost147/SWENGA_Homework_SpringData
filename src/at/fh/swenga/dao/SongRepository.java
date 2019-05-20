package at.fh.swenga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.model.SongModel;

@Repository
@Transactional
public interface SongRepository extends JpaRepository<SongModel, Integer> {
	
	public List<SongModel> findAll();
	
	// findByField
	public List<SongModel> findBySongName(String songName);
	
	// findByField
	public List<SongModel> findByAlbum(String album);
	
	// findByFieldOrField
	public List<SongModel> findBySongNameOrAlbum(String songName, String album);
	
	// find with @Query
	@Query("SELECT s "
			+ "FROM SongModel s "
			+ "JOIN s.artist a "
			+ "WHERE lower(a.name) = lower(:searchString)")
	public List<SongModel> filterSongsByArtist(@Param("searchString") String searchString);

	
	// with @NamedQuery
	public List<SongModel> searchInSongNameAlbumArtistLike(@Param("searchString") String searchString);
	
	// Sorting
	@Query("SELECT s "
			+ "FROM SongModel s "
			+ "WHERE lower(s.album) = lower(:searchString) "
			+ "ORDER BY s.songName ASC")
	public List<SongModel> filterSongsByAlbumSortedASC(@Param("searchString") String searchString);
	
	// Limiting Query results
	public List<SongModel> findTop5BySongName(String songName);
	
	// other - Count albums from artist
	@Query("SELECT COUNT(DISTINCT s.album) "
			+ "FROM SongModel s "
			+ "JOIN s.artist a "
			+ "WHERE lower(a.name) = lower(:searchString)")
	public int countAlbumsByArtist(@Param("searchString") String searchString);
	
	// other - Count albums from artist
	@Query("SELECT COUNT(a) FROM ArtistModel a ")
	public int countArtists();
	
}
