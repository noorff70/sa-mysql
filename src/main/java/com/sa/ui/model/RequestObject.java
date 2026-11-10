package com.sa.ui.model;

import lombok.Getter;
import lombok.Setter;

/*
 * Request body of the sa-mongo compatible endpoints (same JSON as sa-mongo's RequestObject).
 * Only the fields sa-mysql uses are declared; other fields the Angular app sends are ignored.
 */
@Getter
@Setter
public class RequestObject {

	private String courseDescription;   // course search text
}
