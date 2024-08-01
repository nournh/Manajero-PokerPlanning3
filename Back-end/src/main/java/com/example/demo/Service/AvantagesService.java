package com.example.demo.Service;




import com.example.demo.Entity.Avantages;
import com.example.demo.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvantagesService {

    @Autowired
    private AvantagesRepository AvantagesRepository;

    public List<Avantages> getAllAvantages() {
        return AvantagesRepository.findAll();
    }

    public Optional<Avantages> getAvantagesById(String id) {
        return AvantagesRepository.findById(id);
    }

    public Avantages createAvantages(Avantages avantages) {
        return AvantagesRepository.save(avantages);
    }

    public Optional<Avantages> updateAvantages(String id, Avantages  avantages) {
        Optional<Avantages> AvantagesData = AvantagesRepository.findById(id);

        if (AvantagesData.isPresent()) {
            Avantages _avantages = AvantagesData.get();
            _avantages.setDescription(avantages.getDescription());
            _avantages.setPublished(avantages.isPublished());
            return Optional.of(AvantagesRepository.save(_avantages));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteAvantages(String id) {
        try {
            AvantagesRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
