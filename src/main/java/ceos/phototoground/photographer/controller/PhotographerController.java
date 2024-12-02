package ceos.phototoground.photographer.controller;


import ceos.phototoground.photographer.dto.PhotographerListDTO;
import ceos.phototoground.photographer.service.PhotographerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photographer")
public class PhotographerController {

    private final PhotographerService photographerService;

    @GetMapping
    public ResponseEntity<PhotographerListDTO> getPhotographerList(
            @RequestParam(value = "cursor", required = false) Long cursor,
            @RequestParam(value = "size", defaultValue = "15", required = false) int size,
            @RequestParam(value = "univ", required = false) String univ,
            @RequestParam(value = "gender", required = false) String gender) {

        PhotographerListDTO dto = photographerService.getPhotographerList(cursor, size, univ, gender);

        return ResponseEntity.ok(dto);
    }

}
