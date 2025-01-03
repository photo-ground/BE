package ceos.phototoground.domain.spot.controller;

import ceos.phototoground.domain.spot.dto.OneSpotInfo;
import ceos.phototoground.domain.spot.dto.SpotInfo;
import ceos.phototoground.domain.spot.service.SpotService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spot")
public class SpotController {

    private final SpotService spotService;

    @GetMapping
    public ResponseEntity<List<SpotInfo>> showSpotInfoList(@RequestParam(value = "univ") String univ) {

        List<SpotInfo> infoList = spotService.showSpotInfoList(univ);
        return ResponseEntity.ok(infoList);
    }


    @GetMapping("/{spotId}")
    public ResponseEntity<OneSpotInfo> showOneSpotInfo(@PathVariable("spotId") Long spotId,
                                                       @RequestParam(value = "cursor", required = false) Long cursor,
                                                       @RequestParam(value = "size", defaultValue = "15", required = false) int size) {

        OneSpotInfo info = spotService.showOneSpotInfo(spotId, cursor, size);
        return ResponseEntity.ok(info);
    }

}
