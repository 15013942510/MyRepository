package com.web.shopping.pojogroup;

import java.io.Serializable;
import java.util.List;

import com.web.shopping.pojo.TbSpecification;
import com.web.shopping.pojo.TbSpecificationOption;

public class Specification implements Serializable {

	private TbSpecification specification;
	
	private List<TbSpecificationOption> specificationOptionsList;

	public TbSpecification getSpecification() {
		return specification;
	}

	public void setSpecification(TbSpecification specification) {
		this.specification = specification;
	}

	public List<TbSpecificationOption> getSpecificationOptionsList() {
		return specificationOptionsList;
	}

	public void setSpecificationOptionsList(List<TbSpecificationOption> specificationOptionsList) {
		this.specificationOptionsList = specificationOptionsList;
	}
	
	
}
