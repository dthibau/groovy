package org.formation.model

import groovy.sql.Sql

class DBHelper {
	static def url = 'jdbc:postgresql://localhost:5434/groovy'
	static  user = 'postgres'
	static  password = 'postgres'
	static  driver = 'org.postgresql.Driver'
	
	static Sql getConnection() {
		Sql.newInstance(url, user, password, driver)
	}
	
	
}
