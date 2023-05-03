package org.formation

//@GrabConfig( systemClassLoader=true )
//@Grab('org.postgresql:postgresql:42.2.9')
import org.formation.model.Index
import org.formation.service.Indexer

import groovy.transform.Field
import groovy.xml.MarkupBuilder
import groovy.sql.Sql


def url = 'jdbc:postgresql://localhost:5434/groovy'
def user = 'postgres'
def password = 'postgres'
def driver = 'org.postgresql.Driver'
def sql = Sql.newInstance(url, user, password, driver)

sql.execute '''
  DROP TABLE IF EXISTS formation;
'''

sql.execute '''
  CREATE TABLE formation (
    id          SERIAL,
    created   timestamp,
    indexed	  timestamp,
    xmlmap    text,
    jsonmap   text
  );
'''

sql.close()