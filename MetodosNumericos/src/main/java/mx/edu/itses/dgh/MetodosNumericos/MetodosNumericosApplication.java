package mx.edu.itses.dgh.MetodosNumericos;

import org.mariuszgromada.math.mxparser.License;
import org.mariuszgromada.math.mxparser.mXparser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MetodosNumericosApplication {

	public static void main(String[] args) {
                boolean isCallSuccessfull=License.iConfirmNonCommercialUse("Materia Metodos Numericos");
                String message=License.getUseTypeConfirmationMessage();
                
               mXparser.consolePrintln("messsage"+message);
                
		SpringApplication.run(MetodosNumericosApplication.class, args);
	}

}
