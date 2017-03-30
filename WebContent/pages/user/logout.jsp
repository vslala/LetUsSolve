<%
	if (null != session.getAttribute("user"))
	{
		session.removeAttribute("user");
		response.sendRedirect(request.getContextPath() + "/pages/site/home.jsp");
		return;
	}
%>