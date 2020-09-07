package cl.medicapp.acceptance.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginStep {

    @When("Intento logearme con correo {string} y contraseña {string}")
    public void intentoLogearmeConCorreoYContrasenha(String email, String pass) {

    }

    @Then("Obtengo token y codigo http {int}")
    public void obtengoTokenYCodigoHttp(int expected) {

    }

}
