package application.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    String home() {
        return "index";
    }

    @GetMapping("/detail/{summaryId}/{pageId}")
    String detail(Model model, @PathVariable("summaryId") Long summaryId, @PathVariable("pageId") Long pageId) {
        model.addAttribute("summaryId", summaryId);
        model.addAttribute("pageId", pageId);
        return "detail";
    }

    @GetMapping("/history")
    String history() {
        return "history";
    }

    @GetMapping("/help")
    String help() {
        return "help";
    }


    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/logout")
    String logout() {
        return "logout";
    }

    @GetMapping("/403")
    String error() {
        return "error/403";
    }
}
