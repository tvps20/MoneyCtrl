package com.santiago.moneyctrl.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.santiago.moneyctrl.dtos.enuns.TipoEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@JsonInclude(Include.NON_NULL) // Não faz a serialização se o valor for null
public class BaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	protected Long id;

	@Getter
	@Setter
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	protected LocalDateTime createdAt;

	@Getter
	@Setter
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	protected LocalDateTime updatedAt;
	
	@Getter
	@Setter
	protected boolean ativo;
	
	@Getter
	@Setter
	protected TipoEntity tipo;

	// Construtores
	public BaseDTO() {
	}

	public BaseDTO(Long id) {
		this.id = id;
	}

	// Metodos de Comparacao por valor
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseDTO other = (BaseDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
