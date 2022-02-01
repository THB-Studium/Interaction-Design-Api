package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.services.HighlightService;
import com.team.angular.interactiondesignapi.transfertobjects.hightlight.HighlightReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.hightlight.HighlightReadReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.hightlight.HighlightReadWriteTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/highlights")
@CrossOrigin(origins = "*")
public class HighlightController {
    @Autowired
    protected HighlightService highlightService;

    @ApiOperation("Get All Highlight")
    @GetMapping("")
    public List<HighlightReadListTO> getAllHighlight() {
        return highlightService.getAll();
    }

    @ApiOperation("Get One Highlight")
    @GetMapping("/{id}")
    public HighlightReadReadTO getHighlightById(
            @ApiParam(name = "HighlightId", value = "ID of the Highlight") @PathVariable UUID id) {
        return highlightService.getHighlight(id);
    }

    @ApiOperation("Add One Highlight")
    @PostMapping("")
    public HighlightReadReadTO addHighlight(
            @ApiParam(name = "Highlight", value = "Highlight to add") @RequestBody HighlightReadWriteTO highlight) throws Exception {
        return highlightService.addHighlight(highlight);
    }

    @ApiOperation("Update Highlight")
    @PutMapping("")
    public HighlightReadReadTO updateHighlight(
            @ApiParam(name = "Highlight", value = "Highlight to update") @RequestBody HighlightReadWriteTO highlight) throws Exception {
        return highlightService.updateHighlight(highlight);
    }

    @ApiOperation("Delete Highlight")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> daleteHighlight(
            @ApiParam(name = "HighlightId", value = "Id of the Highlight") @PathVariable UUID id) {
        return highlightService.deleteHighlight(id);
    }

}
