package pl.lodz.p.edu.csr.textclassification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.edu.csr.textclassification.repository.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.repository.ReutersRepository;

import java.util.List;

@Service
public class ReutersService {

    @Autowired
    ReutersRepository reutersRepository;

    List<ReutersEntity> getAllReuters(){
        return reutersRepository.findAll();
    }
}
