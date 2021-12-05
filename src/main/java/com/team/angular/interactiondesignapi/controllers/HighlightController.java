package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.models.Highlight;
import com.team.angular.interactiondesignapi.services.HighlightService;
import com.team.angular.interactiondesignapi.transfertobjects.hightlights.HighlightReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.hightlights.HighlightReadTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

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
    public HighlightReadTO getHighlightById(
            @ApiParam(name = "HighlightId", value = "get One Highlight") @PathVariable UUID id) {
        return highlightService.getHighlight(id);
    }

    @ApiOperation("Add One Highlight")
    @PostMapping("")
    public Highlight addHighlight(
            @ApiParam(name = "Highlight", value = "Highlight to add") @RequestPart(value = "highlight")
                    HighlightReadTO highlight, @RequestPart(value = "bild") MultipartFile bild) {
        return highlightService.addHighlight(highlight, bild);
    }

    @ApiOperation("Update Highlight")
    @PutMapping("")
    public Highlight updateHighlight(@ApiParam(name = "Highlight", value = "Highlight to add") @RequestPart(value = "highlight")
                                             HighlightReadTO highlight, @RequestPart(value = "bild") MultipartFile bild) {
        return highlightService.updateHighlight(highlight, bild);
    }

    @ApiOperation("Delete Highlight")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> daleteHighlight(
            @ApiParam(name = "HighlightId", value = "Id of the Highlight") @PathVariable UUID id) {
        return highlightService.deleteHighlight(id);
    }

}
