package aulas

class Config {

	static def load(String filename) {
		Properties props = new Properties();
		FileInputStream fis = new FileInputStream(filename);
		props.load(fis);
		fis.close();
		return new ConfigSlurper().parse(props)
	}

	static class SpanishString {
		static Boolean toBoolean(String self) {
			def trueStrings = ["si", "sí", "s", "1"]
			def falseStrings = ["no", "n", "0"]
			if (trueStrings.contains(self))
				return Boolean.TRUE
			else if (falseStrings.contains(self))
				return Boolean.FALSE
			else {
				String msg = "Se esperaba uno de los valores " + 
						(trueStrings + falseStrings) + " pero en su lugar se " +
						"encontró '$self'" 
				throw new RuntimeException(msg)
			}
		}
	}
	
}
