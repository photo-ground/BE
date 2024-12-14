package ceos.phototoground.photographer.controller;


import ceos.phototoground.photographer.dto.PhotographerBottomDTO;
import ceos.phototoground.photographer.dto.PhotographerIntroDTO;
import ceos.phototoground.photographer.dto.PhotographerListDTO;
import ceos.phototoground.photographer.dto.PhotographerSearchListDTO;
import ceos.phototoground.photographer.service.PhotographerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photographer")
public class PhotographerController {

    private final PhotographerService photographerService;

    // 작가 리스트 조회 (필터링 포함)
    @GetMapping
    public ResponseEntity<PhotographerListDTO> getPhotographerList(
            @RequestParam(value = "cursor", required = false) Long cursor,
            @RequestParam(value = "size", defaultValue = "15", required = false) int size,
            @RequestParam(value = "univ", required = false) String univ,
            @RequestParam(value = "gender", required = false) String gender) {

        PhotographerListDTO dto = photographerService.getPhotographerList(cursor, size, univ, gender);

        return ResponseEntity.ok(dto);
    }

    // 작가 검색
    @GetMapping("/search")
    public ResponseEntity<PhotographerSearchListDTO> searchPhotographer(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "cursor", required = false) String cursor,
            @RequestParam(value = "size", defaultValue = "15", required = false) int size) {

        PhotographerSearchListDTO dto = photographerService.searchPhotographer(name, cursor, size);

        return ResponseEntity.ok(dto);

    }

    // 특정 작가 상단부 조회
    @GetMapping("/{photographerId}/intro")
    public ResponseEntity<PhotographerIntroDTO> getPhotographerIntro(@PathVariable Long photographerId) {

        PhotographerIntroDTO dto = photographerService.getPhotographerIntro(photographerId);

        return ResponseEntity.ok(dto);
    }

    // 특정 작가 하단부 조회
    @GetMapping("/{photographerId}/bottom")
    public ResponseEntity<PhotographerBottomDTO> getPhotographerBottom(@PathVariable Long photographerId,
                                                                       @RequestParam(value = "cursor", required = false) Long cursor,
                                                                       @RequestParam(value = "size", defaultValue = "5", required = false) int size) {

        PhotographerBottomDTO dto = photographerService.getPhotographerBottom(photographerId, cursor, size);

        return ResponseEntity.ok(dto);
    }
}
