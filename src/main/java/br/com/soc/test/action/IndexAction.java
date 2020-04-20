package br.com.soc.test.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

public class IndexAction {
	
	@Action(value = "helloWorld", results = { @Result(location = "index.jsp", name = "ok") })
	public String execute() {
		System.out.println("Rodando com Struts 2 pela primeira vez na vida");
		return "ok";
	}

}
