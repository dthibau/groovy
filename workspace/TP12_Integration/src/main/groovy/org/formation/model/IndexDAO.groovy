package org.formation.model

class IndexDAO extends DAOAbstract {

	@Override
	public List getFields() {
		return [
			'created',
			'indexed',
			'xmlmap',
			'jsonmap'
		];
	}

	@Override
	public String getTablename() {
		return 'formation';
	}

	@Override
	public String getIdField() {
		return 'id';
	}
}
