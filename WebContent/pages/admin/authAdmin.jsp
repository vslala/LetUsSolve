<%
	if (null == session.getAttribute("admin"))
		response.sendRedirect(request.getContextPath() + "/pages/admin/login.jsp");
%>