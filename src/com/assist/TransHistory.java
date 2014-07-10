package com.assist;

public class TransHistory extends PrivateMultipleResponse {
	public TransHistory(PrivateNetwork nt) {
		super();
		network = nt;
	}

	public synchronized boolean runMethod() {
		setData(network.sendRequest("TransHistory", getParams(),
				connectTimeoutMillis, readTimeoutMillis));
		return success;
	}

	public synchronized int getCurrentType() {
		return rootNode.path("return").path(current_position).path("type")
				.asInt();
	}

	public synchronized String getCurrentCurrency() {
		return rootNode.path("return").path(current_position).path("currency")
				.asText();
	}

	public synchronized String getCurrentDesc() {
		return rootNode.path("return").path(current_position).path("desc")
				.asText().replaceFirst(":order:", "").replace(":", "");
	}

	public synchronized int getCurrentStatus() {
		return rootNode.path("return").path(current_position).path("status")
				.asInt();
	}

	public synchronized String getCurrentTimestamp() {
		return rootNode.path("return").path(current_position).path("timestamp")
				.toString();
	}

}
