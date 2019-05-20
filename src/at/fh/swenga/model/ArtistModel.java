package at.fh.swenga.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "Artist")
public class ArtistModel {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 30)
	private String name;
	
	@OneToMany(mappedBy="artist",fetch=FetchType.LAZY)
    @OrderBy("songName")
    private List<SongModel> songs;

	@Version
	long version;

	public ArtistModel() {
		super();
	}
	
	public ArtistModel(String name) {
		super();
		this.name = name;
	}
	
	public ArtistModel(int id, String name, List<SongModel> songs, long version) {
		super();
		this.id = id;
		this.name = name;
		this.songs = songs;
		this.version = version;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addSongs(SongModel song) {
		if(songs == null) 
			songs = new ArrayList<SongModel>();
		this.songs.add(song);
	}

	public List<SongModel> getSongs() {
		if(songs != null)
			return songs;
		return new ArrayList<SongModel>();
	}

	public void setSongs(List<SongModel> songs) {
		this.songs = songs;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArtistModel other = (ArtistModel) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
