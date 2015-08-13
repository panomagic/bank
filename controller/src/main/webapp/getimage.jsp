<%@ page import = "java.io.*" %>
<%@ page import = "java.sql.*" %>
<% Connection conn = null;
  response.setContentType("image/jpeg");
  OutputStream os = response.getOutputStream();
  try {
    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "970195");
  } catch (SQLException e) {
    e.printStackTrace();
  }
  String req = "" ;
  Blob img ;
  byte[] imgData = null ;
  Statement stmt = conn.createStatement ();
  req = "SELECT image FROM users Where ID = 1" ;
  ResultSet rset  = null;
  try {
    rset = stmt.executeQuery (req);
  } catch (SQLException e) {
    e.printStackTrace();
  }
  while (rset.next ())
  {
    img = rset.getBlob(1);
    imgData = img.getBytes(1,(int)img.length());
  }
  os.write(imgData);
  os.flush();
  rset.close();
  stmt.close(); %>