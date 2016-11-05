package br.com.tezza.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.tezza.model.UsuarioModel;

/**
 * @author Alex Tezza
 * 2 de nov de 2016
 *
 * Este Filter irá validar se o usuário esta logado para lhe dar acesso as páginas do sistema.
 * Usada a implementação Filter pois é necessário filtrar as requisições.
 */
@WebFilter("/sistema/*") // Filtrara todos os itens que estiverem em '/sistemas/*'
public class AutenticacaoFilter implements Filter {

	// Construtor padão da classe.
	public AutenticacaoFilter() {

	}

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpSession httpSession 				= ((HttpServletRequest) request).getSession();

		HttpServletRequest httpServletRequest   = (HttpServletRequest) request;

		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		if(httpServletRequest.getRequestURI().indexOf("index.xhtml") <= -1){

			UsuarioModel usuarioModel =(UsuarioModel) httpSession.getAttribute("usuarioAutenticado");

			if(usuarioModel == null){

				httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+ "/index.xhtml");

			}
			else{

				chain.doFilter(request, response);
			}
		}
		else{

			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
