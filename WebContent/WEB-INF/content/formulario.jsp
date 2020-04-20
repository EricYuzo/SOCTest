<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SOCTest</title>
<style>
.campo-form {
  display: block;
  margin-bottom: 1em;
}
</style>
</head>
<body>
  <h1>Novo Exame Ocupacional</h1>
  
  <form action=salvaExame method=post>
    <label for="cb-finalidades">Finalidade:</label>
    <select id="cd-finalidades" name="exame.finalidade.id" class="campo-form">
      <c:forEach var="finalidade" items="${finalidades}">
        <option value="${finalidade.id}">${finalidade}</option>
      </c:forEach>
    </select>
    
    <label for="cb-funcionarios">Funcionários:</label>
    <select id="cd-funcionarios" name="exame.participante.id" class="campo-form">
      <c:forEach var="funcionario" items="${funcionarios}">
        <option value="${funcionario.id}">${funcionario}</option>
      </c:forEach>
    </select>
    
    <label for="cb-medicos">Médico:</label>
    <select id="cd-medicos" name="exame.medico.id" class="campo-form">
      <c:forEach var="medico" items="${medicos}">
        <option value="${medico.id}">${medico}</option>
      </c:forEach>
    </select>
    
    <label for="cb-resultados">Resultado:</label>
    <select id="cd-resultados" name="exame.resultado.id" class="campo-form">
      <c:forEach var="resultado" items="${resultados}">
        <option value="${resultado.id}">${resultado}</option>
      </c:forEach>
    </select>
    
    <input type="submit" value="Incluir">
  </form>
</body>
</html>