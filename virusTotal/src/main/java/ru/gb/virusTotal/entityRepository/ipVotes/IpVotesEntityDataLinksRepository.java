package ru.gb.virusTotal.entityRepository.ipVotes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.virusTotal.entity.ipVotes.IpVotesEntityDataLinks;

@Repository
public interface IpVotesEntityDataLinksRepository extends JpaRepository<IpVotesEntityDataLinks, Long> {
}
