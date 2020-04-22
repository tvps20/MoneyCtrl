package com.santiago.services.interfaces;

import com.santiago.domain.BaseEntity;
import com.santiago.dtos.BaseDTO;

public interface IMapperToEntity<K extends BaseDTO, T extends BaseEntity> {

	/**
	 * Executa a transformação de um Dto em uma Entidade
	 *
	 * @param dto Dto a ser transformado
	 * @return Entidade resultante da transformação
	 */
	T fromDTO(K dto);
}
