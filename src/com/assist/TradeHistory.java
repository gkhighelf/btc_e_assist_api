package com.assist;

public class TradeHistory extends PrivateMultipleResponse {
	public TradeHistory(PrivateNetwork nt) {
		super();
		network = nt;
	}

	public synchronized void setPair(String str) {
		paramsMap.put("pair", str.replace('-', '_').toLowerCase());
	}

	public synchronized boolean runMethod() {
		setData(network.sendRequest("TradeHistory", getParams(),
				connectTimeoutMillis, readTimeoutMillis));
		return success;
	}

	public synchronized String getCurrentPair() {
		return convertPairName(rootNode.path("return").path(current_position)
				.path("pair").asText());
	}

	public synchronized String getCurrentType() {
		return rootNode.path("return").path(current_position).path("type")
				.asText();
	}

	public synchronized String getCurrentRate() {
		return formatDouble(rootNode.path("return").path(current_position)
				.path("rate").asDouble());
	}

	public synchronized String getCurrentOrder_id() {
		return rootNode.path("return").path(current_position).path("order_id")
				.toString();
	}

	public synchronized int getCurrentIs_Your_Order() {
		return rootNode.path("return").path(current_position)
				.path("is_your_order").asInt();
	}

	public synchronized String getCurrentTimestamp() {
		return rootNode.path("return").path(current_position).path("timestamp")
				.toString();
	}
}
