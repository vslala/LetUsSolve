package com.letussolve.models;

public class Question {
	private int qId;
	private String question;
	private Subject subject;
	private Answer rightAnswer;
	
	public int getqId() {
		return qId;
	}
	public void setqId(int qId) {
		this.qId = qId;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public Answer getRightAnswer() {
		return rightAnswer;
	}
	public void setRightAnswer(Answer rightAnswer) {
		this.rightAnswer = rightAnswer;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Question: " + question)
			.append("ID" + qId)
			.append("Subject: " + subject)
			.append("Right Answer: " + rightAnswer);
		return sb.toString();
	}
}
