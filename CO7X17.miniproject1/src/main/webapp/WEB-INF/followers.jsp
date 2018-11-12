<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" media="screen" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
<html>
	<head>
		<title>Followers report</title>
	</head>
	<body>
		<h4>These are the chosen followers:</h4>
		
		<ul>
			<c:forEach var="follower" items="${followers}">
				<li>${follower.name}</li>
			</c:forEach>
		</ul>
	</body>
</html>