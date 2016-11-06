package br.com.tezza.uteis;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Alex Tezza
 * 5 de nov de 2016
 *
 * 	Classe responsável por realizar a conversão da data para poder persistir.
 */
@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp>{

	// Transforma data do tipo LocalDateTime em Timestamp no momento de gravar no banco de dados.
	// Parâmetro localDateTime possui a data à ser transformada.
	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
		if(localDateTime != null) // Se o parâmetro não for nulo
    		return Timestamp.valueOf(localDateTime); // Retorna o valor da data no tipo Timestamp.

    	return null; // Se o parâmetro for nulo, retorna nulo.
	}

	// Transforma data do tipo Timestamp em LocalDateTime no memento que recupera a informação do banco de dados.
	// Parâmetro timestamp possui a data à ser transformada.
	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
		if(timestamp != null) // Se o parâmetro não for nulo
    		return timestamp.toLocalDateTime(); // Retorna o valor da data no tipo LocalDateTime.

    	return null; // Se o parâmetro for nulo, retorna nulo.
	}

}
