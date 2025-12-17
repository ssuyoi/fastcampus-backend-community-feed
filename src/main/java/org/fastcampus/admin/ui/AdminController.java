package org.fastcampus.admin.ui;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.fastcampus.admin.ui.query.UserStatsQueryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserStatsQueryRepository userStatsQueryRepository;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");

        mv.addObject("result", userStatsQueryRepository.getDailyRegisterUserStats(7));
        return mv;
    }
}
