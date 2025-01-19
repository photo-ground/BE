package ceos.phototoground;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class AdminController {

    @GetMapping("/customer_test")
    public String customer() {

        return "현재 고객이십니다";
    }

    @GetMapping("/photographer_test")
    public String photographer() {

        return "현재 작가이십니다";
    }
}