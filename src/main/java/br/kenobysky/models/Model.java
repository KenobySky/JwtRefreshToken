package br.kenobysky.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Shadows
 */
@MappedSuperclass
@EqualsAndHashCode
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    @Getter
    @Setter
    private Long id;


    
    public Model() {

    }

    
    @PrePersist
    public void prePersist(){
        
    }
    
    @PostPersist
    public void postPersist(){
        
    }
    
    @PreRemove
    public void preRemove(){
        
    }
    
    @PostRemove
    public void postRemove(){
        
    }
    
    @PreUpdate
    public void preUpdate(){
        
    }
    
    @PostUpdate
    public void postUpdate(){
        
    }
    
    @PostLoad
    public void postLoad(){
       
    }
    
    
}
