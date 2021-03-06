package com.santiago.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
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
