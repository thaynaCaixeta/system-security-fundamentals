<%
    String error = (String) request.getAttribute("error");
    String originalString = (String) request.getAttribute("originalString");
    String hashedString = (String) request.getAttribute("hashedString");
%>
<html>
<body>
<form action="<%= request.getContextPath() %>/servlet" method="get">
    <label>String to hash:
        <input type="text" name="stringToHash" />
    </label>
    <button type="submit">Hash</button>
</form>

<% if (error != null && !error.isEmpty()) { %>
<p style="color:red"><%= error %></p>
<% } %>

<% if (originalString != null && hashedString != null) { %>
<h3>Hash Result</h3>
<p><strong>Original string:</strong> <%= originalString %></p>
<p><strong>Hashed string:</strong> <%= hashedString %></p>
<% } %>
</body>
</html>
