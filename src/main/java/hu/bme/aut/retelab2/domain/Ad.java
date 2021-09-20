package hu.bme.aut.retelab2.domain;

import hu.bme.aut.retelab2.helpers.SecretGenerator;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Ad {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String title;
    private String description;
    @NotNull
    private Integer price;
    @ElementCollection
    private Set<String> tags;
    @CreationTimestamp
    private LocalDateTime createdDate;
    private LocalDateTime expiration;
    private String key;
    //Getters:
    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public Integer getPrice() {
        return price;
    }
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    public String getKey() {
        return key;
    }
    public Set<String> getTags() {
        return tags;
    }
    public LocalDateTime getExpiration() {
        return expiration;
    }
    // Setters:
    public void setId(Long id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }
    // Methods:
    public void addKey() {
        this.key = SecretGenerator.generate();
    }
    public void nullKey() {
        this.key = null;
    }
}
