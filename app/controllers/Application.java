package controllers;

import play.mvc.Controller;
import play.mvc.With;
import security.Seguranca;

@With(Seguranca.class)
public class Application extends Controller {

    public static void index() {
        render();
    }

}
