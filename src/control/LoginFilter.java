package control;

import java.io.IOException;
  
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.UserLogin;
import bean.AdminLogin;

/**
* Classe para o filtro da sess�o.
* @author Rodrigo da Silva Freitas <rodrigojato@hotmail.com>
* @package control
*/
public class LoginFilter implements Filter {

	/**
	* M�todo para fazer a filtragem
	* @param ServletRequest Representa a requisi��o
	* @param ServletResponse Representa a resposta
	* @param FilterChain Representa a malha do filtro
	*/
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		AdminLogin session = (AdminLogin) req.getSession().getAttribute("AdminLogin"); //MUDA ESSA LINHA - COLOCAR NOME DA BEAN Q TA FAZENDO LOGIN
		UserLogin session2 = (UserLogin) req.getSession().getAttribute("UserLogin");
		String url = req.getRequestURI();
		if((session == null || !session.getLogged()) && (session2==null || !session2.getLogged())) {
			if(url.indexOf("addPost.xhtml") >=0) { //PAGINA QUE VAI SER FILTRADA, Q S� VAI SER ACESSADA BASEADO NA SESS�O
				res.sendRedirect(req.getServletContext().getContextPath() + "/login.xhtml"); // P�GINA QUE VAI SER ENVIADA CASO N�O ESTEJA LOGADO
			}else {
				chain.doFilter(request, response); //N�O SEI OQ ISSO FAZ
			}
		}else {
			if(session==null || !session.getLogged()) {
				if(url.indexOf("login.xhtml") >=0) { //P�gina q n pode ser acessada caso a pessoa esteja logada, tipo a login
					res.sendRedirect(req.getServletContext().getContextPath() + "/index.xhtml"); //Pra onde a pessoa vai ser enviada.
				}else if(url.indexOf("logout.xhtml") >=0) {
					req.getSession().removeAttribute("UserLogin");
					res.sendRedirect(req.getServletContext().getContextPath() + "/index.xhtml");
				}else if(url.indexOf("addPost.xhtml") >=0) {
					res.sendRedirect(req.getServletContext().getContextPath() + "/index.xhtml");
				}else {
					chain.doFilter(request, response); //NOVAMENTE N SEI OQ FAZ
				}
			}else {
				if(url.indexOf("login.xhtml") >=0) { //P�gina q n pode ser acessada caso a pessoa esteja logada, tipo a login
					res.sendRedirect(req.getServletContext().getContextPath() + "/index.xhtml"); //Pra onde a pessoa vai ser enviada.
				}else if(url.indexOf("logout.xhtml") >=0) {
					req.getSession().removeAttribute("AdminLogin");
					res.sendRedirect(req.getServletContext().getContextPath() + "/index.xhtml");
				}else {
					chain.doFilter(request, response); //NOVAMENTE N SEI OQ FAZ
				}
			}
			
		}
		
	}
	

}