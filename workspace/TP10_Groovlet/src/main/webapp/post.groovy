import groovy.json.JsonSlurper
import org.formation.dynamic.CSVExporter
import org.formation.dynamic.JsonExporter
import org.formation.dynamic.XMLExporter
import org.formation.model.DBHelper
import org.formation.model.Index
import org.formation.model.IndexDAO
import org.formation.service.IndexerService


import groovy.sql.Sql

response.contentType = 'application/json'

// Access headers, with context bound variable
if (headers['Content-Type'] != "application/json") {
    throw new RuntimeException("Please use 'application/json' header")
}

// Get content from POST body
def jsonContent = request.reader.text

// Parse JSON to Map
def content = null
if (jsonContent) {
    content = new JsonSlurper()
                .parseText(jsonContent)
}

if ( content.text == null ) {
    throw new RuntimeException("Please provide a text field")
}

// New index
def index = new Index(content.text) 
index = IndexerService.instance.buildIndex(index)

// Store in BD
def connection= DBHelper.connection
def indexDAO = new IndexDAO(db : connection)

use (XMLExporter) {
	index.metaClass.xmlMap = index.asXML()
	index.metaClass.id = indexDAO.create([index.created.toTimestamp(),index.indexed.toTimestamp(),index.xmlMap])
}

connection.close()
// build JSON output
json."result" {
    index.findAll() { i -> 
        "id" i.id
        "created" i.created
        "indexed" i.indexed
        "keywords" i.map
    }
}

