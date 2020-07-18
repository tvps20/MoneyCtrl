package com.santiago.moneyctrl.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.santiago.moneyctrl.domain.Cartao;
import com.santiago.moneyctrl.domain.Comprador;
import com.santiago.moneyctrl.domain.Cota;
import com.santiago.moneyctrl.domain.enuns.TipoStatus;
import com.santiago.moneyctrl.dtos.CompradorDTO;
import com.santiago.moneyctrl.dtos.CotaCompradorDTO;
import com.santiago.moneyctrl.dtos.CotaDTO;
import com.santiago.moneyctrl.dtos.LancamentoDTO;
import com.santiago.moneyctrl.repositories.CompradorRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CompradorService extends BaseService<Comprador, CompradorDTO> {

	public CompradorService(CompradorRepository repository) {
		super(repository);
		this.entityName = "Comprador";
		BaseService.baseLog = CompradorService.log;
	}
	
	public List<CotaCompradorDTO> GerarCotasByIdComprador(Long idComprador){
		log.info("[GerarCotasById] - Buscando comprador. CompradorId: " + idComprador);
		Comprador comprador = this.findById(idComprador);
		Map<Cartao, CotaCompradorDTO> mapCotas = new HashMap<>();
		
		log.info("[GerarCotasById] - Gerando compras");
		
		for (Cota cota : comprador.getLancamentos()) {
			if(cota.getLancamento().getFatura().getStatus() != TipoStatus.PAGA) {
				if (!mapCotas.keySet().contains(cota.getLancamento().getFatura().getCartao())) {
					// Se não encontrar criar um item novo.
					CotaCompradorDTO contaComprador = new CotaCompradorDTO(cota.getLancamento().getFatura().getCartao());
					mapCotas.put(cota.getLancamento().getFatura().getCartao(), contaComprador);
				}
				CotaDTO dto = new CotaDTO(cota.getId(), cota.getValor(), null);
				dto.setLancamento(new LancamentoDTO(cota.getLancamento()));
				
				mapCotas.get(cota.getLancamento().getFatura().getCartao()).getCotas().add(dto);
			}
		}	
		
		log.info("[GerarCotasById] - Compras geradas com sucesso.");
		return new ArrayList<CotaCompradorDTO>(mapCotas.values());
	}

	@Override
	public Comprador fromDTO(CompradorDTO dto) {
		log.info("[Mapping] - 'CompradorDTO' to 'Comprador'. Id: " + dto.getId());
		Comprador comprador = new Comprador(dto.getId(), dto.getNome(), dto.getUsername(), dto.getPassword());
		comprador.setSobrenome(dto.getSobrenome());
		comprador.setEmail(dto.getEmail());
		
		log.info("[Mapping] - Mapping finalizado com sucesso.");
		return comprador;
	}

	@Override
	public void updateData(Comprador newObj, Comprador obj) {
		log.info("[Parse] - 'comprador' from 'newComprador'. Id: " + newObj.getId());
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		newObj.setSobrenome(obj.getSobrenome());
		newObj.setPassword(obj.getPassword());
		log.info("[Parse] - Parse finalizado com sucesso.");
	}
}
