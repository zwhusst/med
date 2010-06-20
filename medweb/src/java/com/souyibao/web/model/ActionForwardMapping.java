package com.souyibao.web.model;

import java.util.Map;

public class ActionForwardMapping {
	public static final String SUCCESS_FORWARD = "success";

	public static final String EXCEPITON_FORWARD = "exception";

	public static final String ERROR_FORWARD = "error";

	private Map<String, ActionForward> forwards = null;;

	public ActionForwardMapping(ActionHandlerModel handlerModel) {
		this.forwards = handlerModel.getForwards();
	}

	public ActionForward findForward(String forwardName) {
		return forwards.get(forwardName);
	}

	public ActionForward successForward() {
		return forwards.get(SUCCESS_FORWARD);
	}

	public ActionForward exceptionForward() {
		return forwards.get(EXCEPITON_FORWARD);
	}

	public ActionForward errorForward() {
		return forwards.get(ERROR_FORWARD);
	}
}
