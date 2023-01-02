package org.formation.test

import java.sql.Timestamp

import org.formation.model.Index
import org.formation.model.IndexDAO
import org.formation.service.IndexService
import org.formation.service.IndexerFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import groovy.mock.interceptor.MockFor
import groovy.mock.interceptor.StubFor

class IndexServiceTest {
	def anIndex
	@BeforeEach
	public void setUp() {
		anIndex = new Index(created : new Date(), indexed : new Date(), map : ['titi':1,'toto':2])
	}

	@Test
	public void testMockReindex() {
		def mock = new MockFor(IndexDAO)
		mock.demand.with {
			findById{ anIndex }
			update { String s, Timestamp t, int id -> 1 }
			update { String s, String s2, int id -> 1 }
			update { String s, String s2, int id -> 1 }
		}
		mock.use {
			def indexService = new IndexService(dao : new IndexDAO(), indexer : IndexerFactory.getDefaultIndexer())
			def index = indexService.reindex(1, "dumy dummy")
			assert index.created == anIndex.created
			assert index.indexed.after(anIndex.created)
		}
		mock.expect.verify()
	}

	@Test
	public void testStubReindex() {
		def stub = new StubFor(IndexDAO)
		stub.demand.with {

			update { String s, Timestamp t, int id -> 1 }
			update { String s, String s2, int id -> 1 }
			update { String s, String s2, int id -> 1 }
			findById{ anIndex }
		}
		stub.use {
			def indexService = new IndexService(dao : new IndexDAO(), indexer : IndexerFactory.getDefaultIndexer())
			def index = indexService.reindex(1, "dumy dummy")
			assert index.created == anIndex.created
			assert index.indexed.after(anIndex.created)
		}
	}
}
