package com.santiago.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
public class Pagamento extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	private BigDecimal valor;
	
	@Getter
	@Setter
	@Column(nullable = false)
	private LocalDate data;
}
