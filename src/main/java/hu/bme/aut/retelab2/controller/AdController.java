package hu.bme.aut.retelab2.controller;

import hu.bme.aut.retelab2.domain.Ad;
import hu.bme.aut.retelab2.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ads")
public class AdController {
    @Autowired
    private AdRepository adRepository;
    @PostMapping
    public Ad create(@RequestBody Ad ad) {
        ad.setId(null);
        ad.addKey();
        return adRepository.save(ad);
    }
    @GetMapping
    public List<Ad> getBetween(@RequestParam(required = false, defaultValue = "0") Integer minimumPrice, @RequestParam(required = false, defaultValue = "10000000") Integer maximumPrice) {
        List<Ad> ads = adRepository.getBetween(minimumPrice, maximumPrice);
        for (Ad ad : ads)
        {
            ad.nullKey();
        }
        return ads;
    }
    @PutMapping
    public ResponseEntity<Ad> update(@RequestBody Ad ad) {
        ResponseEntity<Ad> newAd = null;
        try {
            return ResponseEntity.ok(adRepository.updateAd(ad));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }
    }
    @GetMapping("{tag}")
    public List<Ad> getByTag(@PathVariable String tag){
        return adRepository.getByTag(tag);
    }
}
