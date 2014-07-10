package com.assist;

import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

abstract class PublicBaseClass extends CommonClass {
	protected Iterator<String> it;
	protected String current_position;
	private StringBuilder paramsBuf;
	private StringBuilder address;
	protected boolean success;
	private boolean isLimit;
	private boolean isIgnoreInvalid;
	private StringBuilder limitString;

	final private String limit = "limit=";
	final private String ignoreInvalid = "ignore_invalid=1";
	final private String TARGET_URL = "https://btc-e.com/api/3/";

	PublicBaseClass() {
		paramsBuf = new StringBuilder("/");
		address = new StringBuilder();
		limitString = new StringBuilder();
		isLimit = false;
		isIgnoreInvalid = true;
	}

	/**
	 * Send request.
	 * 
	 * @return TRUE if positive answer is received. FALSE if has ANY trouble. If
	 *         getErrorMessage().length()==0 it's network troubles.
	 */
	protected synchronized boolean runMethod() {
		return false;
	}

	public synchronized void addPair(String pair) throws NoSuchMethodException {
		paramsBuf.append(pair.replace('-', '_').toLowerCase() + "-");
	}

	public synchronized void setLimit(int count) throws NoSuchMethodException {
		limitString.delete(0, limitString.length());
		limitString.append(limit);
		limitString.append(String.valueOf(count));
		isLimit = true;
	}

	/**
	 * This method reverse "ignore_invalid" parameter. Default state is
	 * "ignore_invalid=1"
	 * 
	 * @throws NoSuchMethodException
	 */
	public synchronized void setReverseIgnoreInvalid()
			throws NoSuchMethodException {
		isIgnoreInvalid = isIgnoreInvalid ? false : true;
	}

	/**
	 * Reset all parameters (addPair, Limit, ignoreInvalid);
	 * 
	 * @throws NoSuchMethodException
	 */
	public synchronized void resetParams() throws NoSuchMethodException {
		paramsBuf.delete(0, paramsBuf.length());
		paramsBuf.append("/");
		isLimit = false;
		isIgnoreInvalid = true;
	}

	/**
	 * You can set data for object manually, if need
	 * 
	 * @param json
	 *            in JSON format
	 * @return true if this data success=1
	 */
	public synchronized boolean setData(String json) {
		try {
			rootNode = mapper.readTree(json);
			it = rootNode.fieldNames();
			if (it != null) {
				if (!rootNode.path("success").toString().equals("0")) {
					localTimestamp = System.currentTimeMillis();
					success = true;
					return true;
				}
			}
		} catch (Exception e) {
		}
		makeDefaultRootNode();
		success = false;
		it = null;
		localTimestamp = 0;
		return false;
	}

	protected boolean sendRequest(String name) {
		try {
			address.delete(0, address.length());
			address.append(TARGET_URL);
			address.append(name);
			address.append(paramsBuf);
			if (isLimit && isIgnoreInvalid) {
				address.append("?");
				address.append(limitString);
				address.append("&");
				address.append(ignoreInvalid);
			} else if (isLimit) {
				address.append("?");
				address.append(limitString);
			} else if (isIgnoreInvalid) {
				address.append("?");
				address.append(ignoreInvalid);
			}
			URL url = new URL(address.toString());
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(connectTimeoutMillis);
			connection.setReadTimeout(readTimeoutMillis);
			rootNode = null;
			it = null;
			localTimestamp = 0;
			success = false;
			rootNode = mapper.readTree(connection.getInputStream());
			if (!rootNode.path("success").toString().equals("0")) {
				it = rootNode.fieldNames();
				if (it != null) {
					localTimestamp = System.currentTimeMillis();
					success = true;
					return true;
				}
			} else if (getErrorMessage().length() != 0) {
				return false;
			}
		} catch (Exception e) {
		}
		makeDefaultRootNode();
		return false;
	}

	/**
	 * Error message from server if success=0 and it's not connection trouble.
	 * 
	 * @return error message
	 */
	public synchronized String getErrorMessage() {
		return rootNode.path("error").asText();
	}

	public synchronized boolean isSuccess() {
		return success;
	}

	public synchronized boolean hasNextPair() {
		if (it != null) {
			return it.hasNext();
		}
		return false;
	}

	public synchronized void switchNextPair() {
		if (it != null) {
			current_position = it.next();
		}
	}

	public synchronized void switchResetPair() {
		it = rootNode.fieldNames();
	}

	public synchronized void setCurrentPair(String pair) {
		current_position = pair.replace('-', '_').toLowerCase();
	}

	public synchronized String getCurrentPairName() {
		return convertPairName(current_position);
	}
}
