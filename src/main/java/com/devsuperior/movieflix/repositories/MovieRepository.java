package com.devsuperior.movieflix.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
	
	@Query(value = "SELECT obj "
			+ "FROM Movie obj "
			+ "JOIN FETCH obj.genre "
			+ "WHERE (:genreId = 0 OR obj.genre.id = :genreId) "
			+ "ORDER BY obj.title",
		   countQuery = "SELECT COUNT(obj) "
					+ "FROM Movie obj "
					+ "WHERE (:genreId = 0 OR obj.genre.id = :genreId) ")
	Page<Movie> findByGenre(Long genreId, Pageable pageable);
	
	@Query("SELECT new com.devsuperior.movieflix.dto.ReviewDTO(obj.id, obj.text, obj.movie.id, obj.user.id, obj.user.name, obj.user.email) "
			+ "FROM Review obj "
			+ "WHERE obj.movie.id = :movieId")
	List<ReviewDTO> getReview(Long movieId);

}
