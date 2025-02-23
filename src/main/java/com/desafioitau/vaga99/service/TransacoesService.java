package com.desafioitau.vaga99.service;

import com.desafioitau.vaga99.controller.TransacoesController;
import com.desafioitau.vaga99.model.EstatisticaDTO;
import com.desafioitau.vaga99.model.Transacao;
import com.desafioitau.vaga99.model.TransacaoDTO;
import com.desafioitau.vaga99.repository.TransacoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class TransacoesService {
    @Autowired
    TransacoesRepository transacoesRepository;

    public boolean validaTransacoes(Double valor, OffsetDateTime dataHora){
        OffsetDateTime hoje = OffsetDateTime.now(ZoneOffset.UTC);
        return valor != null && dataHora != null && dataHora.isBefore(hoje) && valor >= 0.0;
    }

    public boolean deleteTransacoes() {
        transacoesRepository.deleteAll();
        return transacoesRepository.findAll().isEmpty();
    }

    public EstatisticaDTO getEstatisticaTransacoes() {
        OffsetDateTime horaInicial = OffsetDateTime.now(ZoneOffset.UTC).minusSeconds(60);
        //OffsetDateTime horaInicial2 = OffsetDateTime.parse("2025-02-22T23:51:51.900855400Z").minusSeconds(60);
        List<Transacao> transacoesFiltradas = transacoesRepository.findAll().stream().filter(transacao -> transacao.getDataHora().isAfter(horaInicial)).toList();
        int count = transacoesFiltradas.size();
        double sum = transacoesFiltradas.stream()
                .mapToDouble(transacao -> transacao.getValor().doubleValue())
                .sum();
        double avg = count > 0 ? sum / count : 0.0;
        double min = transacoesFiltradas.stream()
                .mapToDouble(Transacao :: getValor)
                .min()
                .orElse(0.0);
        double max = transacoesFiltradas.stream()
                .mapToDouble(Transacao :: getValor)
                .max()
                .orElse(0.00);
        return new EstatisticaDTO(count, sum, avg, min, max);
    }

    public boolean adicionaTransacao(TransacaoDTO transacaoDTO) {
        if (!validaTransacoes(transacaoDTO.valor(), transacaoDTO.dataHora())){
            return false;
        }
        Transacao transacao = new Transacao();
        transacao.setValor(transacaoDTO.valor());
        transacao.setDataHora(transacaoDTO.dataHora());

        transacoesRepository.save(transacao);

        return transacoesRepository.findById(transacao.getId()).isPresent();
    }
}
