package com.lsim;

import static com.lsim.Generator.bruteForce;

import java.util.Map;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		Lsim lsim = Lsim.builder()
				.username("lsim")
				.secret("faced")
				.sender("example")
				.phone("994000000000")
				.text("lorem ipsum dolor")
				.build();

		lsim.generateLink();

		Map<String, String> params = lsim.readParams();

		if (params.isEmpty()) {
			System.out.println("Params is empty: " + params);
			return;
		}

		String pass = bruteForce(params, 5);

		System.out.println("password found: " + pass);

	}
}
