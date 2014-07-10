package com.assist;

public class ActiveOrders extends PrivateMultipleResponse {
	public ActiveOrders(PrivateNetwork nt) {
		super();
		network = nt;
	}

	public synchronized boolean runMethod() {
		setData(network.sendRequest("OrderList", getParams(),
				connectTimeoutMillis, readTimeoutMillis));
		return success;
	}

	/**
	 * Method is not allowed for this class
	 */
	public synchronized void setFrom(String str) throws NoSuchMethodException {
		throw new NoSuchMethodException("This parameter is not allowed");
	}

	/**
	 * Method is not allowed for this class
	 */
	public synchronized void setCount(String str) throws NoSuchMethodException {
		throw new NoSuchMethodException("This parameter is not allowed");
	}

	/**
	 * Method is not allowed for this class
	 */
	public synchronized void setFrom_id(String str)
			throws NoSuchMethodException {
		throw new NoSuchMethodException("This parameter is not allowed");
	}

	/**
	 * Method is not allowed for this class
	 */
	public synchronized void setEnd_id(String str) throws NoSuchMethodException {
		throw new NoSuchMethodException("This parameter is not allowed");
	}

	/**
	 * Method is not allowed for this class
	 */
	public synchronized void setOrder(String str) throws NoSuchMethodException {
		throw new NoSuchMethodException("This parameter is not allowed");
	}

	/**
	 * Method is not allowed for this class
	 */
	public synchronized void setSince(String str) throws NoSuchMethodException {
		throw new NoSuchMethodException("This parameter is not allowed");
	}

	/**
	 * Method is not allowed for this class
	 */
	public synchronized void setEnd(String str) throws NoSuchMethodException {
		throw new NoSuchMethodException("This parameter is not allowed");
	}

	public synchronized void setPair(String str) {
		paramsMap.put("pair", str.replace('-', '_').toLowerCase());
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

	public synchronized String getCurrentTimestamp_created() {
		return rootNode.path("return").path(current_position)
				.path("timestamp_created").toString();
	}

	public synchronized int getCurrentStatus() {
		return rootNode.path("return").path(current_position).path("status")
				.asInt();
	}
}
