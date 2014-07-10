package com.assist;

import java.io.InputStream;

abstract class PrivateMultipleResponse extends PrivateBaseClass {

	PrivateMultipleResponse() {
		super();
	}

	protected String current_position;

	public synchronized void setData(InputStream json) {
		try {
			rootNode = null;
			it = null;
			localTimestamp = 0;
			success = false;
			rootNode = mapper.readTree(json);
			if (rootNode.path("success").toString() == "1") {
				it = rootNode.path("return").fieldNames();
				if (it != null) {
					localTimestamp = System.currentTimeMillis();
					success = true;
					return;
				}
			} else if (getErrorMessage().length() != 0) {
				return;
			}
		} catch (Exception e) {
		}
		makeDefaultRootNode();
	}

	/**
	 * default 0
	 * 
	 * @param str
	 * @throws NoSuchMethodException
	 */
	public synchronized void setFrom(String str) throws NoSuchMethodException {
		paramsMap.put("from", str);
	}

	/**
	 * default 1000
	 * 
	 * @param str
	 * @throws NoSuchMethodException
	 */
	public synchronized void setCount(String str) throws NoSuchMethodException {
		paramsMap.put("count", str);
	}

	public synchronized void setFrom_id(String str)
			throws NoSuchMethodException {
		paramsMap.put("from_id", str);
	}

	public synchronized void setEnd_id(String str) throws NoSuchMethodException {
		paramsMap.put("end_id", str);
	}

	/**
	 * DESC or ASC sort type, default DESC
	 * 
	 * @param str
	 * @throws NoSuchMethodException
	 */
	public synchronized void setOrder(String str) throws NoSuchMethodException {
		paramsMap.put("order", str.toUpperCase());
	}

	public synchronized void setSince(String str) throws NoSuchMethodException {
		paramsMap.put("since", str);
	}

	public synchronized void setEnd(String str) throws NoSuchMethodException {
		paramsMap.put("end", str);
	}

	public synchronized String getCurrentAmount() {
		return formatDouble(rootNode.path("return").path(current_position)
				.path("amount").asDouble());
	}

	public synchronized boolean hasNext() {
		if (it != null) {
			return it.hasNext();
		}
		return false;
	}

	public synchronized void switchNext() {
		if (it != null) {
			current_position = it.next();
		}
	}

	public synchronized void switchReset() {
		it = rootNode.path("return").fieldNames();
	}

	public synchronized String getCurrentPosition() {
		return current_position;
	}
}
