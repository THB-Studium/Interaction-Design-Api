package com.team.angular.interactiondesignapi.services;

import static com.team.angular.interactiondesignapi.config.CompressImage.compressBild;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.team.angular.interactiondesignapi.exception.ApiRequestException;
import com.team.angular.interactiondesignapi.models.Highlight;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.repositories.HighlightRepository;
import com.team.angular.interactiondesignapi.repositories.LandRepository;
import com.team.angular.interactiondesignapi.transfertobjects.hightlight.Highlight2HighlightReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.hightlight.Highlight2HighlightReadWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.hightlight.HighlightReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.hightlight.HighlightReadReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.hightlight.HighlightReadWriteTO;

@Service
public class HighlightService {
	private static final Logger log = LoggerFactory.getLogger(HighlightService.class);
	@Autowired
	private HighlightRepository highlightRepository;
	@Autowired
	private LandRepository landRepository;

	public HighlightReadReadTO getHighlight(UUID id) {
		Highlight highlight = highlightRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException("Cannot find Highlight with id: " + id));
		return Highlight2HighlightReadWriteTO.apply(highlight);
	}

	public List<HighlightReadListTO> getAll(Integer pageNo, Integer pageSize, String sortBy) {

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<Highlight> pagedResult = highlightRepository.findAll(paging);

		return Highlight2HighlightReadListTO.apply(pagedResult.getContent());
	}

	public HighlightReadReadTO addHighlight(HighlightReadWriteTO highlight) {

		Highlight _highlight = new Highlight();

		if (!highlightRepository.existsHighlightByName(highlight.getName())) {
			_highlight.setName(highlight.getName());
		} else {
			throw new ApiRequestException(highlight.getName() + " already exists");
		}

		_highlight.setDescription(highlight.getDescription() != null ? highlight.getDescription() : null);
		if (highlight.getBild() != null)
			_highlight.setBild(compressBild(highlight.getBild()));
		if (highlight.getLandId() != null) {
			Land land = landRepository.findById(highlight.getLandId())
					.orElseThrow(() -> new ApiRequestException("Cannot find Land with id: " + highlight.getLandId()));
			_highlight.setLand(land);
		}
		return Highlight2HighlightReadWriteTO.apply(highlightRepository.save(_highlight));
	}

	public ResponseEntity<?> deleteHighlight(UUID id) {
		Highlight actual = highlightRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException("Cannot find Highlight with id: " + id));

		highlightRepository.deleteById(actual.getId());
		log.info("Highlight successfully deleted");

		return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
	}

	public HighlightReadReadTO updateHighlight(HighlightReadWriteTO highlight) {

		Highlight _highlight = highlightRepository.findById(highlight.getId()).orElseThrow(
				() -> new ApiRequestException("Update Error: Cannot find Highlight with id: " + highlight.getId()));

		if (highlight.getName() != null && !_highlight.getName().equals(highlight.getName())) {
			if (!highlightRepository.existsHighlightByName(highlight.getName())) {
				_highlight.setName(highlight.getName());
			} else {
				throw new ApiRequestException(highlight.getName() + " already exists");
			}
		}

		_highlight.setDescription(highlight.getDescription() != null ? highlight.getDescription() : null);
		if (highlight.getBild() != null)
			_highlight.setBild(compressBild(highlight.getBild()));

		return Highlight2HighlightReadWriteTO.apply(highlightRepository.save(_highlight));
	}
}
