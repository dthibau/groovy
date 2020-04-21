package org.formation.service

import org.formation.model.Persistent

class PersistentService {
	
	def save(Persistent entity) {
		entity.save();
	}
}
