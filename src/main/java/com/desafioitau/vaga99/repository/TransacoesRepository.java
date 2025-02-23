package com.desafioitau.vaga99.repository;

import com.desafioitau.vaga99.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransacoesRepository extends JpaRepository<Transacao, UUID>{}
