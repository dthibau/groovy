import groovy.json.JsonSlurper
import org.formation.dynamic.JsonExporter
import org.formation.dynamic.XMLExporter
import org.formation.model.DBHelper
import org.formation.model.Index
import org.formation.model.IndexDAO
import org.formation.service.IndexerService


import groovy.sql.Sql

response.contentType = 'application/json'

// Store in BD
def connection= DBHelper.connection
def indexDAO = new IndexDAO(db : connection)

List indexes = indexDAO.findAll()

connection.close()
// build JSON output
json(indexes)
