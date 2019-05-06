package spring.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.entity.QuizDto;
import spring.repository.ExamQuizDao;

/**
 * 문제가 시험에 사용되었는지 검사하여
 *  사용중인 문제는 수정이 불가능
 *  관리자의 경우 수정 가능
 */
@Component
public class IsUsedQuizEditFilter implements Filter{

	
	@Autowired
	private ExamQuizDao examQuizDao;
	 
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse)response;
		HttpServletRequest req = (HttpServletRequest) request;
		String no = request.getParameter("no");
		
		
		int quiz_no = Integer.parseInt(request.getParameter("no"));
		String power = (String) req.getSession().getAttribute("power");
		
		boolean result = examQuizDao.isUsed(quiz_no);
		
		//사용중이면서 관리자가 아닐때
		if(result && !power.equals("관리자")) {
			resp.sendRedirect("/exampaper/register/q_info?no="+quiz_no);
		}
		else{ //사용중이 아니거나 관리자일 때 	
			chain.doFilter(request, response);
		}
	}

}
