<%
	if (null != session.getAttribute("admin"))
		session.removeAttribute("admin");
	response.sendRedirect(request.getContextPath() + "/pages/admin/login.jsp");
	
%>