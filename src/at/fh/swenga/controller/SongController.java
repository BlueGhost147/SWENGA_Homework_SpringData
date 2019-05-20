package at.fh.swenga.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.dao.ArtistRepository;
import at.fh.swenga.dao.SongRepository;
import at.fh.swenga.model.ArtistModel;
import at.fh.swenga.model.SongModel;

@Controller
public class SongController {

	@Autowired
	SongRepository songRepository;
	
	@Autowired
	ArtistRepository artistRepository;

	@RequestMapping(value = { "/", "list" })
	public String index(Model model) {
		List<SongModel> songs = songRepository.findAll();
		model.addAttribute("songs", songs);
		model.addAttribute("count", songs.size());
		return "index";
	}
	
	@RequestMapping(value = { "/getPage" })
	public String getPage(Pageable page,Model model) {
		Page<SongModel> songsPage = songRepository.findAll(page);
		model.addAttribute("songsPage", songsPage);
		model.addAttribute("songs", songsPage.getContent());
		model.addAttribute("count", songsPage.getTotalElements());
		return "index";
	}

	@RequestMapping(value = { "/find" })
	public String find(Model model, @RequestParam String searchString, @RequestParam String searchType) {
		List<SongModel> songs = null;
		int count=0;

		switch (searchType) {
		case "query_allSongs":
			songs = songRepository.findAll();
			break;
		case "query_filterSongsByName":
			songs = songRepository.findBySongName(searchString);
			break;
		case "query_filterSongsByAlbum":
			songs = songRepository.findByAlbum(searchString);
			break;
		case "query_filterSongsBySongNameOrAlbum":
			songs = songRepository.findBySongNameOrAlbum(searchString,searchString);
			break;
		case "query_searchInSongNameAlbumArtistLike":
			songs = songRepository.searchInSongNameAlbumArtistLike("%"+searchString+"%");
			break;
		case "query_filterSongsByArtist":
			songs = songRepository.filterSongsByArtist(searchString);
			break;	
		case "query_filterSongsByAlbumSortedASC":
			songs = songRepository.filterSongsByAlbumSortedASC(searchString);
			break;	
		case "query_filterSongsBySongNameTop5":
			songs = songRepository.findTop5BySongName(searchString);
			break;	
		case "query_countAlbumsByArtist":
			count = songRepository.countAlbumsByArtist(searchString);
			break;	
		case "query_countArtists":
			count = songRepository.countArtists();
			break;	
		default:
			songs = songRepository.findAll();
		}
		
		model.addAttribute("songs", songs);

		if (songs!=null) {
			model.addAttribute("count", songs.size());			
		}
		else {
			model.addAttribute("count", count);				
		}
		return "index";
	}

	@RequestMapping(value = { "/findById" })
	public String findById(@RequestParam("id") SongModel e, Model model) {
		if (e!=null) {
			List<SongModel> songs = new ArrayList<SongModel>();
			songs.add(e);
			model.addAttribute("songs", songs);
		}
		return "index";
	}



	
	@RequestMapping("/fillSongList")
	@Transactional
	public String fillData(Model model) {

		DataFactory df = new DataFactory();
		
		ArtistModel artist = null;
		
		for(int i=0;i<100;i++) {
			if (i%7==0) {
				String artistName=df.getFirstName() + " " + df.getLastName();
				artist=artistRepository.findFirstByName(artistName);
				if (artist==null) {
					artist = new ArtistModel(artistName);
				}
			}
			
			SongModel songModel = new SongModel(
					df.getRandomWord(),
					artist,
					df.getRandomWord(),
					df.getBirthDate());
			songModel.setArtist(artist);
			songRepository.save(songModel);
		}
	
		return "forward:list";
	}

	@RequestMapping("/delete")
	public String deleteData(Model model, @RequestParam int id) {
		songRepository.deleteById(id);

		return "forward:list";
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "error";

	}
}
