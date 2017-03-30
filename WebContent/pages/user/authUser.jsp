<% 
	if (null == session.getAttribute("user")) {
		response.sendRedirect(request.getContextPath() + "/pages/site/home.jsp");
	}
%>