package com.entite;

public class Aide {
	private String question;
	private String reponse;
	public Aide()
	{}
	public Aide(String q, String r) {
		setQuestion(q);setReponse(r);
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getReponse() {
		return reponse;
	}
	public void setReponse(String reponse) {
		this.reponse = reponse;
	}

}
