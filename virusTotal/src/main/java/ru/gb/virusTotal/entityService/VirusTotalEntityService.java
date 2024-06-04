package ru.gb.virusTotal.entityService;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.virusTotal.entity.ipVotes.IpVotesEntity;
import ru.gb.virusTotal.entityRepository.ipVotes.*;

import java.util.List;

@Service
@Data
public class VirusTotalEntityService {
    @Autowired
    private IpVotesEntityRepository ipVotesEntityRepository;

    public void save(IpVotesEntity ipVotesEntity) {
        ipVotesEntityRepository.save(ipVotesEntity);
    }

    public List<IpVotesEntity> findAll() {
        return ipVotesEntityRepository.findAll();
    }

    public void deleteById(Long id) {
        ipVotesEntityRepository.deleteById(id);
    }
}
