package com.example.backendanunciosmobiles.repositories;

import com.example.backendanunciosmobiles.models.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
}
