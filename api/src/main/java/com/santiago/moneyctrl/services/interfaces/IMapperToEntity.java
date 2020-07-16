package com.santiago.moneyctrl.services.interfaces;

import com.santiago.moneyctrl.domain.BaseEntity;
import com.santiago.moneyctrl.dtos.BaseDTO;

public interface IMapperToEntity<K extends BaseDTO, T extends BaseEntity> {

	/**
	 * Executa a transformação de um Dto em uma Entidade
	 *
	 * @param dto Dto a ser transformado
	 * @return Entidade resultante da transformação
	 */
	T fromDTO(K dto);
}
