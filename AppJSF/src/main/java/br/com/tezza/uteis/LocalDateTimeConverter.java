package br.com.tezza.uteis;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;
/**
 *
 * @author Alex Tezza
 * 8 de nov de 2016
 *
 *	Classe utilizada para realizar a conversão de datas exibidas na listagem de pessoas.
 */


@FacesConverter(value= LocalDateTimeConverter.ID) // Passa a constante ID como valor.
public class LocalDateTimeConverter extends DateTimeConverter {

	// Constante que contêm o caminho da classe
	public static final String ID="br.com.tezza.uteis.LocalDateTimeConverter";

	// Método recebe os objetos, converte suas datas e retorna no novo formato.
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		// Cria um novo objeto Date recebendo nulo.
		Date date = null;

		// Cria um novo objeto LocalDataTime que também recebe nulo.
		LocalDateTime localDateTime = null;
		// Cria um Objeto para receber o retorno do método getAsObject que é da superclasse. Passando como parâmetro os objetos
		// que o método getAsObject desta classe receberam.
		Object object = super.getAsObject(facesContext, uiComponent, value);

		// Compara a variável com a classe para saber se a variável pertence ao mesmo tipo de objeto.
		if(object instanceof Date){

			// A variável date recebe os valores da variável object.
			date = (Date) object;

			// Instância um Instant que recebe a data.
			Instant instant = Instant.ofEpochMilli(date.getTime());
			// Seta uma 'mascara' de formatação de data default.
			localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
			// Retorna a data formatada.
			return localDateTime;

		}
		else{
			// Caso a operação de conversão não seja possível, a exceção é lançada e sua mensagem é exibida.
			throw new IllegalArgumentException(String.format("value=%s Não foi possível converter LocalDateTime, resultado super.getAsObject=%s",value,object));
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		// Se o value for nulo
		if(value == null)
			// No retorno é chamando o método da da superclasse passando os valores recebidos como parâmetro.
			return super.getAsString(facesContext, uiComponent, value);

		// Compara a variável com a classe para saber se a variável pertence ao mesmo tipo de objeto.
		if(value instanceof LocalDateTime){

			// Instância a variável rebendo o valor de value.
			LocalDateTime localDateTime = (LocalDateTime) value;

			// Instância um novo Instant que recebe uma 'mascara de formatação' de data.
			Instant instant = localDateTime.toInstant(ZoneOffset.UTC);

			// Instância um Date que recebe uma data de instant.
			Date  date =  Date.from(instant);

			// No retorno é chamado o método getAsString da superclasse,
			// passando como parâmetro os objetos recebidos por parâmetro no método getAsString desta classe.
			return super.getAsString(facesContext, uiComponent, date);
		}
		else{
			// Caso o valor não seja um LocalDataTime é lançada a execeção e sua mensagem é exibida.
			throw new IllegalArgumentException(String.format("value=%s não é um LocalDateTime",value));
		}
	}
}