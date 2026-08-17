<form action="${pageContext.request.contextPath}/login" method="post">

    <label>Username:</label>
    <input type="text" name="username" required>

    <label>Password:</label>
    <input type="password" name="password" required>

    <button type="submit">Login</button>

</form>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>