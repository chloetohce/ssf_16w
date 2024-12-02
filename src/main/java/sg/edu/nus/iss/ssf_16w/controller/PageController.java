package sg.edu.nus.iss.ssf_16w.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/boardgame")
public class PageController {
    
    @GetMapping("/upload")
    public String uploadPage() {
        return "upload-file";
    }
    
}
