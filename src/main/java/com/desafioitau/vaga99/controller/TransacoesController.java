package com.desafioitau.vaga99.controller;

import com.desafioitau.vaga99.model.EstatisticaDTO;
import com.desafioitau.vaga99.model.TransacaoDTO;
import com.desafioitau.vaga99.service.TransacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(name = "/")
public class TransacoesController {
    @Autowired
    TransacoesService transacoesService;

    @PostMapping("/transacao")
    public ResponseEntity<?> transacaoAdd(@RequestBody TransacaoDTO transacaoDTO) {
        try {
            if(!transacoesService.adicionaTransacao(transacaoDTO)){
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping ("/transacao")
    public ResponseEntity<Void> transacaoDelete() {
        try {
            if (!transacoesService.deleteTransacoes()) {
                return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
            }
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping ("/estatistica")
    public ResponseEntity<EstatisticaDTO> getEstatistica(){
        EstatisticaDTO estatisticaDTO = transacoesService.getEstatisticaTransacoes();
        System.out.println(estatisticaDTO);
        return ResponseEntity.status(HttpStatus.OK).body(estatisticaDTO);
    }
}
