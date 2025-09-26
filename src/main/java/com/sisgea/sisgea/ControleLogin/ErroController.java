package com.sisgea.sisgea.ControleLogin;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErroController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == 403) {
                return "redirect:/sisgea/erro.html?code=403";
            } else if (statusCode == 404) {
                return "redirect:/sisgea/erro.html?code=404";
            }
        }

        return "redirect:/sisgea/erro.html";
    }
}
