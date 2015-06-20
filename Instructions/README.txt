1. Download JBoss AS 7.1.0.Final (http://jbossas.jboss.org/downloads/) and extract zip
 - Go to: jboss-as-7.1.1.Final\modules\com\ and add 'mysql' folder from instructions 
 - In standalone.xml you have to add datasource:
	<datasource jta="true" jndi-name="java:/ConferenceDS" pool-name="ConferenceDS" enabled="true" use-java-context="true" use-ccm="true">
                    <connection-url>jdbc:mysql://localhost:3306/javaee?characterEncoding=utf8</connection-url>
                    <driver>mysql</driver>
                    <security>
                        <user-name>root</user-name>
                        <password>root</password> // your pass
                    </security>
                    <statement>
                        <prepared-statement-cache-size>100</prepared-statement-cache-size>
                        <share-prepared-statements>true</share-prepared-statements>
                    </statement>
    </datasource>
 - add driver:  <driver name="mysql" module="com.mysql"/>
 
2. In folder db_migration is sql file for DB with one test user

. Eclipse Luna (Eclipse IDE for Java EE Developers)
 - File -> Import.. -> General -> Existing Projects into Workspace and import project from GitHub
 - Window -> Show view -> Servers
 - In Servers menu:
	- Click this link to create a new server ...
	- Choose JBoss Community -> JBoss AS 7.1 and click Next
	- In Jboss Runtime Screen in Home Directory enter where you extract jboss zip
	- !!! Runtime JRE choose Alternate JRE and MUST BE java 7 NOT 8 and click Finish. The server is shown in Servers tab.
	- Right click on server -> Add and Remove.. and add project to server
	- Run server
	
 - If everything is OK : http://localhost:8080/FMI.JavaEE/login.xhtml
 