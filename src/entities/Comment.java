package entities;

public class Comment {
	private Orderer orderer;
	private String comment;
	public Orderer getOrderer() {
		return orderer;
	}
	public void setOrderer(Orderer orderer) {
		this.orderer = orderer;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
