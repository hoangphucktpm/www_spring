package iuh.fit.frontend.controllers;

import iuh.fit.backend.services.CalcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/calc")
public class ServiceController {
    @Autowired
    private CalcService calcService;

    @GetMapping("/add")
    public ModelAndView add(int a, int b) {
        int result = calcService.add(a, b);
        ModelAndView modelAndView = new ModelAndView("result");
        modelAndView.addObject("result", result);
        return modelAndView;
    }


}
