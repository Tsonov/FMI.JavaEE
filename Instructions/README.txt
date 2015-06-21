1. Finally we will use TomeeAS (I put zip in Instructions folder)
 
2. In folder db_migration is sql file for DB with one test user

. Eclipse Luna (Eclipse IDE for Java EE Developers)
 - File -> Import.. -> General -> Existing Projects into Workspace and import project from GitHub
 - Window -> Show view -> Servers
 - In Servers menu:
	- Click this link to create a new server ...
	- Choose Apache -> Apache Tomcat v7.0 Server and click Next
	- !!! Runtime JRE choose Alternate JRE and MUST BE java 6 and click Finish. The server is shown in Servers tab.
	- Right click on server -> Add and Remove.. and add project to server
	- Run server
	
 - If everything is OK : http://localhost:8080/JavaEE/
 