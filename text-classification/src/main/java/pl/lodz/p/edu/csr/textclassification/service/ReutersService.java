package pl.lodz.p.edu.csr.textclassification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.edu.csr.textclassification.repository.ReutersRepository;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;

import java.util.List;

@Service
public class ReutersService {

    private ReutersRepository reutersRepository;

    @Autowired
    public ReutersService(ReutersRepository reutersRepository) {
        this.reutersRepository = reutersRepository;
    }

    List<ReutersEntity> getAllReuters() {
        return reutersRepository.findAll();
    }
}
