package com.santiago.moneyctrl.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santiago.moneyctrl.domain.Cartao;
import com.santiago.moneyctrl.domain.Comprador;
import com.santiago.moneyctrl.domain.Divida;
import com.santiago.moneyctrl.domain.Fatura;
import com.santiago.moneyctrl.domain.Lancamento;
import com.santiago.moneyctrl.domain.enuns.TipoMes;
import com.santiago.moneyctrl.domain.enuns.TipoStatus;
import com.santiago.moneyctrl.dtos.CotaDTO;
import com.santiago.moneyctrl.dtos.CotaFaturaDTO;
import com.santiago.moneyctrl.dtos.FaturaDTO;
import com.santiago.moneyctrl.dtos.LancamentoDTO;
import com.santiago.moneyctrl.repositories.FaturaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FaturaService extends BaseService<Fatura, FaturaDTO> {

	@Autowired
	private CartaoService cartaoService;

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private DividaService dividaService;

	public FaturaService(FaturaRepository repository) {
		super(repository);
		this.entityName = "Fatura";
		BaseService.baseLog = FaturaService.log;
	}

	@Override
	public Fatura insert(Fatura entity) {
		log.info("[InsertFatura] - Salvando uma nova fatura.");

		this.cartaoService.findById(entity.getCartao().getId());
		Fatura fatura = super.insert(entity);

		log.info("[InsertFatura] - Fatura salva no bando de dados.");
		return fatura;
	}

	public Page<Fatura> findPageByStatus(String status, Integer page, Integer linesPerPage, String direction,
			String orderBy) {
		log.info("[FindPageStatus] - Buscando paginado todas as faturas por status: { status: " + status + ", Page: "
				+ page + ", linesPerPage: " + linesPerPage + " }");

		Direction directionParse = direction.equals("ASC") ? Direction.ASC : Direction.DESC;
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, directionParse, orderBy);
		TipoStatus statusType = TipoStatus.toEnum(status);
		Page<Fatura> faturas = ((FaturaRepository) this.repository).findByStatus(statusType, pageRequest);

		log.info("[FindPageStatus] - Busca paginada finalizada com sucesso.");
		return faturas;
	}

	public Page<Fatura> noFindPageByStatus(String status, Integer page, Integer linesPerPage, String direction,
			String orderBy) {
		log.info("[FindPageStatus] - Buscando paginado todas as faturas sem status: { status: " + status + ", Page: "
				+ page + ", linesPerPage: " + linesPerPage + " }");

		Direction directionParse = direction.equals("ASC") ? Direction.ASC : Direction.DESC;
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, directionParse, orderBy);
		TipoStatus statusType = TipoStatus.toEnum(status);
		Page<Fatura> faturas = ((FaturaRepository) this.repository).noFindByStatus(statusType, pageRequest);

		log.info("[FindPageStatus] - Busca paginada finalizada com sucesso.");
		return faturas;
	}

	public List<Fatura> noFindPageByStatus(String status) {
		log.info("[FindAllStatus] - Buscando paginado todas as faturas sem status. Status: " + status);
		TipoStatus statusType = TipoStatus.toEnum(status);
		List<Fatura> list = ((FaturaRepository) this.repository).noFindByStatus(statusType);

		log.info("[FindAllStatus] - Busca finalizada com sucesso.");
		return list;
	}

	public List<Fatura> findPageByStatus(String status) {
		log.info("[FindAllStatus] - Buscando paginado todas as faturas por status. Status: " + status);
		TipoStatus statusType = TipoStatus.toEnum(status);
		List<Fatura> list = ((FaturaRepository) this.repository).findByStatus(statusType);

		log.info("[FindAllStatus] - Busca finalizada com sucesso.");
		return list;
	}

	public List<CotaFaturaDTO> gerarCotasByIdFatura(Long faturaId) {
		log.info("[GerarCotasById] - Gerando cotas.");
		Fatura fatura = this.findById(faturaId);
		Map<Comprador, CotaFaturaDTO> mapCotas = new HashMap<>();

		for (Lancamento lancamento : fatura.getLancamentos()) {
			lancamento.getCotas().stream().forEach(c -> {
				if (!mapCotas.keySet().contains(c.getComprador())) {
					// Se não encontrar criar um item novo.
					CotaFaturaDTO cotaFatura = new CotaFaturaDTO(c.getComprador());
					mapCotas.put(c.getComprador(), cotaFatura);
				}
				CotaDTO dto = new CotaDTO(c.getId(), c.getValor(), null);
				dto.setLancamento(new LancamentoDTO(c.getLancamento()));

				mapCotas.get(c.getComprador()).getCotas().add(dto);
			});
		}

		log.info("[GerarCotasById] - Cotas geradas com sucesso.");
		return new ArrayList<CotaFaturaDTO>(mapCotas.values());
	}

	@Transactional
	public Fatura fecharFatura(Long id) {
		log.info("[Fechar-Fatura] - Fechando fatura.");
		Fatura fatura = this.findById(id);
		Fatura proximaFatura = this.gerarProximaFatura(fatura);
		List<CotaFaturaDTO> cotasFatura = this.gerarCotasByIdFatura(id);
		List<Divida> dividas = this.dividaService.gerarDividas(cotasFatura, fatura);
		fatura.setStatus(TipoStatus.FECHADA);
		Fatura faturaAtualizada = this.update(fatura);
		this.insert(proximaFatura);
		this.lancamentoService.saveAllLancamentos(proximaFatura.getLancamentos());
		this.dividaService.saveAllDividas(dividas);

		log.info("[Fechar-Fatura] - Fatura Fechada com sucesso");
		return faturaAtualizada;
	}

	private Fatura gerarProximaFatura(Fatura faturaAtual) {
		log.info("[GerarNovaFatura] - Gerando nova fatura.");
		LocalDateTime vencimento = faturaAtual.getVencimento().plusMonths(1);
		TipoMes mesReferente = faturaAtual.getMesReferente() == TipoMes.DEZEMBRO ? TipoMes.JANEIRO : TipoMes.toEnum(faturaAtual.getMesReferente().getCod() + 1) ;
		Fatura novaFatura = new Fatura(null, vencimento, mesReferente, faturaAtual.getCartao());
		novaFatura.setLancamentos(this.lancamentoService.gerarLancamentosFuturos(faturaAtual.getLancamentos(), novaFatura));

		log.info("[GeraNovaFatura] - Nova fatura gerada com sucesso.");
		return novaFatura;
	}

	public Fatura pagarFatura(Long id) {
		log.info("[Pagar-Fatura] - Pagando fatura.");
		Fatura fatura = this.findById(id);
		fatura.setStatus(TipoStatus.PAGA);

		log.info("[Pagar-Fatura] - Fatura paga com sucesso");
		return this.update(fatura);
	}

	@Override
	public Fatura fromDTO(FaturaDTO dto) {
		log.info("[Mapping] - 'FaturaDTO' to 'Fatura'. Id: " + dto.getId());
		Fatura fatura = new Fatura(dto.getId(), dto.getVencimento(), dto.getObservacao(), dto.getMesReferente(),
				new Cartao(dto.getCartaoId()));

		log.info("[Mapping] - Mapping finalizado com sucesso.");
		return fatura;
	}

	@Override
	public void updateData(Fatura newObj, Fatura obj) {
		log.info("[Parse] - 'NewFatura' from 'Fatura'. Id: " + newObj.getId());
		newObj.setVencimento(obj.getVencimento());
		newObj.setObservacao(obj.getObservacao());
		newObj.setStatus(obj.getStatus());
		log.info("[Parse] - Parse finalizado com sucesso.");
	}
}
