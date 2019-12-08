<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
   <head>
      <title>Jmp web module</title>
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
      <script type="text/javascript" src="ajax.js"></script>
   </head>

   <body>
      <p> Simple Java Web App </p>
      <br>
      <table>
         <tr>
            <th>Inner state:</th>
         </tr>
         <c:forEach items="${InnerState}" var="listItem">
            <tr>
               <td>
                  <c:out value="${listItem}"/>
               </td>
            </tr>
         </c:forEach>
      </table>
      <br>
      <div id="status"></div>
      <br>
      <form action="jmp">
         <input type="submit" value="Update page GET"/>
      </form>
      <br>
      <form action="jmp" method="POST" id="post-form">
         <div id="inputStatus">Add new String:</div>
         <input type="text" name="String" id="postInput"/>
         <input type="submit" value="POST"/>
      </form>
      <br>
      <form id="put-form">
         <select id="stringToUpdate">
            <c:forEach items="${InnerState}" var="listItem">
               <option value="${listItem}">${listItem}</option>
            </c:forEach>
         </select>
         <input type="text" name="newString" id="newString"/>
         <input type="submit" value="UPDATE" class="put-button"/>
      </form>
      <br>
      <form id="delete-form">
         <select id="stringToDelete">
            <c:forEach items="${InnerState}" var="listItem">
               <option value="${listItem}">${listItem}</option>
            </c:forEach>
         </select>
         <input type="submit" value="DELETE" class="put-button"/>
      </form>
      <br>

      <c:set var="currentDate" value="<%=new java.util.Date()%>"/>
      Current date with date type attribute: <br/>
      <fmt:formatDate type="date" value="${currentDate}" />
      <br/>
      Current date with time type attribute: <br/>
      <fmt:formatDate type="time" value="${currentDate}" />
      <br/>
      Current date with both type attribute: <br/>
      <fmt:formatDate type="both" value="${currentDate}" />
      <br/>
      Current date with pattern type attribute: <br/>
      <fmt:formatDate pattern="yyyy-MM-dd" value="${currentDate}"/>

   </body>
</html>
