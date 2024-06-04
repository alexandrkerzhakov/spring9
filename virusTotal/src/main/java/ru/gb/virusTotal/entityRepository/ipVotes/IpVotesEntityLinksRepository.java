package ru.gb.virusTotal.entityRepository.ipVotes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.virusTotal.entity.ipVotes.IpVotesEntityLinks;

@Repository
public interface IpVotesEntityLinksRepository extends JpaRepository<IpVotesEntityLinks, Long> {
}
