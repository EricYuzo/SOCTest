<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SOCTest</title>
<style>
.lista-exames {
  width: 100%;
  border-collapse: collapse;
}
th, td {
  border: 1px solid black;
}
</style>
</head>
<body>
  <h1>Exames Ocupacionais</h1>
  <h2>Todos os exames</h2>
  <table class="lista-exames">
  <thead>
    <tr>
      <th>Finalidade</th>
      <th>Empresa</th>
      <th>Funcionário</th>
      <th>Setor</th>
      <th>Cargo</th>
      <th>Médico</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="exame" items="${exames}">
      <tr>
        <td>${exame.finalidade}</td>
        <td>${exame.participante.empresa}</td>
        <td>${exame.participante.funcionario}</td>
        <td>${exame.participante.setor}</td>
        <td>${exame.participante.cargo}</td>
        <td>${exame.medico}</td>
      </tr>
    </c:forEach>
  </tbody>
  </table>
</body>
</html>