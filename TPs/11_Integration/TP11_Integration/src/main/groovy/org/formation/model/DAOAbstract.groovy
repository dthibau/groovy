package org.formation.model

import groovy.json.JsonSlurper
import groovy.sql.DataSet

abstract class DAOAbstract {
	def db;
	
	abstract List getFields()
	abstract String getTablename()
	abstract String getIdField()
	
	
	DataSet dataSet() {
		db.dataSet(tablename)
	}

	def getWhereId() {
		"WHERE $idField = ?"
	}

	def create(List args) {
		Map argMap = [:]
		args.eachWithIndex { arg, i -> argMap[fields[i]] = arg }
		dataSet().add argMap
	}


	def update(field, newValue, id) {
		def stmt = "UPDATE $tablename SET $field = ? $whereId"
		db.executeUpdate stmt, [newValue, id]
	}
	def delete(id) {
		def stmt = "DELETE FROM $tablename $whereId"
		db.executeUpdate stmt, [id]
	}
	def findById(int id) {		
		dataSet().findAll { it.id == id } 
	}

	def findAll() {
		dataSet().rows()
	}
}


