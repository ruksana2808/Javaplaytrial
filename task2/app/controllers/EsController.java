package controllers;

import config.Global;
import play.mvc.Result;

import javax.inject.Inject;

import static play.mvc.Results.ok;

public class EsController {
    @Inject
    private Global global;
    public Result connection(){
        global.configure();
        return ok("Success");
    }
}
