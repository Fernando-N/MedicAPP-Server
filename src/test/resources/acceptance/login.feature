Feature: Logear usuario

  Scenario: Logearme con un usuario correctamente
    When Intento logearme con correo "admin@test.com" y contraseña "test"
    Then Obtengo token y codigo http 200

  Scenario: Logearme con un usuario incorrectamente
    When Intento logearme con correo "admin@test.com" y contraseña "dasdasd"
    Then Obtengo token y codigo http 400
